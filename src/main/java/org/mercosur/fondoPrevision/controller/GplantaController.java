package org.mercosur.fondoPrevision.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.service.GplantaService;

@Controller
public class GplantaController {

	@Autowired
	GplantaService gplantaService;
	
	
	@GetMapping("/plantaForm")
	public String getPlanta(Model model) {
		model.addAttribute("plantaForm", new Gplanta());
		model.addAttribute("plantaList", gplantaService.getAllPlanta());
		
		return "funcionarios/planta-view";
	}
}
