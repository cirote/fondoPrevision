package org.mercosur.fondoPrevision.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mercosur.fondoPrevision.dto.ChangePasswordForm;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Role;
import org.mercosur.fondoPrevision.entities.SolicitudPrestamo;
import org.mercosur.fondoPrevision.entities.User;
import org.mercosur.fondoPrevision.exceptions.CustomFieldValidationException;
import org.mercosur.fondoPrevision.exceptions.UsernameOrIdNotFound;
import org.mercosur.fondoPrevision.pdfs.UserPdfExporter;
import org.mercosur.fondoPrevision.repository.RoleRepository;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.GplantaService;
import org.mercosur.fondoPrevision.service.ParamService;
import org.mercosur.fondoPrevision.service.SolicitudPrestamoService;
import org.mercosur.fondoPrevision.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lowagie.text.DocumentException;

@Controller
public class UserController {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	GplantaService gplantaService;

	@Autowired
	ParamService paramService;
	
	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	SolicitudPrestamoService solicitudPrestamoService;
	

	private Map<String, Role> roleCache;
	
	@GetMapping({"/","/login"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		Role userRol = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRol);
		model.addAttribute("userForm", new User());
		model.addAttribute("roles",roles);
		model.addAttribute("signup", true);
		model.addAttribute("titulo", "Fondo de Previsión MERCOSUR");
		return "user-form/user-signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@Valid @ModelAttribute("userForm")User user, BindingResult result,
			HttpServletRequest request, ModelMap model) {
		Role userRol = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRol);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		model.addAttribute("userForm", user);
		model.addAttribute("roles",roles);
		model.addAttribute("signup", true);

		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			return "user-form/user-signup";
		}else {
			try {
				userService.createUser(user);
					
			}catch (CustomFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				return "user-form/user-signup";
			}
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				return "user-form/user-signup";
			}
		}
		
		return "index";
		
	}

	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("allRoles",roleRepository.findAll());
		List<SolicitudPrestamo> lstSolicitudes = new ArrayList<SolicitudPrestamo>();
		List<SolicitudPrestamo> lstSolComision = new ArrayList<SolicitudPrestamo>();
		List<SolicitudPrestamo> lstDevueltas = new ArrayList<SolicitudPrestamo>();
		String mesliquidacion = paramService.getMesliquidacion();
		
		try {
			User loggedUser = userService.getLoggedUser();
			model.addAttribute("loggedUser", loggedUser);

			if(userService.isLoggedUserADMIN()) {
				lstSolicitudes = solicitudPrestamoService.getNoProcesadas();
				lstDevueltas = solicitudPrestamoService.getdevueltasDeComision();
				model.addAttribute("mesLiquidacion", mesliquidacion.substring(4) + '/' + mesliquidacion.subSequence(0, 4));
				if(lstSolicitudes.isEmpty() && lstDevueltas.isEmpty()) {
					model.addAttribute("sinSolicitudes", true);
				}
				else {
					model.addAttribute("lstSolicitudes", lstSolicitudes);
					model.addAttribute("lstDevueltas", lstDevueltas);
				}
			}
			else if(userService.isLoggedUserSUPERVISOR()) {
				lstSolComision = solicitudPrestamoService.getenviadasAComision();
				if(lstSolComision.isEmpty()) {
					model.addAttribute("sinSolicitudes", true);
				}
				else {
					model.addAttribute("lstSolComision", lstSolComision);
				}
			}
			
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());			
		}

		return "/home";
		
	}
	
	@GetMapping("/userForm")
	public String getUserForm(Model model) {
		List<Role> roles = roleRepository.findAll();
		roleCache = new HashMap<String, Role>();
		for (Role role : roles) {
			roleCache.put(role.getId().toString(), role);
		}
		try {
			Ayuda help = ayudaService.getByClave("finfuser");
			model.addAttribute("help", help);
		}
		catch(Exception e) {
			model.addAttribute("formError", "No se encontró la ayuda correspondiente a este Form");
		}
		model.addAttribute("userForm", new User());
		model.addAttribute("allRoles", roles);
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab","active");
		model.addAttribute("plantaList", gplantaService.getAllPlanta());

		return "user-form/user-view";
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		binder.registerCustomEditor(Set.class, "roles", new CustomCollectionEditor(Set.class) {
			protected Object convertElement(Object element) {
				if (element instanceof Role) {
					return element;
				}
				if (element instanceof String) {
					Role role = roleCache.get(element);
					return role;
				}
				System.out.println("Don't know what to do with: " + element);
				return null;
			}
		});
	}
	
	@GetMapping("/userForm/{tarjeta}")
	public String nuevoUsuarioFromPlanta(@PathVariable(name="tarjeta") Integer tarjeta, ModelMap model) {
		List<Role> roles = roleRepository.findAll();
		roleCache = new HashMap<String, Role>();
		for (Role role : roles) {
			roleCache.put(role.getId().toString(), role);
		}
		try {
			User user = new User();
			
			Gplanta func = gplantaService.getFuncionarioByTarjeta(tarjeta);
			String[] nya = func.getNombre().split(" ", 2);
			user.setApellido(nya[0]);
			user.setNombre(nya[1]);
			user.setGplanta_id(func.getIdgplanta());
			user.setTarjeta(tarjeta);
			
			model.addAttribute("userForm", user);
			model.addAttribute("allRoles", roles);
			model.addAttribute("userList", userService.getAllUsers());
			model.addAttribute("formTab","active");
			model.addAttribute("plantaList", gplantaService.getAllPlanta());			
		}
		catch(Exception e) {
			model.addAttribute("formError",e.getMessage());
			model.addAttribute("userForm", new User());
			model.addAttribute("formTab","active");
			model.addAttribute("userList", userService.getAllUsers());
			model.addAttribute("allRoles", roles);			
		}
		return "user-form/user-view";
	}
		
	@GetMapping("/oneUserForm")
	public String getOneUserForm(Model model) {
		try {
			List<Role> roles = roleRepository.findAll();
			roleCache = new HashMap<String, Role>();
			for (Role role : roles) {
				roleCache.put(role.getId().toString(), role);
			}

			User userlogged = userService.getLoggedUser();
			User user = userService.getUserById(userlogged.getId());
			model.addAttribute("userList", userService.getAllUsers());
			model.addAttribute("allRoles", roleRepository.findAll());
			model.addAttribute("userForm", user);
			model.addAttribute("editMode", true);
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user-form/user-datos-form";
	}

	@PostMapping("/editOneUser")
	public String postEditOneUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("editMode","true");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
		}else {
			try {
				Set<Role> userRol = userService.getRolesOfUser(user);
				user.setRoles(userRol);
				userService.updateUser(user);
				User userlogged = userService.getLoggedUser();
				user = userService.getUserById(userlogged.getId());
				model.addAttribute("userForm", user);
				model.addAttribute("editMode", true);
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
			} catch (Exception e) {
				model.addAttribute("formError",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("allRoles",roleRepository.findAll());
				model.addAttribute("editMode","true");
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
			}
		}
		model.addAttribute("allRoles",roleRepository.findAll());
		return "user-form/user-datos-form";
	}

	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formError", result.getFieldError());
			model.addAttribute("formTab","active");
		}else {
			try {
				userService.createUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
				
			}catch (CustomFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("allRoles",roleRepository.findAll());
			}
			catch (Exception e) {
				model.addAttribute("formError",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("allRoles",roleRepository.findAll());
			}
		}
		
		model.addAttribute("plantaList", gplantaService.getAllPlanta());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("allRoles",roleRepository.findAll());
		return "user-form/user-view";
	}
		
	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name="id") Long id) throws Exception{
		User user = userService.getUserById(id);
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("allRoles", roleRepository.findAll());
		model.addAttribute("userForm", user);
		model.addAttribute("formTab", "active");
		model.addAttribute("editMode", true);
		model.addAttribute("passwordForm",new ChangePasswordForm(id));
		
		return "user-form/user-view";
	}
	
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
		}else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("allRoles",roleRepository.findAll());
				model.addAttribute("editMode","true");
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
			}
		}
		
		model.addAttribute("plantaList", gplantaService.getAllPlanta());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("allRoles",roleRepository.findAll());
		return "user-form/user-view";
	}
	
	@GetMapping("/editUser/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(Model model, @PathVariable(name="id") Long id) {
		try {
			userService.deleteUser(id);
		}
		catch(UsernameOrIdNotFound uoie) {
			model.addAttribute("listErrorMessage", uoie.getMessage());
		}
		return "redirect:/userForm";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/users/export")
	public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("Application/pdf");
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyyHH:mm:ss");
		String currentDateTime = sdf.format(new Date()); 
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		
		response.setHeader(headerKey, headerValue);
		
		Iterable<User> lstUsers = userService.getAllUsers();
		
		UserPdfExporter exporter = new UserPdfExporter(lstUsers);
		exporter.export(response);
		
		
	}
}
