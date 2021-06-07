package org.mercosur.fondoPrevision.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.AportesSummary;
import org.mercosur.fondoPrevision.dto.CapitalesForm;
import org.mercosur.fondoPrevision.dto.CuotasPagas;
import org.mercosur.fondoPrevision.dto.MovimientosForDisplay;
import org.mercosur.fondoPrevision.dto.MovimientosForm;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.User;
import org.mercosur.fondoPrevision.excel.AportesNominaExcelExporter;
import org.mercosur.fondoPrevision.excel.DescCuotasExcelExporter;
import org.mercosur.fondoPrevision.pdfs.AportesNominaPdfExporter;
import org.mercosur.fondoPrevision.pdfs.DescCuotasPdfExporter;
import org.mercosur.fondoPrevision.pdfs.MovimientosPdfExporter;
import org.mercosur.fondoPrevision.repository.AyudaRepository;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.service.MovimientosService;
import org.mercosur.fondoPrevision.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.DocumentException;

@Controller
public class MovimientosController {

	@Autowired
	MovimientosRepository movimientosRepository;
	
	@Autowired
	MovimientosService movimientosService;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Autowired
	AyudaRepository ayudaRepository;
	
	@Autowired
	UserService userService;
	
	@ModelAttribute("plantaList")
	public List<Gplanta> populatePlanta(){
		return (List<Gplanta>) gplantaRepository.findAll();
	}

	public MovimientosController() {
		super();
	}
	
