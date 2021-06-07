package org.mercosur.fondoPrevision.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.mercosur.fondoPrevision.dto.AportesForm;
import org.mercosur.fondoPrevision.dto.AportesSummary;
import org.mercosur.fondoPrevision.dto.CapitalesForm;
import org.mercosur.fondoPrevision.dto.FuncionarioConSueldoMes;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.excel.AportesExcelExporter;
import org.mercosur.fondoPrevision.pdfs.AportesPdfExporter;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.CalculoAportesService;
import org.mercosur.fondoPrevision.service.GplantaService;
import org.mercosur.fondoPrevision.service.ParamService;
import org.mercosur.fondoPrevision.service.RunBatFileService;
import com.lowagie.text.DocumentException;

@Controller
public class AportesController {

	@Autowired
	RunBatFileService runBatFileService;
	
	@Autowired
	ParamService paramService;
	
	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	GplantaService gplantaService;

	@Autowired
	CalculoAportesService calculoAportesService;
	
	@Autowired
	MovimientosRepository movimientosRepository;
	
	@GetMapping("/aportesForm")
	public String getaportesForm(Model model) {
		
		try {
			String clave = "faportes";
			Ayuda help = ayudaService.getByClave(clave);
			model.addAttribute("help", help);
			model.addAttribute("aportesForm", new AportesForm());
			model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
			model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
		}
				
		model.addAttribute("procname", "Intermedio");
		model.addAttribute("aportesTab", "active");

		return "aportes/aportes-view";
	}
	
	@GetMapping("/editBasico/{tarjeta}")
	public String getEditBasico(Model model, @PathVariable("tarjeta") Integer tarjeta, final AportesForm aportesForm, 
			final BindingResult result) throws Exception{
		
		try {
			String mesLiquidacion = paramService.getMesliquidacion();
			aportesForm.setMesliquidacion(mesLiquidacion);
			FuncionarioConSueldoMes fun = gplantaService.getFuncionarioConSueldoMes(tarjeta);

			if(fun != null) {
				aportesForm.setFuncionario(fun);
				aportesForm.setTarjeta(fun.getTarjeta());
				model.addAttribute("aportesForm", aportesForm);
				model.addAttribute("basicoTab", "active");
			}
			else {
				model.addAttribute("aportesForm", new AportesForm());
				model.addAttribute("formError", "No se encontró al funcionario");
				model.addAttribute("aportesTab", "active");
			}

		}
		catch(Exception e) {
			model.addAttribute("aportesForm", new AportesForm());
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("aportesTab", "active");			
		}
		model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
		model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
		return("aportes/aportes-view");
	}
	
	@GetMapping("editBasico/cancel")
	public String canceEditBasico(Model model) {
		return "redirect:/aportesForm";
	}
	
	@PostMapping("/basicoMensual/{tarjeta}")
	public String modificarBasico(@PathVariable(name="tarjeta") Integer tarjeta, final AportesForm aportesForm, final BindingResult results, Model model) throws Exception {
		
		if(results.hasErrors()) {
			model.addAttribute("aportesForm", aportesForm);
			model.addAttribute("formError", results.getAllErrors().toString());
			model.addAttribute("basicoTab", "active");
		}
		else {
			try {
				Gplanta funcionario = gplantaService.getFuncionarioByTarjeta(tarjeta);
				if(calculoAportesService.actualizacionBasicoyComplemento(funcionario, 
						aportesForm.getMesliquidacion(), aportesForm.getBasico(), aportesForm.getComplemento())) {
					model.addAttribute("aportesForm", new AportesForm());
					model.addAttribute("formSuccess", "Se actualizó el básico y el complemento del funcionario");
					model.addAttribute("aportesTab", "active");
				}
				else {
					model.addAttribute("aportesForm", aportesForm);
					model.addAttribute("formError", "No fue posible realizar la actualización");
					model.addAttribute("basicoTab", "active");
				}
			}
			catch(Exception e) {
				model.addAttribute("formError", e.getMessage());
				model.addAttribute("basicoTab", "active");
			}
		}
		model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
		model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
		return("aportes/aportes-view");

	}
	
