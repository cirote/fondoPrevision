package org.mercosur.fondoPrevision.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.mercosur.fondoPrevision.dto.CuentaNuevaForm;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Role;
import org.mercosur.fondoPrevision.entities.Saldos;
import org.mercosur.fondoPrevision.entities.User;
import org.mercosur.fondoPrevision.exceptions.CustomFieldValidationException;
import org.mercosur.fondoPrevision.repository.GcargoRepository;
import org.mercosur.fondoPrevision.repository.GorganigramaRepository;
import org.mercosur.fondoPrevision.repository.RoleRepository;
import org.mercosur.fondoPrevision.repository.SaldosRepository;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.GplantaService;
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

@Controller
public class CuentaNuevaController {

	@Autowired
	GcargoRepository gcargoRepository;
	
	@Autowired
	GorganigramaRepository gorganigramaRepository;
	
	@Autowired
	GplantaService gplantaService;
	
	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	SaldosRepository saldosRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserService userService;

	private Map<String, Role> roleCache;


	@GetMapping("/newctaForm")
	public String getCuentaForm(Model model) {
		List<Role> roles = roleRepository.findAll();
		roleCache = new HashMap<String, Role>();
		for (Role role : roles) {
			roleCache.put(role.getId().toString(), role);
		}
		try {
			Gplanta funcionario = new Gplanta();
			funcionario.setTarjeta(gplantaService.getLastTarjeta());
			User usuario = new User();
			usuario.setTarjeta(funcionario.getTarjeta());
			
			CuentaNuevaForm cuentaNuevaForm = new CuentaNuevaForm();
			cuentaNuevaForm.setFuncionario(funcionario);
			cuentaNuevaForm.setUsuario(usuario);
			
			String clave = "faperCta";
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);

			model.addAttribute("cuentaNuevaForm", cuentaNuevaForm);
			model.addAttribute("cargos", gcargoRepository.findAll());
			model.addAttribute("sectores", gorganigramaRepository.findAll());
			model.addAttribute("allRoles", roles);
			model.addAttribute("plantaSMList", gplantaService.getAllByUnidad("SM"));
			model.addAttribute("plantaUTFList", gplantaService.getAllByUnidad("UTF"));
			model.addAttribute("procname", "CtaNueva");
			model.addAttribute("formTab", "active");
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("cuentaNuevaForm", new CuentaNuevaForm());
			model.addAttribute("cargos", gcargoRepository.findAll());
			model.addAttribute("sectores", gorganigramaRepository.findAll());
			model.addAttribute("allRoles", roles);
			model.addAttribute("funcionario", new Gplanta());
			model.addAttribute("procname", "CtaNueva");
			model.addAttribute("formTab", "active");			
		}
		
		return "funcionarios/funcionarios-view";
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

	
	@PostMapping("/cuentanuevaForm")
	public String createCuenta(@Valid @ModelAttribute("cuentaNuevaForm") CuentaNuevaForm form, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("cuentaNuevaForm", form);
			model.addAttribute("cargos", gcargoRepository.findAll());
			model.addAttribute("sectores", gorganigramaRepository.findAll());
			model.addAttribute("formTab","active");
		}else {
			try {
				if(form.getBasico().compareTo(BigDecimal.ZERO) > 0) {
					if(form.getComplemento() == null) { 
						form.setComplemento(BigDecimal.ZERO);
					};
					Gplanta newFun = gplantaService.createCuenta(form.getFuncionario(), form.getIdcargo(), form.getIdorganigrama(), form.getBasico(), form.getComplemento());
					if(newFun.getIdgplanta() != null) {
						Optional<Saldos> saldos = saldosRepository.findByTarjeta(newFun.getTarjeta());
						if(saldos.isPresent()) {
							model.addAttribute("saldos", saldos.get());							
						}
						else {
							model.addAttribute("formError", "No se generó el registro de saldos!");
						}
						form.getUsuario().setTarjeta(newFun.getTarjeta());
						String nombreapellido[] = newFun.getNombre().split(" ");
						form.getUsuario().setApellido(nombreapellido[0]);
						form.getUsuario().setNombre(nombreapellido[1]);
						form.getUsuario().setRoles(form.getRoles());
						userService.createUser(form.getUsuario());
					}
					model.addAttribute("cuentaNuevaForm", new CuentaNuevaForm());
					model.addAttribute("formSuccess", "Cuenta creada con éxito!");
					model.addAttribute("cargos", gcargoRepository.findAll());
					model.addAttribute("sectores", gorganigramaRepository.findAll());
					model.addAttribute("plantaSMList", gplantaService.getAllByUnidad("SM"));
					model.addAttribute("plantaUTFList", gplantaService.getAllByUnidad("UTF"));
					model.addAttribute("allRoles", roleRepository.findAll());
					model.addAttribute("procname", "CtaNueva");
					model.addAttribute("outputMode", true);
					model.addAttribute("formTab","active");
				}
				else {
					result.rejectValue("basico", null, "Se debe ingresar el valor del primer mes");
					model.addAttribute("cuentaNuevaForm", form);
					model.addAttribute("formTab","active");
					model.addAttribute("plantaSMList", gplantaService.getAllByUnidad("SM"));
					model.addAttribute("plantaUTFList", gplantaService.getAllByUnidad("UTF"));
					model.addAttribute("allRoles", roleRepository.findAll());
					model.addAttribute("outputMode", false);
					model.addAttribute("cargos", gcargoRepository.findAll());
					model.addAttribute("sectores", gorganigramaRepository.findAll());
					model.addAttribute("procname", "CtaNueva");
				}
				
				
			}catch (CustomFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				model.addAttribute("cuentaNuevaForm", form);
				model.addAttribute("formTab","active");
				model.addAttribute("cargos", gcargoRepository.findAll());
				model.addAttribute("sectores", gorganigramaRepository.findAll());
				model.addAttribute("allRoles", roleRepository.findAll());
				model.addAttribute("procname", "CtaNueva");				
			}
			catch (Exception e) {
				model.addAttribute("formError",e.getMessage());
				model.addAttribute("cuentaNuevaForm", form);
				model.addAttribute("formTab","active");
				model.addAttribute("cargos", gcargoRepository.findAll());
				model.addAttribute("sectores", gorganigramaRepository.findAll());
				model.addAttribute("allRoles", roleRepository.findAll());
				model.addAttribute("procname", "CtaNueva");
			}
		}

