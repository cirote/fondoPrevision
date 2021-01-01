package org.mercosur.fondoPrevision.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.CapitalesForDisplay;
import org.mercosur.fondoPrevision.dto.CapitalesForm;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.excel.CapitalesExcelExporter;
import org.mercosur.fondoPrevision.pdfs.CapitalesPdfExporter;
import org.mercosur.fondoPrevision.repository.AyudaRepository;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.repository.ResultadoDistribucionRepository;
import org.mercosur.fondoPrevision.service.ConsultaCapitalesService;
import org.mercosur.fondoPrevision.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.DocumentException;

@Controller
public class CapitalesDisponiblesController {

	@Autowired
	MovimientosRepository movimientosRepository;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Autowired
	ResultadoDistribucionRepository resultadoDistribRepository;
		
	@Autowired
	ConsultaCapitalesService consultaCapitalesService;
	
	@Autowired
	ParamService paramService;
	
	@Autowired
	AyudaRepository ayudaRepository;
	
	@ModelAttribute("plantaList")
	public List<Gplanta> populatePlanta(){
		return (List<Gplanta>) gplantaRepository.findAll();
	}

	public CapitalesDisponiblesController() {
		super();
	}
	
	
	@GetMapping("/consultaCapitales")
	public String getCapitalesForm(ModelMap model) {
		
		String clave = "fconscap";
		List<String> meses = movimientosRepository.getMesesLiquidacion();
		try {
			model.addAttribute("help", ayudaRepository.findByClave(clave));
			model.addAttribute("outputMode", false);
			model.addAttribute("procname", "Ajustes");
			model.addAttribute("capitalesForm", new CapitalesForm());
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("capForDisplay", new CapitalesForDisplay());
		}
		catch(Exception e) {
			model.addAttribute("help", ayudaRepository.findByClave(clave));
			model.addAttribute("outputMode", false);
			model.addAttribute("capitalesForm", new CapitalesForm());
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("formError", e.getMessage());
		}

		return "consultas/capitales-form";
	}
	
	private List<String> mesesConForma(List<String> meses){
		List<String> lstmeses = new ArrayList<String>();
		String mm;
		for(String mes:meses) {
			mm = mes.substring(4) + "/" + mes.substring(0, 4);
			lstmeses.add(mm);
		}		
		return lstmeses;
	}
	
	@RequestMapping(value= {"/getCapitales"}, params= {"getInfo"})
	public String traerInformacion(final CapitalesForm capForm, final BindingResult result, Model model) {
		String clave = "fconscap";
		List<String> meses = movimientosRepository.getMesesLiquidacion();
		List<String> mesesDist = resultadoDistribRepository.getMesesDistribucion();
		try {
			String mesliquidacion = capForm.getMesliquidacion().substring(3) + capForm.getMesliquidacion().substring(0, 2);
			List<CapitalesForDisplay> lstcfd = consultaCapitalesService.getCapitalesByMesliquidacion(mesliquidacion);
			if(lstcfd == null || lstcfd.isEmpty()) {
				model.addAttribute("formError", "No hay información disponible para el mes elegido");
			}
			Optional<String> mesdist = mesesDist.stream().filter(md -> mesliquidacion.equals(md)).findFirst();
			if(mesdist.isPresent()) {
				model.addAttribute("conDistribucion", true);
			}
			else {
				model.addAttribute("conDistribucion", false);
			}
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("capitalesForm", capForm);
			model.addAttribute("outputMode", true);
			model.addAttribute("procname", "Ajustes");
			model.addAttribute("capitalesList", lstcfd);
			model.addAttribute("capForDisplay", new CapitalesForDisplay());
			
		}
		catch(Exception e) {
			model.addAttribute("help", ayudaRepository.findByClave(clave));
			model.addAttribute("outputMode", false);
			model.addAttribute("capitalesForm", capForm);
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("conDistribucion", false);
			model.addAttribute("formError", e.getMessage());
			
		}
		return "consultas/capitales-form";
	}

