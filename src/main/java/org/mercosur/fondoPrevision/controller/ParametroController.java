package org.mercosur.fondoPrevision.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.mercosur.fondoPrevision.dto.MesLiquidacionForm;
import org.mercosur.fondoPrevision.dto.ParamForm;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.entities.ParamPrestamo;
import org.mercosur.fondoPrevision.entities.Parametro;
import org.mercosur.fondoPrevision.exceptions.CustomFieldValidationException;
import org.mercosur.fondoPrevision.exceptions.ParamNotFoundException;
import org.mercosur.fondoPrevision.repository.ParamPrestamoRepository;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.ParamService;
import org.mercosur.fondoPrevision.service.RunBatFileService;

@Controller
public class ParametroController {

	@Autowired
	RunBatFileService runBatFileService;
	
	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	ParamService paramService;
	
	@Autowired
	ParamPrestamoRepository paramPrestamoRepository;

	
	@GetMapping("/paramForm")
	public String getParamForm(Model model) {
		String mesliq = paramService.getMesliquidacion();
		MesLiquidacionForm anioMesForm = new MesLiquidacionForm(1l);
		anioMesForm.setMesliquidacion(mesliq.substring(4)+"/"+mesliq.substring(0, 4));
		try {
			String clave = "fpar";
			String procname = "Parametros";
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);	
			model.addAttribute("procname", procname);
		}
		catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		ParamForm paramForm = new ParamForm();
		
