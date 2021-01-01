package org.mercosur.fondoPrevision.controller;

import org.mercosur.fondoPrevision.dto.CargoNuevoForm;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.entities.Gcargo;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.repository.GcargoRepository;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.GvinculoFuncionarioCargoRepository;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.GplantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CambioCargoController {

	@Autowired
	GcargoRepository gcargoRepository;
	
	@Autowired
	GvinculoFuncionarioCargoRepository gvinculoRepository;
	
	@Autowired
	GplantaService gplantaService;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Autowired
	AyudaService ayudaService;

	@GetMapping("/cargoForm")
	public String getCargoForm(Model model) {
		try {
			Gplanta funcionario = new Gplanta();
			Gcargo cargoActual = new Gcargo();
			CargoNuevoForm cargoNuevoForm = new CargoNuevoForm();
			cargoNuevoForm.setFuncionario(funcionario);
			cargoNuevoForm.setIdgplanta(funcionario.getIdgplanta());
			cargoNuevoForm.setCargoActual(cargoActual);
			cargoNuevoForm.setBasico("");
			Ayuda help = ayudaService.getByClave("factCargo");
			model.addAttribute("help", help);
			model.addAttribute("cargoNuevoForm", cargoNuevoForm);
			model.addAttribute("cargosList", gcargoRepository.findAll());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("plantaSMList", gplantaService.getAllByUnidad("SM"));
			model.addAttribute("plantaUTFList", gplantaService.getAllByUnidad("UTF"));
			model.addAttribute("procname", "ActCargo");
			model.addAttribute("formTab", "active");
		}
		catch(Exception e) {
			Gplanta funcionario = new Gplanta();
			Gcargo cargoActual = new Gcargo();
			CargoNuevoForm cargoNuevoForm = new CargoNuevoForm();
			cargoNuevoForm.setFuncionario(funcionario);
			cargoNuevoForm.setCargoActual(cargoActual);
			model.addAttribute("cargoNuevoForm", cargoNuevoForm);
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("cargosList", gcargoRepository.findAll());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("procname", "ActCargo");
			model.addAttribute("formTab", "active");			
		}
		
		return "cambio-de-cargo/cargos-view";
	}

	@GetMapping("/getInfo/{id}")
	public String traerInformacion(@PathVariable(name="id") Long idfunc, ModelMap model) {
		try {
			Gplanta funcionario = gplantaRepository.getOne(idfunc);
			Gcargo cargo = funcionario.getGcargo();
			CargoNuevoForm cargoNuevoForm = new CargoNuevoForm();
			cargoNuevoForm.setCargoActual(cargo);
			cargoNuevoForm.setFuncionario(funcionario);
			cargoNuevoForm.setIdgplanta(funcionario.getIdgplanta());
			model.addAttribute("cargoNuevoForm", cargoNuevoForm);
			model.addAttribute("conInformacion", true);
			model.addAttribute("cargosList", gcargoRepository.findAll());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("plantaSMList", gplantaService.getAllByUnidad("SM"));
			model.addAttribute("plantaUTFList", gplantaService.getAllByUnidad("UTF"));
			model.addAttribute("procname", "ActCargo");
			model.addAttribute("formTab", "active");			
		}
		catch(Exception e) {
			Gplanta funcionario = new Gplanta();
			CargoNuevoForm cargoNuevoForm = new CargoNuevoForm();
			cargoNuevoForm.setFuncionario(funcionario);
			cargoNuevoForm.setIdgplanta(funcionario.getIdgplanta());
			model.addAttribute("cargoNuevoForm", cargoNuevoForm);
			model.addAttribute("conInformacion", false);
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("cargosList", gcargoRepository.findAll());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("procname", "ActCargo");
			model.addAttribute("formTab", "active");			
			
		}
		return "cambio-de-cargo/cargos-view";
	}
	
	@RequestMapping(value= {"/nuevoCargo"}, params= {"guardar"})
	public String actualizarCargo(final CargoNuevoForm cargoNuevoForm, final BindingResult results, Model model) {
		
		if(results.hasErrors()) {
			model.addAttribute("formError", results.getAllErrors().toString());
			model.addAttribute("cargoNuevoForm", cargoNuevoForm);
			model.addAttribute("cargosList", gcargoRepository.findAll());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("procname", "ActCargo");
			model.addAttribute("formTab", "active");			
			return "cambio-de-cargo/cargos-view";
		}
		try {
			Long idfunc = cargoNuevoForm.getIdgplanta();
			Integer idnewCargo = cargoNuevoForm.getIdgcargo();
			String fechaAcceso = cargoNuevoForm.getFechaDeAcceso();
			String firstBasico = cargoNuevoForm.getBasico();
			String firstComplemento = cargoNuevoForm.getComplemento();
			if(firstComplemento.isEmpty()) {
				firstComplemento = "0";
			}
			Gplanta funcionario = gplantaService.actualizarCargo(idfunc, idnewCargo, fechaAcceso, firstBasico, firstComplemento);
			cargoNuevoForm.setFuncionario(funcionario);
			Gcargo cargo = funcionario.getGcargo();
			cargoNuevoForm.setCargoActual(cargo);
			cargoNuevoForm.setFuncionario(funcionario);
			model.addAttribute("cargoNuevoForm", cargoNuevoForm);
			model.addAttribute("conInformacion", true);
			model.addAttribute("cargosList", gcargoRepository.findAll());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("plantaSMList", gplantaService.getAllByUnidad("SM"));
			model.addAttribute("plantaUTFList", gplantaService.getAllByUnidad("UTF"));
			model.addAttribute("formSuccess", "El cargo se ha actualizado exitosamente!");
			model.addAttribute("procname", "ActCargo");
			model.addAttribute("formTab", "active");			
			
		} catch (Exception e) {
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("cargoNuevoForm", cargoNuevoForm);
			model.addAttribute("conInformacion", true);
			model.addAttribute("cargosList", gcargoRepository.findAll());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("procname", "ActCargo");
			model.addAttribute("formTab", "active");						
		}
		
		return "cambio-de-cargo/cargos-view";
	}
}