	@RequestMapping(value= {"/calculoAportes"}, params= {"aportes"})
	public String calcularAportes(final AportesForm aportesForm, final BindingResult results, Model model) throws Exception {
		String res = "";
		String mesliq = "";
		if(results.hasErrors()) {
			model.addAttribute("aportesForm", aportesForm);
			model.addAttribute("formError", results.getAllErrors().toString());
			model.addAttribute("aportesTab", "active");
		}
		else if(aportesForm.getAporteSobre().equals("AGUINALDO")){
			return aportesSobreAguinaldo(aportesForm, model);
		}
		else {
			try {
				if(!aportesForm.getMesliquidacion().matches("\\d{2}/(\\d{4})")){
					model.addAttribute("aportesForm", aportesForm);
					model.addAttribute("formError", "El Mes Liquidación no debe estar en blanco y su formato debe ser 'MM/yyyy'");
					model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
					model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
					model.addAttribute("aportesTab", "active");
					return "aportes/aportes-view";
				} else if(!chequeoOkMesLiquidacion(aportesForm.getMesliquidacion())) {
					model.addAttribute("aportesForm", new AportesForm());
					String formError = "El mes Liquidación debe coincidir con el que está en Parámetros!";
					model.addAttribute("formError", formError);
					model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
					model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
					model.addAttribute("aportesTab", "active");
					return "aportes/aportes-view";
				}
				mesliq = aportesForm.getMesliquidacion().substring(3)+aportesForm.getMesliquidacion().substring(0, 2);
				if(calculoAportesService.controlDeEjecucion(mesliq)) {
					res = calculoAportesService.calculoDeAportes(mesliq);
					if(res.equals("success")) {
						gplantaService.actualizarUltimosIngresos();
						model.addAttribute("formSuccess", "El cálculo de aportes ha finalizado exitosamente!");
						model.addAttribute("aportesList", calculoAportesService.obtenerResultadosAportes(mesliq));
						model.addAttribute("haberesReport", true);
						model.addAttribute("resultTab", "active");
					}
				}
				else {
					model.addAttribute("aportesList", calculoAportesService.obtenerResultadosAportes(mesliq));
					model.addAttribute("aportesForm", aportesForm);
					model.addAttribute("haberesReport", true);
					model.addAttribute("resultTab", "active");
				}
				model.addAttribute("help", ayudaService.getByClave("faportes"));
				model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
				model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
				model.addAttribute("procname", "Intermedio");
			}
			catch(Exception e) {
				model.addAttribute("procname", "Intermedio");
				model.addAttribute("formError", e.getMessage());
				model.addAttribute("aportesForm", aportesForm);
				model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
				model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
			}
			
		}
		return "aportes/aportes-view";
	}
	