	@GetMapping("/movimientosForm")
	public String getMovimientosForm(ModelMap model) {
		
		String clave = "fmovs";
		try {
			List<String> meses = movimientosRepository.getMesesLiquidacionDesc();
			model.addAttribute("help", ayudaRepository.findByClave(clave));
			model.addAttribute("outputMode", false);
			model.addAttribute("movimientosForm", new MovimientosForm());
			model.addAttribute("meseslist", mesesConForma(meses));
		}
		catch(Exception e) {
			model.addAttribute("help", ayudaRepository.findByClave(clave));
			model.addAttribute("outputMode", false);
			model.addAttribute("movimientosForm", new MovimientosForm());
			model.addAttribute("meseslist", movimientosRepository.getMesesLiquidacionDesc());
			model.addAttribute("formError", e.getMessage());
		}
		return "consultas/movimientos-form";
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
	
	@RequestMapping(value= {"/movimientosFuncionario"}, params= {"getInfo"})
	public String traerInformacion(final MovimientosForm movForm, final BindingResult result, Model model) {
		List<String> meses = movimientosRepository.getMesesLiquidacionDesc();
		try {
			String desde = movForm.getMesdesde().substring(3) + movForm.getMesdesde().substring(0, 2);
			String hasta = movForm.getMeshasta().substring(3) + movForm.getMeshasta().substring(0, 2);
			Gplanta funcionario = gplantaRepository.getOne(movForm.getIdfuncionario());
			movForm.setFuncionario(funcionario);
			List<MovimientosForDisplay> lstMovs = movimientosService.getMovimientosList(movForm.getIdfuncionario(), desde, hasta);
			
			model.addAttribute("movimientosForm", movForm);
			model.addAttribute("movimientosList", lstMovs);
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("outputMode", true);
		}
		catch(Exception e) {
			model.addAttribute("movimientosForm", movForm);
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("formError", e.getMessage());
		}
		
		return "consultas/movimientos-form";
	}

	@GetMapping("/movimientos/cancel")
	public String cancelEstado(ModelMap model) {
		return "redirect:/movimientosForm";
	}
	
	@GetMapping("/movimientosUserForm")
	public String getMovsdelUsuario(ModelMap model) {
		try {
			User user = userService.getLoggedUser();
			Gplanta funcionario = gplantaRepository.getOne(user.getGplanta_id());
			MovimientosForm movForm = new MovimientosForm();
			movForm.setFuncionario(funcionario);
			movForm.setIdfuncionario(funcionario.getIdgplanta());
			List<String> meses = movimientosRepository.getMesesLiquidacionDesc();
			model.addAttribute("outputMode", false);
			model.addAttribute("movimientosForm", movForm);
			model.addAttribute("meseslist", mesesConForma(meses));
		}
		catch(Exception e) {
			List<String> meses = movimientosRepository.getMesesLiquidacionDesc();
			model.addAttribute("outputMode", false);
			model.addAttribute("movimientosForm", new MovimientosForm());
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("formError", e.getMessage());
		}
			
			return "consultas/movimientos-form";
	}
	
	@RequestMapping("/pdfMovimientosreport/{id}")
    public void movimientosReport(HttpServletResponse response, @PathVariable(name="id") Long idfuncionario, 
    		@RequestParam(value="desde", required=true) String mesDesde, 
    		@RequestParam(value="hasta", required=true) String mesHasta, Model model) throws DocumentException, IOException, Exception{
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		String mesinicial = mesDesde.substring(3) + mesDesde.substring(0, 2);
		String mesfinal = mesHasta.substring(3) + mesHasta.substring(0, 2);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=movimientosreport_" + currDate + ".pdf";
		
		try {
			List<MovimientosForDisplay> lstMovs = movimientosService.getMovimientosList(idfuncionario, mesinicial, mesfinal);
			response.setHeader(headerKey, headerValue);
			MovimientosPdfExporter exporter = new MovimientosPdfExporter(lstMovs, mesDesde, mesHasta);
			exporter.export(response);
			
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
     }

	@GetMapping("/descuentoCuotas")
	public String getDescuentoCuotasForm(ModelMap model) {
		
		try {
			List<String> meses = movimientosRepository.getMesesLiquidacionDesc();
			model.addAttribute("outputMode", false);
			model.addAttribute("consultaForm", new CapitalesForm());
			model.addAttribute("meseslist", mesesConForma(meses));
		}
		catch(Exception e) {
			model.addAttribute("outputMode", false);
			model.addAttribute("consultaForm", new CapitalesForm());
			model.addAttribute("meseslist", movimientosRepository.getMesesLiquidacion());
			model.addAttribute("formError", e.getMessage());
		}

		return "consultas/descuentos-form";
	}
	
	@RequestMapping(value= {"/descuentoCuotas"}, params= {"getInfo"})
	public String traerInfoDescuentos(final CapitalesForm infoForm, final BindingResult result, Model model) {
		List<String> meses = movimientosRepository.getMesesLiquidacionDesc();

		
		try {
			String mesliquidacion = infoForm.getMesliquidacion().substring(3) + infoForm.getMesliquidacion().substring(0, 2);
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("consultaForm", infoForm);
			model.addAttribute("outputMode", true);
			model.addAttribute("cuotasListSM", movimientosService.getCuotasPagasList(mesliquidacion, "SM"));
			model.addAttribute("cuotasListUTF", movimientosService.getCuotasPagasList(mesliquidacion, "UTF"));
			
		}
		catch(Exception e) {
			model.addAttribute("outputMode", false);
			model.addAttribute("consultaForm", infoForm);
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("formError", e.getMessage());
			
		}

		return "consultas/descuentos-form";
	}
	
	@RequestMapping("/pdfCuotasPagas")
	public void reportCuotasPagas(HttpServletResponse response, 
			@RequestParam(value="mesliquidacion", required=true) String mesliquidacion, Model model)  
					throws DocumentException, IOException, Exception{
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		String mesinicial = mesliquidacion.substring(3) + mesliquidacion.substring(0, 2);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=descuentosNominaReport_" + currDate + ".pdf";
		
		try {
			List<CuotasPagas> lstSM = movimientosService.getCuotasPagasList(mesinicial, "SM");
			List<CuotasPagas> lstUTF = movimientosService.getCuotasPagasList(mesinicial, "UTF");
			response.setHeader(headerKey, headerValue);
			DescCuotasPdfExporter exporter = new DescCuotasPdfExporter(lstSM, lstUTF, mesliquidacion);
			exporter.export(response);
			
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}

	}
	
	@RequestMapping("excelCuotasPagas")
	public void cuotasPagasToExcel(HttpServletResponse response, 
			@RequestParam(value="mesliquidacion", required=true) String mesliquidacion, Model model) throws IOException, Exception{

		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		String mesinicial = mesliquidacion.substring(3) + mesliquidacion.substring(0, 2);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=descCuotas_" + currDate + ".xlsx";
		
		try {
			List<CuotasPagas> lstSM = movimientosService.getCuotasPagasList(mesinicial, "SM");
			List<CuotasPagas> lstUTF = movimientosService.getCuotasPagasList(mesinicial, "UTF");
			response.setHeader(headerKey, headerValue);
			DescCuotasExcelExporter exporter = new DescCuotasExcelExporter(lstSM, lstUTF, mesliquidacion);
			exporter.export(response);
			
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}		
	}
	
	@GetMapping("/aportesNomina")
	public String getAportesNomina(ModelMap model) {
		
		try {
			List<String> meses = movimientosRepository.getMesesLiquidacionDesc();
			model.addAttribute("outputMode", false);
			model.addAttribute("aportesForm", new CapitalesForm());
			model.addAttribute("meseslist", mesesConForma(meses));
		}
		catch(Exception e) {
			model.addAttribute("outputMode", false);
			model.addAttribute("aportesForm", new CapitalesForm());
			model.addAttribute("meseslist", movimientosRepository.getMesesLiquidacionDesc());
			model.addAttribute("formError", e.getMessage());
		}

		return "consultas/aportes-form";
	}
	
	@RequestMapping(value= {"/aportesNomina"}, params= {"getInfo"})
	public String traerInfoAportes(final CapitalesForm infoForm, final BindingResult result, Model model) {
		List<String> meses = movimientosRepository.getMesesLiquidacionDesc();

		
		try {
			String mesliquidacion = infoForm.getMesliquidacion().substring(3) + infoForm.getMesliquidacion().substring(0, 2);
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("aportesForm", infoForm);
			model.addAttribute("outputMode", true);
			model.addAttribute("aportesListSM", movimientosService.getAportesNomina("1, 2, 9", mesliquidacion, "SM"));
			model.addAttribute("aportesListUTF", movimientosService.getAportesNomina("1, 2, 9", mesliquidacion, "UTF"));
			
		}
		catch(Exception e) {
			model.addAttribute("outputMode", false);
			model.addAttribute("aportesForm", infoForm);
			model.addAttribute("meseslist", mesesConForma(meses));
			model.addAttribute("formError", e.getMessage());		
		}

		return "consultas/aportes-form";
	}

	@RequestMapping("/pdfAportesNomina")
	public void reportAportesNomina(HttpServletResponse response, 
			@RequestParam(value="mesliquidacion", required=true) String mesliquidacion, Model model)  
					throws DocumentException, IOException, Exception{
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		String mesinicial = mesliquidacion.substring(3) + mesliquidacion.substring(0, 2);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=aportesNominaReport_" + currDate + ".pdf";
		
		try {
			List<AportesSummary> lstSM = movimientosService.getAportesNomina("1, 2, 9", mesinicial, "SM");
			List<AportesSummary> lstUTF = movimientosService.getAportesNomina("1, 2, 9", mesinicial, "UTF");
			response.setHeader(headerKey, headerValue);
			AportesNominaPdfExporter exporter = new AportesNominaPdfExporter(lstSM, lstUTF, mesliquidacion);
			exporter.export(response);
			
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	@RequestMapping("/excelAportesNomina")
	public void aportesNominaToExcel(HttpServletResponse response, 
			@RequestParam(value="mesliquidacion", required=true) String mesliquidacion, Model model) throws IOException, Exception{

		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currDate = sdf.format(new Date());
		String mesinicial = mesliquidacion.substring(3) + mesliquidacion.substring(0, 2);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=aportesNomina_" + currDate + ".xlsx";
		
		try {
			List<AportesSummary> lstSM = movimientosService.getAportesNomina("1, 2, 9", mesinicial, "SM");
			List<AportesSummary> lstUTF = movimientosService.getAportesNomina("1, 2, 9", mesinicial, "UTF");
			response.setHeader(headerKey, headerValue);
			AportesNominaExcelExporter exporter = new AportesNominaExcelExporter(lstSM, lstUTF, mesliquidacion);
			exporter.export(response);
			
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}		
	}

}
