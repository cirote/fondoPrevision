package org.mercosur.fondoPrevision.service;

import org.mercosur.fondoPrevision.exceptions.UserNotFoundException;
import org.mercosur.fondoPrevision.exceptions.UsernameOrIdNotFound;

import java.util.Set;

import org.mercosur.fondoPrevision.dto.ChangePasswordForm;
import org.mercosur.fondoPrevision.entities.Role;
import org.mercosur.fondoPrevision.entities.User;

public interface UserService {

	@SuppressWarnings("rawtypes")
	public Iterable getAllUsers();
	
	public User getUserById(Long id) throws UsernameOrIdNotFound;
	
	public User getUserByUsername(String username) throws UsernameOrIdNotFound;
	
	public User getUserByTarjeta(Integer tarjeta) throws UsernameOrIdNotFound;
	
	public User getLoggedUser() throws Exception;
	
	public Set<Role> getRolesOfUser(User user) throws Exception;
	
	public boolean isLoggedUserADMIN();
	
	public boolean isLoggedUserSUPERVISOR();
	
	public User createUser(User formUser) throws Exception;
	
	public User createUserWithPlanta(User user) throws Exception;

	public User updateUser(User user) throws Exception;

	public void deleteUser(Long id) throws UsernameOrIdNotFound;

	public User changePassword(ChangePasswordForm form) throws Exception;
	
	public User resetPassword(ChangePasswordForm form) throws Exception;
	
	public User get(String resetPasswordToken);
	
	public void updateResetPasswordToken(String token, String email) throws UserNotFoundException;
	
}