	private String aportesSobreAguinaldo(final AportesForm aportesForm, Model model) throws Exception {

		String res = "";
		String mesliq = paramService.getMesliquidacion();
		try {
			if(!aportesForm.getDesde().matches("\\d{2}/(\\d{4})") || !aportesForm.getHasta().matches("\\d{2}/(\\d{4})")) {
				model.addAttribute("aportesForm", aportesForm);
				model.addAttribute("formError", "Los meses de comienzo y fin del período no deben estar en blanco y su formato debe ser 'MM/yyyy'");
				model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
				model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
				model.addAttribute("aportesTab", "active");
				return "aportes/aportes-view";				
			}
			String mesdesde = aportesForm.getDesde().substring(3)+aportesForm.getDesde().substring(0, 2);
			String meshasta = aportesForm.getHasta().substring(3)+aportesForm.getHasta().substring(0, 2);
			if(calculoAportesService.controlDeEjecucionAguinaldo(mesliq)) {
				res = calculoAportesService.calculoDeAportesAguinaldo(mesdesde, meshasta);
				if(res.equals("success")) {
					model.addAttribute("formSuccess", "El cálculo de aportes ha finalizado exitosamente!");
					model.addAttribute("aportesList", calculoAportesService.obtenerResultadosAportesAguinaldo(mesliq));
					model.addAttribute("aportesForm", aportesForm);
					model.addAttribute("aguinaldoReport", true);
					model.addAttribute("resultTab", "active");
				}
			}
			else {
				model.addAttribute("aportesList", calculoAportesService.obtenerResultadosAportesAguinaldo(mesliq));
				model.addAttribute("aportesForm", aportesForm);
				model.addAttribute("aguinaldoReport", true);
				model.addAttribute("resultTab", "active");
			}
			model.addAttribute("help", ayudaService.getByClave("faportes"));
			model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
			model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
			model.addAttribute("procname", "Intermedio");
			
		}
		catch(Exception e) {
			model.addAttribute("procname", "Intermedio");
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("aportesForm", aportesForm);
			model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
			model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));			
		}
		return "aportes/aportes-view";
	}
	
	private Boolean chequeoOkMesLiquidacion(String mesliquidacion) {
		if(mesliquidacion.isEmpty()) return false;
		String aniomes = mesliquidacion.substring(3)+mesliquidacion.substring(0, 2);
		String aniomesParam = paramService.getMesliquidacion();
		Integer mi = Integer.valueOf(aniomes);
		Integer mp = Integer.valueOf(aniomesParam);
		if(mi.intValue() == mp.intValue()) {
			return true;
		}
		return false;
	}
	
	@RequestMapping(value= {"/calculoAportes"}, params= {"results"})
	public String getResultadosDelMes(Model model, final AportesForm aportesForm, final BindingResult result) {
		String mesliq = "";
		
		if(result.hasErrors()) {
			model.addAttribute("aportesForm", aportesForm);
			model.addAttribute("aportesTab", "active");
		}
		else {
		try {
			if(aportesForm.getAporteSobre().equals("AGUINALDO")) {
				mesliq = paramService.getMesliquidacion();
				model.addAttribute("help", ayudaService.getByClave("faportes"));
				model.addAttribute("aportesForm", new AportesForm());
				model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
				model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
				model.addAttribute("aportesList", calculoAportesService.obtenerResultadosAportesAguinaldo(mesliq));
				model.addAttribute("aguinaldoReport", true);
				model.addAttribute("resultTab", "active");
				return "aportes/aportes-view";
			}
			if(!aportesForm.getMesliquidacion().matches("\\d{2}/(\\d{4})")){
				model.addAttribute("aportesForm", new AportesForm());
				model.addAttribute("formError", "El Mes Liquidación no debe estar en blanco y su formato debe ser 'MM/yyyy'");
				model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
				model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
				model.addAttribute("aportesTab", "active");
				return "aportes/aportes-view";
			} else if(!chequeoOkMesLiquidacion(aportesForm.getMesliquidacion())) {
				model.addAttribute("aportesForm", new AportesForm());
				String formError = "El mes Liquidación debe coincidir con el que está en Parámetros!";
				model.addAttribute("formError", formError);
				model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
				model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
				model.addAttribute("aportesTab", "active");
				return "aportes/aportes-view";
			}
			mesliq = aportesForm.getMesliquidacion().substring(3)+aportesForm.getMesliquidacion().substring(0, 2);

			model.addAttribute("help", ayudaService.getByClave("faportes"));
			model.addAttribute("aportesForm", new AportesForm());
			model.addAttribute("plantaSMList", calculoAportesService.getAllFuncionariosByUnidad("SM"));
			model.addAttribute("plantaUTFList", calculoAportesService.getAllFuncionariosByUnidad("UTF"));
			model.addAttribute("aportesList", calculoAportesService.obtenerResultadosAportes(mesliq));
			model.addAttribute("haberesReport", true);
			model.addAttribute("resultTab", "active");
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("aportesForm", new AportesForm());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("aportesTab", "active");
		}
		}
		return "aportes/aportes-view";
	}
	
	@GetMapping("/consultaAportes")
	public String getConsultaAportes(ModelMap model) {

		List<String> meses = movimientosRepository.getMesesLiquidacionDesc();
		try {
			model.addAttribute("outputMode", false);
			model.addAttribute("anotherview", false);
			model.addAttribute("capitalesForm", new CapitalesForm());
			model.addAttribute("meseslist", FuncionesUtiles.mesesConForma(meses));
		}
		catch(Exception e) {
			model.addAttribute("outputMode", false);
			model.addAttribute("anotherview", false);
			model.addAttribute("capitalesForm", new CapitalesForm());
			model.addAttribute("meseslist", FuncionesUtiles.mesesConForma(meses));
			model.addAttribute("formError", e.getMessage());
		}

		return "consultas/consulta-aportes-form";
	}

	@RequestMapping(value= {"/resultAportes"}, params= {"getInfo"})
	public String getInformacionAportes(final CapitalesForm capForm, final BindingResult result, ModelMap model) {
		List<String> meses = movimientosRepository.getMesesLiquidacionDesc();

		try {
			String mesliquidacion = capForm.getMesliquidacion().substring(3) + capForm.getMesliquidacion().substring(0, 2);
			List<AportesSummary> lstAportes = calculoAportesService.obtenerResultadosMensualYAguinaldo(mesliquidacion);
			if(lstAportes == null || lstAportes.isEmpty()) {
				model.addAttribute("formError", "No se encontraron aportes para el mes elegido");
			}
			model.addAttribute("meseslist", FuncionesUtiles.mesesConForma(meses));
			model.addAttribute("capitalesForm", capForm);
			model.addAttribute("outputMode", true);
			model.addAttribute("anotherview", false);
			model.addAttribute("aportesList", lstAportes);
			model.addAttribute("paraexcelList", lstAportes);
			
		}
		catch(Exception e) {
			model.addAttribute("outputMode", false);
			model.addAttribute("anotherview", false);
			model.addAttribute("capitalesForm", capForm);
			model.addAttribute("meseslist", FuncionesUtiles.mesesConForma(meses));
			model.addAttribute("formError", e.getMessage());
			
		}
		
		return "consultas/consulta-aportes-form";
	}

	@GetMapping("/pdfreport")
    public void aportesReport(HttpServletResponse response) throws DocumentException, IOException, Exception{
		List<AportesSummary> aportesLst = new ArrayList<AportesSummary>();
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		
		String mesLiquidacion = paramService.getMesliquidacion();
        aportesLst = calculoAportesService.obtenerResultadosAportes(mesLiquidacion);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=aportesreport_" + currDate + ".pdf";
		
		response.setHeader(headerKey, headerValue);
		AportesPdfExporter exporter = new AportesPdfExporter(aportesLst, mesLiquidacion, false);
		exporter.export(response);
     }
	
	@GetMapping("/pdfAguinaldoreport")
    public void aguinaldoReport(HttpServletResponse response) throws DocumentException, IOException, Exception{
		List<AportesSummary> aportesLst = new ArrayList<AportesSummary>();
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		
		String mesLiquidacion = paramService.getMesliquidacion();
        aportesLst = calculoAportesService.obtenerResultadosAportesAguinaldo(mesLiquidacion);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=aguinaldoreport_" + currDate + ".pdf";
		
		response.setHeader(headerKey, headerValue);
		AportesPdfExporter exporter = new AportesPdfExporter(aportesLst, mesLiquidacion, true);
		exporter.export(response);
     }

	@GetMapping("/pdfConsultaReport")
	public void consultaReport(HttpServletResponse response, 
			@RequestParam(value="mesliquidacion", required=true) String mesliquidacion) throws DocumentException, IOException, Exception{
		List<AportesSummary> aportesLst = new ArrayList<AportesSummary>();
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		String mesinicial = mesliquidacion.substring(3) + mesliquidacion.substring(0, 2);
		
        aportesLst = calculoAportesService.obtenerResultadosMensualYAguinaldo(mesinicial);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=consultaAportesreport_" + currDate + ".pdf";
		
		response.setHeader(headerKey, headerValue);
		AportesPdfExporter exporter = new AportesPdfExporter(aportesLst, mesinicial, false);
		exporter.export(response);
	}
	
	@GetMapping("/excelConsulta")
	public void exportConsultaToExcel(HttpServletResponse response,
			@RequestParam(value="mesliquidacion", required=true) String mesliquidacion) throws IOException, Exception{
		List<AportesSummary> aportesLst = new ArrayList<AportesSummary>();
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		String mesinicial = mesliquidacion.substring(3) + mesliquidacion.substring(0, 2);
		
        aportesLst = calculoAportesService.obtenerResultadosMensualYAguinaldo(mesinicial);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=consultaAportes_" + currDate + ".xlsx";
		
		response.setHeader(headerKey, headerValue);
		AportesExcelExporter exporter = new AportesExcelExporter(aportesLst, mesliquidacion);
		
		exporter.export(response);
		
	}
}
