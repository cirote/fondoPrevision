package org.mercosur.fondoPrevision.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.mercosur.fondoPrevision.dto.SalarioForm;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.entities.Gcargo;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.GcargoService;
import org.mercosur.fondoPrevision.service.RunBatFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SalariosController {

	@Autowired
	RunBatFileService runBatFileService;
	
	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	GcargoService gcargoService;

	@GetMapping("/salarioForm")
	public String getSalariosForm(Model model) {
		try {
			String clave = "fsalr";
			String procname = "Salarios";
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);	
			model.addAttribute("procname", procname);
			List<Gcargo> lstCargosSM = gcargoService.getAllByUnidad("SM");
			List<Gcargo> lstCargosUTF = gcargoService.getAllByUnidad("UTF");
			if(lstCargosSM.get(0).getComplemento().compareTo(BigDecimal.ZERO) > 0) {
				model.addAttribute("salarioForm", new SalarioForm(true));
			}
			else {
				model.addAttribute("salarioForm", new SalarioForm(false));			
			}
			model.addAttribute("salariosSMList", lstCargosSM);
			model.addAttribute("salariosUTFList", lstCargosUTF);

		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
		}
		
		return("salarios/salarios-view");

	}
	
	@PostMapping("/calculoSalario")
	public String calculoAumento(@Valid @ModelAttribute(name="salarioForm") SalarioForm form, Errors errors, BindingResult result, Model model) {
		
		Boolean tieneComplemento = form.getComplementoMayorquecero();
		
			try {
				if(errors.hasErrors()) {
					model.addAttribute("salarioForm", form);
				}
				else if(tieneComplemento) {
					model.addAttribute("salarioForm", form);
					model.addAttribute("formError", "No se puede volver a calcular porque el complemento es > 0");
				}
				else if(form.getPctAumento().toString().matches("\\d+\\.?\\d*")) {
					gcargoService.calculoDeAumento(form.getPctAumento());
					model.addAttribute("salarioForm", new SalarioForm(true));
				}
				else {
					model.addAttribute("salarioForm", new SalarioForm(false));
					model.addAttribute("formError", "El formato del pct. de aumento no es correcto");
				}
				String clave = "fsalr";
				String procname = "Salarios";
				Ayuda help = ayudaService.getByClave(clave);
				model.addAttribute("help", help);	
				model.addAttribute("procname", procname);
				model.addAttribute("salariosSMList", gcargoService.getAllByUnidad("SM"));
				model.addAttribute("salariosUTFList", gcargoService.getAllByUnidad("UTF"));

			}
			catch(Exception e) {
				model.addAttribute("salarioForm", form);
				model.addAttribute("formError", e.getMessage());
			}


		return("salarios/salarios-view");
	}
	
	@GetMapping("/actualizarBasico")
	public String integrarBasicoyComplemento(Model model) {
		
		try {
			gcargoService.sumarBasicoAndComplemento();
			model.addAttribute("salarioForm", new SalarioForm(false));
			model.addAttribute("salariosSMList", gcargoService.getAllByUnidad("SM"));
			model.addAttribute("salariosUTFList", gcargoService.getAllByUnidad("UTF"));
			
			String clave = "fsalr";
			String procname = "Salarios";
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);	
			model.addAttribute("procname", procname);
		}
		catch(Exception e) {
			model.addAttribute("salarioForm", new SalarioForm(false));
			model.addAttribute("formError", e.getMessage());
		}
		
		return("salarios/salarios-view");
		
	}
}
