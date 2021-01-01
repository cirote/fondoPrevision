package org.mercosur.fondoPrevision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.mercosur.fondoPrevision.repository.LogfondoRepository;
import org.mercosur.fondoPrevision.service.LogfondoService;

@Controller
public class LogfondoController {

	@Autowired
	LogfondoRepository logfondoRepository;
	
	@Autowired
	LogfondoService logfondoService;
	
	@GetMapping("/logForm")
	public String getLogs(Model model) {
		model.addAttribute("listLog", logfondoService.getAllLogfondo());
		return "logs/logs-list";
	}
}
