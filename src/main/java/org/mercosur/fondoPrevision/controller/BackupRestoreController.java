package org.mercosur.fondoPrevision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.mercosur.fondoPrevision.dto.RestauraForm;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.repository.ProcedimientoRepository;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.RunBatFileService;

@Controller
public class BackupRestoreController {
	
	@Autowired
	RunBatFileService runBatFileService;
	
	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	ProcedimientoRepository procedimientoRepository;
	
	@GetMapping("/backupForm")
	public String getProc(Model model, @RequestParam(value="procname") String name) {
		try {
			String clave = "frespmesSH";
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);				
		}
		catch(Exception e) {
			model.addAttribute("respErrorMessage", e.getMessage());
		}
				
		model.addAttribute("procname", name);
		
		return "backuprestore/backuprestore";
	}
	
	@GetMapping("/restoreForm")
	public String getRestore(Model model) {
		try {
			String clave = "frestinf";
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);
			model.addAttribute("restauraForm", new RestauraForm("Intermedio"));
			model.addAttribute("procsList", procedimientoRepository.findAll());
			model.addAttribute("procname", "Intermedio");
		}
		catch(Exception e) {
			model.addAttribute("formErrorMessage", e.getMessage());
		}
		return("backuprestore/restore-form");
	}
}
