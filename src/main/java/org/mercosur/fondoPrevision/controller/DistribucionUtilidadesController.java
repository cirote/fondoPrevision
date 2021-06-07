package org.mercosur.fondoPrevision.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.DistribForm;
import org.mercosur.fondoPrevision.dto.ResultDistribucion;
import org.mercosur.fondoPrevision.dto.ResultadoDistribSummary;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.entities.DatosDistribucion;
import org.mercosur.fondoPrevision.excel.DistribExcelExporter;
import org.mercosur.fondoPrevision.pdfs.DistribucionPdfExporter;
import org.mercosur.fondoPrevision.repository.MovimientosHistRepository;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.CuentaGlobalService;
import org.mercosur.fondoPrevision.service.DistribucionUtilidadesService;
import org.mercosur.fondoPrevision.service.GplantaService;
import org.mercosur.fondoPrevision.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.DocumentException;

@Controller
public class DistribucionUtilidadesController {

	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	GplantaService gplantaService;

	@Autowired
	ParamService paramService;
	
	@Autowired
	CuentaGlobalService cuentaGlobalService;
	
	@Autowired
	MovimientosHistRepository movimientoshistRepository;
	
	@Autowired
	DistribucionUtilidadesService distribucionUtilidadesService;
						
	
	@GetMapping("/distribForm")
	public String getDistribForm(Model model) {
		String clave = "fdist";
		try {
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);
			model.addAttribute("distribForm", new DistribForm());
			model.addAttribute("outputMode", false);
			
		}
		catch(Exception e) {
			model.addAttribute("distribForm", new DistribForm());
			model.addAttribute("formError", e.getMessage());
		}
				
