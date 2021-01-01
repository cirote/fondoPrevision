package org.mercosur.fondoPrevision.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.CuotasPagas;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.pdfs.CuotasPagasPdfExporter;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.GplantaService;
import org.mercosur.fondoPrevision.service.PagoDeCuotasService;
import org.mercosur.fondoPrevision.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;

@Controller
public class PagoDeCuotasController {

	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	GplantaService gplantaService;
	
	@Autowired
	PagoDeCuotasService pagocuotasService;
	
	@Autowired
	ParamService paramService;
	
	@GetMapping("/cuotasForm")
	public String getPagoDeCuotasForm(Model model) {
		try {
			String clave = "fpagoCuotas";
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);				
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
		}
				
		model.addAttribute("procname", "Intermedio");
		return ("pagodecuotas/cuotas-form");
	}
	
	@RequestMapping(value="/ejecutarPago", params ={"executePago"})
	public String pagarCuotas(Model model) {
		try {
			pagocuotasService.pagoDeCuotas();
			pagocuotasService.consolidacionDeSaldos(paramService.getMesliquidacion());
			gplantaService.actualizarUltimosIngresos();
			model.addAttribute("formSuccess", "El procedimiento se ha ejecutado con éxito!.");
			model.addAttribute("cuotasPagasList", pagocuotasService.resultadoPagoDeCuotas());
			model.addAttribute("outputMode", true);
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
		}
		return("/pagodecuotas/cuotas-form");
	}
	
	@GetMapping("/pdfCuotasReport")
    public void cuotasPagasReport(HttpServletResponse response) throws DocumentException, IOException, Exception{
		List<CuotasPagas> cuotasLst = new ArrayList<CuotasPagas>();
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		
		String mesLiquidacion = paramService.getMesliquidacion();
        cuotasLst = pagocuotasService.resultadoPagoDeCuotas();
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=cuotasreport_" + currDate + ".pdf";
		
		response.setHeader(headerKey, headerValue);
		CuotasPagasPdfExporter exporter = new CuotasPagasPdfExporter(cuotasLst, mesLiquidacion);
		exporter.export(response);
     }

}
