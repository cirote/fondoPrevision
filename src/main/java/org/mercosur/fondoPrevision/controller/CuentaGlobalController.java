package org.mercosur.fondoPrevision.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.CuentaGlobalForm;
import org.mercosur.fondoPrevision.dto.CuentaGlobalSummary;
import org.mercosur.fondoPrevision.entities.CuentaGlobal;
import org.mercosur.fondoPrevision.excel.CuentaGlobalExcelExporter;
import org.mercosur.fondoPrevision.pdfs.CuentaGlobalPdfExporter;
import org.mercosur.fondoPrevision.service.CuentaGlobalService;
import org.mercosur.fondoPrevision.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;

@Controller
public class CuentaGlobalController {

	@Autowired
	CuentaGlobalService cuentaGlobalService;
	
	@Autowired
	ParamService paramService;
	
	
	public CuentaGlobalController() {
		super();
	}
	
	@GetMapping("/cuentaGlobalForm")
	public String getCtaGlobalForm(Model model) {
		try {
			String mesliquidacion = paramService.getMesliquidacion();
			List<CuentaGlobal> lstResumen = cuentaGlobalService.getResumenByMes();
			BigDecimal sumaTotal = cuentaGlobalService.getSumaTotalLastYear(mesliquidacion);
			
			CuentaGlobalForm cuentaGlobalForm = new CuentaGlobalForm(mesliquidacion, lstResumen, sumaTotal);
			model.addAttribute("cuentaGlobalForm", cuentaGlobalForm);
			model.addAttribute("outputMode", false);
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
		}
		return ("cuenta-global/cuenta-global-form");
	}
	
	@RequestMapping("/cuentaDetalle/{mesliquidacion}")
	public String getDetalleByMes(@PathVariable(name="mesliquidacion") String mesliquidacion, Model model) {
		try {
			List<CuentaGlobal> lstResumen = cuentaGlobalService.getResumenByMes();
			BigDecimal sumaTotal = cuentaGlobalService.getSumaTotalLastYear(mesliquidacion);
			List<CuentaGlobalSummary> lstDetalle = cuentaGlobalService.getDetalleByOneMes(mesliquidacion);
			CuentaGlobalForm cuentaGlobalForm = new CuentaGlobalForm(mesliquidacion, lstResumen, sumaTotal);
			cuentaGlobalForm.setLstDetalle(lstDetalle);
			model.addAttribute("cuentaGlobalForm", cuentaGlobalForm);
			model.addAttribute("outputMode", true);
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
		}
		return ("cuenta-global/cuenta-global-form");
	}

	
	private Map<String, List<CuentaGlobalSummary>> createMap(List<CuentaGlobal> resumen) throws Exception{
		Map<String, List<CuentaGlobalSummary>> ctaGlobalMap = new HashMap<String, List<CuentaGlobalSummary>>();
		for(CuentaGlobal cg : resumen) {
			String key = cg.getMesliquidacion() + " - " + getAsString(cg.getImporte());
			List<CuentaGlobalSummary> detalles = cuentaGlobalService.getDetalleByOneMes(cg.getMesliquidacion());
			ctaGlobalMap.put(key, detalles);
		}
		return ctaGlobalMap;
	}

	private String getAsString(BigDecimal nro){
		if(nro == null){
			return null;
		}
		BigDecimal remainder = ((BigDecimal) nro).remainder(BigDecimal.ONE);
		String digitocero = remainder.toString();
		digitocero = digitocero.substring(digitocero.length() - 1);
		
		if(remainder.abs().compareTo(BigDecimal.ZERO) > 0){
			if(digitocero.equals("0")){
				return  NumberFormat.getInstance(Locale.getDefault()).format(nro) + "0";					
			}
			return  NumberFormat.getInstance(Locale.getDefault()).format(nro);
		}
		else{
			return NumberFormat.getInstance(Locale.getDefault()).format(nro) + ",00";
		}

	}

	@GetMapping("/pdfCtaGlobalExport")
	public void exportPdfCtaGlogal(HttpServletResponse response) throws DocumentException, IOException, Exception{
		
		List<CuentaGlobal> lstResumen = cuentaGlobalService.getResumenByMes();
		String mesliquidacion = paramService.getMesliquidacion();
		BigDecimal sumaTotal = cuentaGlobalService.getSumaTotalLastYear(mesliquidacion);
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		Map<String, List<CuentaGlobalSummary>> ctaGlobalMap = createMap(lstResumen);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ctaglobalreport_" + currDate + ".pdf";
		
		response.setHeader(headerKey, headerValue);
		CuentaGlobalPdfExporter exporter = new CuentaGlobalPdfExporter(lstResumen, sumaTotal, mesliquidacion, ctaGlobalMap);
		exporter.export(response);

	}
	
	@GetMapping("/excelCtaGlobalExport")
	public void exportExcelCtaGlobal(HttpServletResponse response) throws IOException, Exception{
		List<CuentaGlobal> lstResumen = cuentaGlobalService.getResumenByMes();
		String mesliquidacion = paramService.getMesliquidacion();
		BigDecimal sumaTotal = cuentaGlobalService.getSumaTotalLastYear(mesliquidacion);
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		Map<String, List<CuentaGlobalSummary>> ctaGlobalMap = createMap(lstResumen);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ctaglobal_" + currDate + ".xlsx";
		
		response.setHeader(headerKey, headerValue);
		CuentaGlobalExcelExporter exporter = new CuentaGlobalExcelExporter(lstResumen, sumaTotal, mesliquidacion, ctaGlobalMap);
		exporter.export(response);
	
	}
}
