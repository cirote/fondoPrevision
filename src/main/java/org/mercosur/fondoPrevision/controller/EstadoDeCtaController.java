package org.mercosur.fondoPrevision.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.mercosur.fondoPrevision.dto.EstadoDeCta;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.User;
import org.mercosur.fondoPrevision.pdfs.EstadoDeCtaPdfExporter;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.EstadoDeCtaService;
import org.mercosur.fondoPrevision.service.GplantaService;
import org.mercosur.fondoPrevision.service.ParamService;
import org.mercosur.fondoPrevision.service.UserService;

import com.lowagie.text.DocumentException;

@Controller
public class EstadoDeCtaController {

	@Autowired
	GplantaService gplantaService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	EstadoDeCtaService estadoDeCtaService;
	
	@Autowired
	ParamService parametroService;
		
	@GetMapping("/estadoDeCtaForm")
	public String getEstadoDeCta(Model model) {
		
		String clave = "festadocta";
		try {
			model.addAttribute("help", ayudaService.getByClave(clave));
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("estadoDeCta", new EstadoDeCta());
			model.addAttribute("outputMode", false);
			
		}
		catch(Exception e) {
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("estadoDeCta", new EstadoDeCta());
			model.addAttribute("formError", e.getMessage());
		}
		
		return "estado-cta/estadocta-form";
	}
	
	@GetMapping("/estadoDeCtaUserForm")
	public String getEstadoCtaUser(Model model) {
		try {
			User user = userService.getLoggedUser();
			Gplanta funcionario = gplantaService.getFuncionarioByTarjeta(user.getTarjeta());
			EstadoDeCta newEstado = estadoDeCtaService.getEstadoDeCtabyFuncionario(funcionario.getIdgplanta());
			if(newEstado.getConDistribucion()) {
				String mes = newEstado.getMesDistribucion().substring(4);
				String anio = newEstado.getMesDistribucion().substring(0, 4);
				LocalDate fechaBalance = FuncionesUtiles.strfechaTolocaldate(FuncionesUtiles.ultimoDiadelMes(mes, anio), mes, anio);
				model.addAttribute("labelBalance", "Asignación de Intereses por Distribución de Utilidades al ");
				model.addAttribute("fechaBalance", fechaBalance);
			}
			model.addAttribute("estadoDeCta", newEstado);
			model.addAttribute("outputMode", true);
		}
		catch(Exception e) {
			model.addAttribute("estadoDeCta", new EstadoDeCta());
			model.addAttribute("formError", e.getMessage());
		}
		return "estado-cta/estadocta-form";
	}
	
	@RequestMapping(value= {"/estadoCtaFuncionario"}, params= {"getInfo"})
	public String traerInformacion(final EstadoDeCta estadoDeCta, final BindingResult result, Model model) {
		try {
			EstadoDeCta newEstado = estadoDeCtaService.getEstadoDeCtabyFuncionario(estadoDeCta.getIdfuncionario());
			if(newEstado.getConDistribucion()) {
				String mes = newEstado.getMesDistribucion().substring(4);
				String anio = newEstado.getMesDistribucion().substring(0, 4);
				LocalDate fechaBalance = FuncionesUtiles.strfechaTolocaldate(FuncionesUtiles.ultimoDiadelMes(mes, anio), mes, anio);
				model.addAttribute("labelBalance", "Asignación de Intereses por Distribución de Utilidades al ");
				model.addAttribute("fechaBalance", fechaBalance);
			}
			model.addAttribute("estadoDeCta", newEstado);
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("outputMode", true);
		}
		catch(Exception e) {
			model.addAttribute("estadoDeCta", new EstadoDeCta());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("formError", e.getMessage());
		}
		
		return "estado-cta/estadocta-form";
	}
	
	@GetMapping("/estado/cancel")
	public String cancelEstado(ModelMap model) {
		return "redirect:/estadoDeCtaForm";
	}
	
	@RequestMapping("/pdfEstadoreport/{id}")
    public void estadoReport(HttpServletResponse response, @PathVariable(name="id") Long idfuncionario, Model model) throws DocumentException, IOException, Exception{
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		
		String mesLiquidacion = parametroService.getMesliquidacion();
		LocalDate fechaBalance = null;
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=estadoCtareport_" + currDate + ".pdf";
		
		try {
			EstadoDeCta newEstado = estadoDeCtaService.getEstadoDeCtabyFuncionario(idfuncionario);
			if(newEstado.getConDistribucion()) {
				String mes = newEstado.getMesDistribucion().substring(4);
				String anio = newEstado.getMesDistribucion().substring(0, 4);
				fechaBalance = FuncionesUtiles.strfechaTolocaldate(FuncionesUtiles.ultimoDiadelMes(mes, anio), mes, anio);
			}

			response.setHeader(headerKey, headerValue);
			EstadoDeCtaPdfExporter exporter = new EstadoDeCtaPdfExporter(newEstado, mesLiquidacion, fechaBalance);
			exporter.export(response);
			
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
     }

}