		model.addAttribute("procname", "Intermedio");
		return ("/distribucion-utilidades/distribucion-form");
	}

	@RequestMapping(value= {"/ejecutarDistribucion"}, params = {"saldoctaglobal"})
	public String traersaldos(final DistribForm distribForm, 
							final BindingResult results, Model model) {
		
		// Trae información de la Cuenta Global que es la que se alimenta mes a mes
		// con los intereses de los préstamos.
		if(results.hasErrors()) {
			model.addAttribute("ditribForm", distribForm);
		}
		else {
			try {
				if(!distribForm.getMesInicial().matches("\\d{2}/(\\d{4})")){
					model.addAttribute("ditribForm", new DistribForm());
					model.addAttribute("formError", "El formato del mes Inicial no es el correcto");
					return("/distribucion-utilidades/distribucion-form");
				}
				else if(!distribForm.getMesFinal().matches("\\d{2}/(\\d{4})")) {
					model.addAttribute("ditribForm", new DistribForm());
					model.addAttribute("formError", "El formato del mes Final no es el correcto");
					return("/distribucion-utilidades/distribucion-form");
				}
				else {
					String aniomesini = distribForm.getMesInicial().substring(3).concat(distribForm.getMesInicial().substring(0, 2));
					String aniomesfin = distribForm.getMesFinal().substring(3).concat(distribForm.getMesFinal().substring(0, 2));
					if(!validacionPeriodo(aniomesini, aniomesfin)) {
						model.addAttribute("ditribForm", new DistribForm());
						model.addAttribute("formError", "El mes Final debe ser posterior al Inicial!");
						return("/distribucion-utilidades/distribucion-form");
					}
					else {
						BigDecimal saldoCtaGlobal = cuentaGlobalService.getSumaByPeriodo(aniomesini, aniomesfin);
						distribForm.setSaldoCtaGlobal(saldoCtaGlobal);
						model.addAttribute("distribForm", distribForm);
						
					}
				}
			}
			catch(Exception e) {
				model.addAttribute("distribForm", distribForm);
				model.addAttribute("formError", e.getMessage());
			}
		}
		model.addAttribute("procname", "Intermedio");
		return ("/distribucion-utilidades/distribucion-form");
	}
	
	private Boolean validacionPeriodo(String aniomesini, String aniomesfin) {
		Integer valor1 = Integer.valueOf(aniomesini);
		Integer valor2 = Integer.valueOf(aniomesfin);
		if(valor2 > valor1) {
			return true;
		}
		return false;
	}

	private Boolean anioMesMayorMesLiquidacion(String mes, String mesLiq){
		Integer nanio = Integer.valueOf(mesLiq.substring(0,4));
		Integer nmes = Integer.valueOf(mesLiq.substring(4));
		
		if(Integer.valueOf(mes.substring(3)) > nanio){
			return true;
		}
		else if(Integer.valueOf(mes.substring(3)) == nanio && Integer.valueOf(mes.substring(0,2)) > nmes){
			return true;
		}
		return false;
	}
	
	private String filtrarEgresos(String mesliquid) {
		try {
			List<Integer> lstEgresos = movimientoshistRepository.getTarjetasEgresos(mesliquid);
			String tarQL = "";
			for(Integer t:lstEgresos) {
				tarQL += tarQL.equals("")? t : ", "+t;
			}
			return tarQL;
		}
		catch(Exception e) {
			return "";
		}
	}
	
	private String filtrarIngresos(Date fecha){
		try {
			List<Integer> lstIngresos = gplantaService.getTarjetasIngresos(fecha);
			String tarQL = "";
			for(Integer t:lstIngresos){
				tarQL += tarQL.equals("")?t:", "+t;
			}
			return tarQL;			
		}
		catch(Exception e) {
			return "";
		}
	}

	@RequestMapping(value= {"/ejecutarDistribucion"}, params = {"distribuir"})
	public String realizarDistribucion(final DistribForm distribForm, 
							final BindingResult results, Model model) {
		String mesLiquidacion = paramService.getMesliquidacion();
		
		String mes1 = distribForm.getMesInicial();
		String mes2 = distribForm.getMesFinal();
		
		if(distribForm.getTotalAdistrib() == null) {
			model.addAttribute("distribForm", new DistribForm());
			model.addAttribute("outputMode", false);
			model.addAttribute("formError", "No se ha ingresado el Total a Distribuir!.");
			return("/distribucion-utilidades/distribucion-form");
			
		}

		if(anioMesMayorMesLiquidacion(mes1, mesLiquidacion) || anioMesMayorMesLiquidacion(mes2, mesLiquidacion)) {
			model.addAttribute("ditribForm", new DistribForm());
			model.addAttribute("outputMode", false);
			model.addAttribute("formError", "Los límites del período no pueden ser posteriores al Mes Liquidación!.");
			return("/distribucion-utilidades/distribucion-form");		
		}
				
		String aniomes1 = mes1.substring(3) + mes1.substring(0, 2);
		String aniomes2 = mes2.substring(3) + mes2.substring(0, 2);
		String litMesFinEjercicio = FuncionesUtiles.literalMesAnio(aniomes2.substring(4), aniomes2.substring(0, 4));
		
		String dia2 = FuncionesUtiles.ultimoDiadelMes(mes2.substring(0,2), mes2.substring(3));
		Date fechatope = FuncionesUtiles.convertirFecha(dia2, mes2.substring(0,2), mes2.substring(3));
		
		String tarjetasQL =  filtrarIngresos(fechatope);	//deja afuera los ingresos posteriores a la fecha tope.
						//filtrarEgresos deja afuera los egresos del último mes del ejercicio.
		tarjetasQL += tarjetasQL.equals("")? filtrarEgresos(aniomes2):tarjetasQL + ", " + filtrarEgresos(aniomes2);
		
		try{
			BigDecimal sumaNumerales = BigDecimal.ZERO;
			
			if(tarjetasQL.isEmpty()) {
							// Obtiene la suma total de los numerales (base de distribución) del mes final
							// del ejercicio
				sumaNumerales = distribucionUtilidadesService.getSumaNumeralesSinDistribucion(aniomes2);				
			}
			else {
						// Si hubo egresos se obtiene la base de distribución sin tomar en cuenta los egresos.
				sumaNumerales = distribucionUtilidadesService.getSumaNumeralesPeriodoyTarjetas(aniomes2, tarjetasQL);
			}

			BigDecimal bdSuma = distribForm.getTotalAdistrib();
			
			DatosDistribucion datosDistrib = new DatosDistribucion();

			if(distribucionUtilidadesService.distribucionRealizada(aniomes2)){
				model.addAttribute("ditribForm", new DistribForm());
				model.addAttribute("outputMode", false);
				model.addAttribute("formError", "ya se realizó la distribución correspondiente a mes: " + litMesFinEjercicio);
				return("/distribucion-utilidades/distribucion-form");		
			}
			else{
				datosDistrib = distribucionUtilidadesService.salvarDatosDistribucion(aniomes2, bdSuma, sumaNumerales);
			}

			List<ResultadoDistribSummary> resultado = distribucionUtilidadesService.distribuirUtilidades(tarjetasQL, aniomes1, aniomes2, datosDistrib);

			model.addAttribute("ditribForm", distribForm);
			model.addAttribute("resultadoList", resultado);
			model.addAttribute("outputMode", true);
		}
		catch(Exception ex){
			model.addAttribute("ditribForm", new DistribForm());
			model.addAttribute("outputMode", false);
			model.addAttribute("formError", ex.getMessage());
			return("/distribucion-utilidades/distribucion-form");		
		}
		
		model.addAttribute("procname", "Intermedio");
		return ("/distribucion-utilidades/distribucion-form");

	}
	
	
	@GetMapping("/consultaDistribucion")
	public String getListaMeses(Model model) {
		String clave = "fconsUtil";
		try {
			List<String> meses = distribucionUtilidadesService.getMesesDistribucion();
			
			model.addAttribute("help", ayudaService.getByClave(clave));
			model.addAttribute("mesesList", FuncionesUtiles.mesesConForma(meses));
			model.addAttribute("resultD", new ResultDistribucion());
			model.addAttribute("outputMode", false);
			
		}
		catch(Exception e) {
			model.addAttribute("resultD", new ResultDistribucion());
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("outputMode", false);
		}
		
		return "distribucion-utilidades/consulta-distribucion";

	}

	@RequestMapping(value= {"/resultDistrib"}, params= {"getInfo"})
	public String getResultDistrib(final ResultDistribucion resultD, final BindingResult result, Model model) {
		try {
			String mesDistrib = resultD.getAnioMesDistrib().substring(3) + resultD.getAnioMesDistrib().substring(0, 2);
		
			if(result.hasErrors()) {
				model.addAttribute("formError", result.getAllErrors().toString());
			}
			else {
				try {
					List<String> meses = distribucionUtilidadesService.getMesesDistribucion();
					DatosDistribucion datosDistrib = distribucionUtilidadesService.getDatosDistribucion(mesDistrib);
					resultD.setTotalNumeralesAct(distribucionUtilidadesService.getSumaNumeralesConDistribucion(mesDistrib));
					resultD.setTotalNumeralesAnt(datosDistrib.getTotBaseDistrib());
					resultD.setSumaADistribuir(datosDistrib.getResultadoADistrib());
					model.addAttribute("lstResult", distribucionUtilidadesService.obtenerResultados(mesDistrib));
					model.addAttribute("outputMode", true);
					model.addAttribute("resultD", resultD);
					model.addAttribute("mesesList", FuncionesUtiles.mesesConForma(meses));				
				}
				catch(Exception e) {
					System.out.println("Exception SumaNumeralesSinDistrib " + e.getMessage());
					model.addAttribute("formError", e.getMessage());
				}
			}
		}
		catch(Exception e) {
			System.out.println("Excepcion en getResultDistrib " + e.getMessage());
		}
		
		return "distribucion-utilidades/consulta-distribucion";
	}
	
	@GetMapping("/pdfDistribucion")
	public void exportResultadoDistribucion(HttpServletResponse response, @RequestParam(value="mesdistrib", required=true) String mesDist, Model model) 
			throws DocumentException, IOException, Exception{
		String mesDistrib = mesDist.substring(3) + mesDist.substring(0, 2);

		DatosDistribucion datosDistrib = distribucionUtilidadesService.getDatosDistribucion(mesDistrib);
		List<ResultadoDistribSummary> lstResult = distribucionUtilidadesService.obtenerResultados(mesDistrib);
		BigDecimal totalNumeralesAnt = datosDistrib.getTotBaseDistrib();
		BigDecimal totalNumeralesAct = distribucionUtilidadesService.getSumaNumeralesConDistribucion(mesDistrib);
		BigDecimal sumaAdistrib = datosDistrib.getResultadoADistrib();
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=distribucionreport_" + currDate + ".pdf";
		
		response.setHeader(headerKey, headerValue);
		DistribucionPdfExporter exporter = new DistribucionPdfExporter(lstResult, totalNumeralesAnt, totalNumeralesAct,
				mesDistrib, sumaAdistrib);
		exporter.export(response);

	}

	@GetMapping("/excelDistribucion")
	public void excelResultadoDistribucion(HttpServletResponse response, @RequestParam(value="mesdistrib", required=true) String mesDist, Model model) throws IOException, Exception {
		String mesDistrib = mesDist.substring(3) + mesDist.substring(0, 2);

		DatosDistribucion datosDistrib = distribucionUtilidadesService.getDatosDistribucion(mesDistrib);
		List<ResultadoDistribSummary> lstResult = distribucionUtilidadesService.obtenerResultados(mesDistrib);
		BigDecimal totalNumeralesAnt = datosDistrib.getTotBaseDistrib();
		BigDecimal totalNumeralesAct = distribucionUtilidadesService.getSumaNumeralesConDistribucion(mesDistrib);
		BigDecimal sumaAdistrib = datosDistrib.getResultadoADistrib();
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=distribucion_" + currDate + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		DistribExcelExporter exporter = new DistribExcelExporter(lstResult, totalNumeralesAnt, totalNumeralesAct, mesDistrib, sumaAdistrib);
		
		exporter.export(response);
	}
}