		return "funcionarios/funcionarios-view";
	}
	
	
	@GetMapping("/creaCuenta/{tarjeta}")
	public String getcreateCuentaForm(Model model, @PathVariable(name="tarjeta") Integer tarjeta) throws Exception{
		try {
			Gplanta fun = gplantaService.getFuncionarioByTarjeta(tarjeta);
			try {
				gplantaService.crearCuentaDeFuncionarioDePlanta(fun);					
				model.addAttribute("formSuccess", "Se creó la cuenta Exitosamente!");
			}
			catch(Exception e) {
				model.addAttribute("formError", e.getMessage());
			}

			model.addAttribute("plantaSMList", gplantaService.getAllByUnidad("SM"));
			model.addAttribute("plantaUTFList", gplantaService.getAllByUnidad("UTF"));
			model.addAttribute("sectores", gorganigramaRepository.findAll());
			model.addAttribute("plantaTab", "active");
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("plantaTab", "active");
		}
		model.addAttribute("cuentaNuevaForm", new CuentaNuevaForm());
		model.addAttribute("sectores", gorganigramaRepository.findAll());
		model.addAttribute("cargos", gcargoRepository.findAll());
		model.addAttribute("procname", "CtaNueva");
		return "funcionarios/funcionarios-view";
	}

	@GetMapping("/editCuenta/{tarjeta}")
	public String getEditCuentaForm(Model model, @PathVariable(name="tarjeta") Integer tarjeta) throws Exception{
		try {
			Gplanta fun = gplantaService.getFuncionarioByTarjeta(tarjeta);
			try {		
				CuentaNuevaForm ctaNueva = new CuentaNuevaForm();
				ctaNueva.setFuncionario(fun);
				Saldos funsaldos = fun.getSaldos();
				model.addAttribute("cuentaNuevaForm", ctaNueva);
				model.addAttribute("cargos", gcargoRepository.findAll());
				model.addAttribute("sectores", gorganigramaRepository.findAll());
				model.addAttribute("funcionario", fun);
				model.addAttribute("saldos", funsaldos);
				model.addAttribute("outputMode", true);
				model.addAttribute("formTab", "active");
			}
			catch(Exception e) {
				model.addAttribute("outputMode", false);
				model.addAttribute("formError", e.getMessage());
				model.addAttribute("plantaTab", "active");
			}
			model.addAttribute("cargos", gcargoRepository.findAll());
			model.addAttribute("sectores", gorganigramaRepository.findAll());
			model.addAttribute("plantaSMList", gplantaService.getAllByUnidad("SM"));
			model.addAttribute("plantaUTFList", gplantaService.getAllByUnidad("UTF"));
		}
		catch(Exception e) {
			model.addAttribute("cuentaNuevaForm", new CuentaNuevaForm());
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("outputMode", false);
			model.addAttribute("plantaSMList", gplantaService.getAllByUnidad("SM"));
			model.addAttribute("plantaUTFList", gplantaService.getAllByUnidad("UTF"));
			model.addAttribute("sectores", gorganigramaRepository.findAll());
			model.addAttribute("plantaTab", "active");
		}
		model.addAttribute("cargos", gcargoRepository.findAll());
		model.addAttribute("procname", "CtaNueva");
		
		return "funcionarios/funcionarios-view";
	}
	
	@GetMapping("/cuentanuevaform/cancel")
	public String cancelCuentaForm(Model model) {
				
		return "redirect:/newctaForm";
	}


	public Map<String, Role> getRoleCache() {
		return roleCache;
	}


	public void setRoleCache(Map<String, Role> roleCache) {
		this.roleCache = roleCache;
	}

}

