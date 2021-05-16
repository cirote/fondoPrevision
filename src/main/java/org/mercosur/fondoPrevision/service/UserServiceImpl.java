package org.mercosur.fondoPrevision.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mercosur.fondoPrevision.dto.ChangePasswordForm;
import org.mercosur.fondoPrevision.entities.Role;
import org.mercosur.fondoPrevision.entities.User;
import org.mercosur.fondoPrevision.exceptions.CustomFieldValidationException;
import org.mercosur.fondoPrevision.exceptions.FuncionarioNoEncontradoException;
import org.mercosur.fondoPrevision.exceptions.UserNotFoundException;
import org.mercosur.fondoPrevision.exceptions.UsernameOrIdNotFound;
import org.mercosur.fondoPrevision.repository.RoleRepository;
import org.mercosur.fondoPrevision.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	GplantaService gplantaService;
	
	@Autowired
	LogfondoService logfondoService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@SuppressWarnings("rawtypes")
	public Iterable getAllUsers() {
		return userRepository.findAll();
	}

	private Boolean checkUsernameAvaliable(User user) throws Exception{
		Optional<?> userFound = userRepository.findByusername(user.getUsername());
		if(userFound.isPresent()) {
			throw new CustomFieldValidationException("username inválido", "username");
		}
		return true;
	}
	
	private Boolean checkPasswordValid(User user) throws Exception{
		if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new CustomFieldValidationException("El campo de confirmacion de Password es obligatorio", "confirmPassword");
		}
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			throw new CustomFieldValidationException("Password y Confirmación no coinciden", "password");
		}
		return true;
	}
	
	private Boolean checkFuncionarioValid(User user) throws Exception{
		if(!gplantaService.checkFuncionarioByTarjeta(user.getTarjeta())){
			throw new FuncionarioNoEncontradoException();
		}
		return true;
	}
	
	public User createUser(User user) throws Exception{
		if(checkUsernameAvaliable(user) && checkPasswordValid(user) && checkFuncionarioValid(user)) {
			String encodePass = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePass);
			user.setGplanta_id(gplantaService.getIdGplantaByTarjeta(user.getTarjeta()));
			user = userRepository.save(user);
			logfondoService.agregarLog("Creación-Actualización de Usuario", " " + user.getUsername());
		}
		return user;
	}

	@Override
	public User createUserWithPlanta(User user) throws Exception {
		if(checkUsernameAvaliable(user) && checkPasswordValid(user)) {
			String encodePass = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePass);
			user.setGplanta_id(gplantaService.getIdGplantaByTarjeta(user.getTarjeta()));
			user = userRepository.save(user);
			logfondoService.agregarLog("Creación-Actualización de Usuario", " " + user.getUsername());
		}
		return user;
	}

	public User getUserById(Long id) throws UsernameOrIdNotFound{
		User user = userRepository.findById(id).orElseThrow(() -> new UsernameOrIdNotFound("Id del usuario no encontrado"));
		return user;
	}

	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		logfondoService.agregarLog("Creación-Actualización de Usuario", " " + toUser.getUsername());
		return userRepository.save(toUser);
	}
	
	protected void mapUser(User from,User to) {
		to.setUsername(from.getUsername());
		to.setNombre(from.getNombre());
		to.setApellido(from.getApellido());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}

	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws UsernameOrIdNotFound {
		User user = getUserById(id);
		userRepository.delete(user);
		try {
			logfondoService.agregarLog("Eliminación de usuario", " "+ user.getUsername());			
		}
		catch(Exception e) {
			throw new UsernameOrIdNotFound(e.getMessage());
		}

	}

	@Override
	public boolean isLoggedUserADMIN(){
		 return loggedUserHasRole("ROLE_ADMIN");
	}

	@Override
	public boolean isLoggedUserSUPERVISOR() {
		return loggedUserHasRole("ROLE_SUPERVISOR");
	}
	
	public boolean loggedUserHasRole(String role) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = null;
		Object roles = null; 
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		
			roles = loggedUser.getAuthorities().stream()
					.filter(x -> role.equals(x.getAuthority() ))      
					.findFirst().orElse(null); //loggedUser = null;
		}
		return roles != null ?true :false;
	}
	
	@SuppressWarnings("unchecked")
	public Set<Role> getRolesOfUser(User user) throws Exception{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = null;
		Object roles = null; 
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		
			roles = loggedUser.getAuthorities().stream().map(temp -> {
				Role rol = roleRepository.findByDescripcion(temp.getAuthority());
				return rol;
			}).collect(Collectors.toSet());
		}
		
		return (Set<Role>) roles;
	}
	
	public User getLoggedUser() throws Exception {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserDetails loggedUser = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		User myUser = userRepository
				.findByusername(loggedUser.getUsername()).orElseThrow(() -> new Exception(""));
		if(loggedUserHasRole("ROLE_ADMIN")) {
			myUser.setIsAdmin(true);
		}
		else if(loggedUserHasRole("ROLE_SUPERVISOR")) {
			myUser.setIsSuper(true);
		}
		else {
			myUser.setIsUser(true);
		}
		return myUser;
	}


	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		User user = userRepository.findById(form.getId()) .orElseThrow(() -> new Exception("Usuario no encontrado en ChangePassword - " + this.getClass().getName()));
		
		if ( !isLoggedUserADMIN() && !bCryptPasswordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
			throw new Exception ("Current Password invalido.");
		}
		
		if(bCryptPasswordEncoder.matches(form.getNewPassword(), user.getPassword())) {
			throw new Exception("La nueva Password no debe ser igual a la anterior");
		}
		
		if(!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("La password nueva y su confirmacion no coinciden!");
		}
		
		String encodePass = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePass);
		logfondoService.agregarLog("Cambio de Password", " del Usuario: " + user.getUsername());
		
		return userRepository.save(user);
	}

	@Override
	public User getUserByUsername(String username) throws UsernameOrIdNotFound {
		User user = userRepository.findByusername(username).orElseThrow(() -> new UsernameOrIdNotFound("Username del usuario no encontrado"));
		return user;
	}

	@Override
	public User getUserByTarjeta(Integer tarjeta) throws UsernameOrIdNotFound {
		User user = userRepository.findByTarjeta(tarjeta).orElseThrow(() -> new UsernameOrIdNotFound("Usuario no encontrado según tarjeta"));
		return user;
	}

	@Override
	public User get(String resetPasswordToken) {
		return userRepository.findByResetPasswordToken(resetPasswordToken);
	}

	@Override
	public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user != null) {
			user.setResetPasswordToken(token);
			userRepository.save(user);
		}
		else {
			throw new UserNotFoundException("No se encontró ningún usuario con el mail ingresado");
		}
		
	}

	@Override
	public User resetPassword(ChangePasswordForm form) throws Exception {
		User user = userRepository.findById(form.getId()) .orElseThrow(() -> new Exception("Usuario no encontrado en ChangePassword - " + this.getClass().getName()));
		
		if(!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("La password nueva y su confirmacion no coinciden!");
		}
		
		String encodePass = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePass);
		user.setResetPasswordToken(null);
		logfondoService.agregarLog("Cambio de Password", " del Usuario: " + user.getUsername(), user.getUsername());
		
		return userRepository.save(user);
	}


}