		model.addAttribute("paramForm", paramForm);
		model.addAttribute("aniomesForm", anioMesForm);
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramPrestamoRepository.findAllOrderBymeses());
		model.addAttribute("listTab", "active");
		
		return("parametros/param-view");
	}
	
	private Boolean chequeoMesLiqOk(String mesActual, String mesSiguiente) {
		Integer ma = Integer.valueOf(mesActual);
		Integer ms = Integer.valueOf(mesSiguiente);
		Integer anioa = Integer.valueOf(mesActual.substring(0, 4));
		Integer mesa = Integer.valueOf(mesActual.substring(4));
		Integer anios = Integer.valueOf(mesSiguiente.substring(0, 4));
		Integer mess = Integer.valueOf(mesSiguiente.substring(4));
		if(anioa == anios) {
			if(ma == ms || ma.compareTo(ms) > 0 || ms.compareTo(ma + 1) > 0) {
				return false;
			}			
		}
		else if(anios.compareTo(anioa + 1) > 0) {
			return false;
		}
		else if(anios - anioa == 1 && (mesa != 12 || mess != 1)){
			return false;
		}
		return true;
	}
	
	@PostMapping("/aniomesForm")
	public String actualizarAnioMes(@Valid @ModelAttribute("aniomesForm")MesLiquidacionForm aniomes, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("aniomesForm", aniomes);
		}else {
			try {
				String actualmesliq = paramService.getMesliquidacion();
		        if(!chequeoMesLiqOk(actualmesliq, aniomes.getMesliquidacion().substring(3).concat(aniomes.getMesliquidacion().substring(0, 2)))) {
		        	model.addAttribute("aniomesErrorMessage", "El AnioMes ingresado no cumple la condición de ser igual al anterior más uno.");
		        	model.addAttribute("aniomesForm", aniomes);
		        }
		        else {
					paramService.updateMesLiquidacion(aniomes.getMesliquidacion().substring(3).concat(aniomes.getMesliquidacion().substring(0, 2)));
					model.addAttribute("formSuccess", "la actualización se procesó correctamente");
					model.addAttribute("aniomesForm", new MesLiquidacionForm());
		        }
			}catch (CustomFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				model.addAttribute("aniomesForm", aniomes);
			}
			catch (Exception e) {
				model.addAttribute("aniomesErrorMessage",e.getMessage());
				model.addAttribute("aniomesForm", aniomes);
			}
		}

		model.addAttribute("paramForm", new ParamForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramPrestamoRepository.findAllOrderBymeses());
		model.addAttribute("aniomesTab","active");
		return "parametros/param-view";
	}
	
	@GetMapping("/aniomesForm/cancel")
	public String editCancelAnioMes(Model model) {
		return "redirect:/paramForm";	
	}
	
	@PostMapping("/paramForm")
	public String createParam(@Valid @ModelAttribute("paramForm")ParamForm paramForm, BindingResult result, ModelMap model) {

		Parametro parametro = new Parametro();
		if(result.hasErrors()) {		
			model.addAttribute("paramForm", paramForm);
			model.addAttribute("formTab","active");
		}else {
			try {
				parametro.setDescripcion(paramForm.getParametro().getDescripcion());
				parametro.setMesliquidacion(paramForm.getParametro().getMesliquidacion());
				parametro.setSimbolo(paramForm.getParametro().getSimbolo());
				parametro.setValor(paramForm.getParametro().getValor());
				paramForm.setParametro(paramService.createParametro(parametro));
				model.addAttribute("paramForm", paramForm);
				model.addAttribute("listTab","active");				
			}
			catch (CustomFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				model.addAttribute("paramForm", paramForm);
				model.addAttribute("formTab","active");
				model.addAttribute("paramList", paramService.getAllParametros());
				model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());				
			} 
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("paramForm", paramForm);
				model.addAttribute("formTab","active");
				model.addAttribute("paramList", paramService.getAllParametros());
				model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());				
			}

		}
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
		return "parametros/param-view";
	}

	@PostMapping("/paramFormPrst")
	public String createParamPrst(@Valid @ModelAttribute("paramForm")ParamForm paramForm, BindingResult result, ModelMap model) {

		ParamPrestamo paramprst = new ParamPrestamo();
		if(result.hasErrors()) {		
			model.addAttribute("paramForm", paramForm);
			model.addAttribute("tasasTab","active");
		}else {
			try {
				paramprst.setMesliquidacion(paramForm.getParPrestamo().getMesliquidacion());
				paramprst.setDescripcion(paramForm.getParPrestamo().getDescripcion());
				paramprst.setMeses(paramForm.getParPrestamo().getMeses());
				paramprst.setTasa(paramForm.getParPrestamo().getTasa());
				paramForm.setParPrestamo(paramService.createParamPrestamo(paramprst));
				model.addAttribute("paramForm", paramForm);
				model.addAttribute("listTab","active");				
			}
			catch (CustomFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				model.addAttribute("paramForm", paramForm);
				model.addAttribute("tasasTab","active");
				model.addAttribute("paramList", paramService.getAllParametros());
				model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());				
			} 
			catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("paramForm", paramForm);
				model.addAttribute("tasasTab","active");
				model.addAttribute("paramList", paramService.getAllParametros());
				model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());				
			}

		}
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
		return "parametros/param-view";
	}

	@GetMapping("/editParam/{idfparametros}")
	public String getEditParamForm(Model model, @PathVariable(name="idfparametros") Long id) throws Exception{
		Parametro param = paramService.getParametroById(id);
		ParamForm paramForm = new ParamForm();
		paramForm.setParametro(param);
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
		model.addAttribute("paramForm", paramForm);
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("formTab", "active");
		model.addAttribute("editMode", true);
		
		return "parametros/param-view";
	}

	@GetMapping("/editParamPrst/{idfparamprst}")
	public String getEditParamPrst(Model model, @PathVariable(name="idfparamprst") Integer id) throws Exception{
		ParamPrestamo paramprst = paramService.getParamPrestamoById(id);
		ParamForm paramForm = new ParamForm();
		paramForm.setParPrestamo(paramprst);
		
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
		model.addAttribute("parprst", paramService.getParamPrestamoById(id));
		model.addAttribute("paramForm", paramForm);
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("tasasTab", "active");
		model.addAttribute("editMode", true);
		
		return "parametros/param-view";
	}

	@PostMapping("/editParam")
	public String postEditParamForm(@Valid @ModelAttribute("paramForm")ParamForm paramForm, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("paramForm", paramForm);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		}else {
			try {
				paramService.updateParametro(paramForm.getParametro());
				model.addAttribute("paramForm", new ParamForm());
				model.addAttribute("formSuccess", "Parametro actualizado exitosamente!.");
				model.addAttribute("formTab","active");
			} catch (Exception e) {
				model.addAttribute("formError",e.getMessage());
				model.addAttribute("paramForm", paramForm);
				model.addAttribute("formTab","active");
				model.addAttribute("paramList", paramService.getAllParametros());
				model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
				model.addAttribute("editMode","true");
			}
		}
		
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramPrestamoRepository.findAllOrderBymeses());

		return "parametros/param-view";
	}
	
	@PostMapping("/editParamPrst")
	public String postEditParamPrst(@Valid @ModelAttribute("paramForm")ParamForm paramForm, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("paramForm", paramForm);
			model.addAttribute("tasasTab","active");
			model.addAttribute("editMode","true");
		}else {
			try {
				paramService.updateParamPrestamo(paramForm.getParPrestamo());
				model.addAttribute("paramForm", new ParamForm());
				model.addAttribute("formSuccess", "Parametro actualizado exitosamente!.");
				model.addAttribute("tasasTab","active");
			} catch (Exception e) {
				model.addAttribute("formError",e.getMessage());
				model.addAttribute("paramForm", paramForm);
				model.addAttribute("tasasTab","active");
				model.addAttribute("paramList", paramService.getAllParametros());
				model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
				model.addAttribute("editMode","true");
			}
		}
		
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramPrestamoRepository.findAllOrderBymeses());

		return "parametros/param-view";
	}


	@GetMapping("/editParam/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/paramForm";
	}
	
	@GetMapping("/editParamPrst/cancel")
	public String cancelEditParPrst(ModelMap model) {
		return "redirect:/paramForm";
	}

	@GetMapping("/deleteParam/{idfparametros}")
	public String deleteParam(Model model, @PathVariable(name="idfparametros") Long id) {
		try {
			paramService.deleteParametro(id);
		}
		catch(ParamNotFoundException pe) {
			model.addAttribute("listErrorMessage", pe.getMessage());
		}
		catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
		model.addAttribute("listTab", "active");
		
		return "redirect:/paramForm";
	}
	
	@GetMapping("/deleteParamPrst/{idfparamprst}")
	public String deleteParamPrst(Model model, @PathVariable(name="idfparamprst") Integer id) {
		try {
			paramService.deleteParamPrestamo(id);
		}
		catch(ParamNotFoundException pe) {
			model.addAttribute("listErrorMessage", pe.getMessage());
		}
		catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
		model.addAttribute("listTab", "active");
		
		return "redirect:/paramForm";
	}

	@GetMapping("/ayuda/fpar")
	public String getAyuda(Model model) {
		try {
			String clave = "fpar";
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);				
		}
		catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("paramForm", new Parametro());
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
		return "parametros/param-view";
	}

	@GetMapping("/respaldos/parametros")
	public String respaldosParametros(Model model) {
		
		String result = runBatFileService.ejecutarBatOrSSH("C:\\var\\backup\\respParametros.bat");
		if(result.equals("Ejecucion exitosa")) {
			model.addAttribute("listSuccessMessage", "El respaldo se ejecutó exitosamente!");
		}
		else {
			model.addAttribute("listErrorMessage", "No se ejecutó el respaldo");
		}
		model.addAttribute("paramForm", new ParamForm());
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("listTab", "active");
		model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
		
		return "parametros/param-view";
	}
	
	@GetMapping("/restore/parametros")
	public String restoreParametros(Model model) {
		String result = runBatFileService.ejecutarBatOrSSH("C:\\var\\backup\\restParametros.bat");
		if(result.equals("Ejecucion exitosa")) {
			model.addAttribute("listSuccessMessage", "La restauración se ejecutó exitosamente!");
		}
		else {
			model.addAttribute("listErrorMessage", "No se ejecutó la restauración");
		}
		model.addAttribute("paramForm", new ParamForm());
		model.addAttribute("aniomesForm", new MesLiquidacionForm());
		model.addAttribute("paramList", paramService.getAllParametros());
		model.addAttribute("paramPrstList", paramService.getAllParamPrestamo());
		model.addAttribute("listTab", "active");
		
		return "parametros/param-view";
		
	}
}