	@RequestMapping(value= {"/realizarAjuste"}, params= {"salvarAjuste"})
	public String getsaveAjuste(final CapitalesForDisplay capForDisplay, final BindingResult result, Model model) throws Exception{
		List<String> meses = movimientosRepository.getMesesLiquidacion();
		String clave = "fconscap";
	
		String mesParam = paramService.getMesliquidacion();
		if(!mesParam.equals(capForDisplay.getMesliquidacion())) {
			model.addAttribute("help", ayudaRepository.findByClave(clave));
			model.addAttribute("outputMode", false);
			model.addAttribute("procname", "Ajustes");
			model.addAttribute("capitalesForm", new CapitalesForm());
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("conDistribucion", false);
			model.addAttribute("formError", "Solo se admiten ajuste sobre el último mes procesado!");	
			return "consultas/capitales-form";
		}
		String mesliquidacion = capForDisplay.getMesliquidacion();
		List<CapitalesForDisplay> lstcfd = consultaCapitalesService.realizarAjuste(capForDisplay);
		List<String> mesesDist = resultadoDistribRepository.getMesesDistribucion();
		CapitalesForm capForm = new CapitalesForm();
		capForm.setMesliquidacion(mesliquidacion.substring(4) + "/" + mesliquidacion.substring(0, 4));
		try {
			if(lstcfd == null || lstcfd.isEmpty()) {
				model.addAttribute("formError", "No hay información disponible para el mes elegido");
			}
			Optional<String> mesdist = mesesDist.stream().filter(md -> mesliquidacion.equals(md)).findFirst();
			if(mesdist.isPresent()) {
				model.addAttribute("conDistribucion", true);
			}
			else {
				model.addAttribute("conDistribucion", false);
			}
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("capitalesForm", capForm);
			model.addAttribute("outputMode", true);
			model.addAttribute("procname", "Ajustes");
			model.addAttribute("capitalesList", lstcfd);
			model.addAttribute("capForDisplay", new CapitalesForDisplay());
			
		}
		catch(Exception e) {
			model.addAttribute("help", ayudaRepository.findByClave(clave));
			model.addAttribute("outputMode", false);
			model.addAttribute("capitalesForm", capForm);
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("conDistribucion", false);
			model.addAttribute("formError", e.getMessage());
			
		}
		return "consultas/capitales-form";
	}
	
	@RequestMapping("/pdfCapitalesReport")
    public void movimientosReport(HttpServletResponse response, 
    		@RequestParam(value="mesliquidacion", required=true) String mesliquidacion, Model model) throws DocumentException, IOException, Exception{
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		String mesinicial = mesliquidacion.substring(3) + mesliquidacion.substring(0, 2);
		Boolean conDistribucion = false;
		
		List<String> mesesDist = resultadoDistribRepository.getMesesDistribucion();
		Optional<String> mesdist = mesesDist.stream().filter(md -> mesinicial.equals(md)).findFirst();
		if(mesdist.isPresent()) {
			conDistribucion = true;
		}
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=capitalesReport_" + currDate + ".pdf";
		
		try {
			List<CapitalesForDisplay> lstCapitales = consultaCapitalesService.getCapitalesByMesliquidacion(mesinicial);
			response.setHeader(headerKey, headerValue);
			CapitalesPdfExporter exporter = new CapitalesPdfExporter(lstCapitales, mesliquidacion, conDistribucion);
			exporter.export(response);
			
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
     }

	@RequestMapping("/excelCapitales")
	public void capitalesToExcel(HttpServletResponse response, 
			@RequestParam(value="mesliquidacion", required=true) String mesliquidacion, Model model) throws IOException, Exception{

		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		String mesinicial = mesliquidacion.substring(3) + mesliquidacion.substring(0, 2);
		Boolean conDistribucion = false;
		
		List<String> mesesDist = resultadoDistribRepository.getMesesDistribucion();
		Optional<String> mesdist = mesesDist.stream().filter(md -> mesinicial.equals(md)).findFirst();
		if(mesdist.isPresent()) {
			conDistribucion = true;
		}

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=capitales_" + currDate + ".xlsx";
		
		try {
			List<CapitalesForDisplay> lstCapitales = consultaCapitalesService.getCapitalesByMesliquidacion(mesinicial);
			response.setHeader(headerKey, headerValue);
			CapitalesExcelExporter exporter = new CapitalesExcelExporter(lstCapitales, mesliquidacion, conDistribucion);
			exporter.export(response);
			
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}

	}
}
