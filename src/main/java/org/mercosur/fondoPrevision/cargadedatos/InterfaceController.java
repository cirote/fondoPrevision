package org.mercosur.fondoPrevision.cargadedatos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InterfaceController {

	@Autowired
	CargaDatosService cargaDatosService;
	
	@GetMapping("/interfForm")
	public String getInterfaceForm(Model model) {
		
		CargaDatos procesos = CargaDatos.SALDOS;
		model.addAttribute("cargadatosEnum", procesos);
		model.addAttribute("interfaceForm", new InterfaceForm());
		model.addAttribute("outputMode", false);
		return "carga-datos/interface-form";
	}
	
	@RequestMapping(value= {"/cargaInformacion"}, params = {"executeProcess"})
	public String ejecutaProceso(final InterfaceForm interfaceForm, final BindingResult results, Model model){
		try {
			CargaDatos proceso = interfaceForm.getProceso();
			switch (proceso){
				case SALDOS:{
					model.addAttribute("lstSaldos", cargaDatosService.cargaSaldosDesde("saldosinterface"));
					break;
				}
				case SUELDOS:{
					model.addAttribute("lstSueldos", cargaDatosService.cargaSueldosDesde("sueldosinterface"));
					break;
				}
				case MOVIMIENTOS:{
					model.addAttribute("lstMovs", cargaDatosService.cargaMovimientos("movimientos"));
					break;
				}
				case PRESTAMOS:{
					model.addAttribute("lstPrst", cargaDatosService.cargaPrestamos("prestamosinterface"));
					break;
				}
				case SALDOSPRESTAMOS:{
					model.addAttribute("lstSAPrst", cargaDatosService.actualizaSaldosPrst());
					break;
				}
				case VINCULOCARGO:{
					model.addAttribute("lstPlanta", cargaDatosService.creaVinculoConCargo());
				}
				default:
			}
			model.addAttribute("outputMode", true);
			return "carga-datos/interface-form";
				
		}
		catch(Exception e) {
			model.addAttribute("outputMode", false);
			model.addAttribute("formError", e.getMessage());
		}
		return "carga-datos/interface-form";
	}
}
