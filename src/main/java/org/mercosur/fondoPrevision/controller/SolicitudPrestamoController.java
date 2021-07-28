package org.mercosur.fondoPrevision.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.mercosur.fondoPrevision.dto.EstadoDeCta;
import org.mercosur.fondoPrevision.dto.SolicitudPrstForm;
import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.ParamPrestamo;
import org.mercosur.fondoPrevision.entities.Parametro;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.Saldos;
import org.mercosur.fondoPrevision.entities.SolicitudPrestamo;
import org.mercosur.fondoPrevision.entities.TipoPrestamo;
import org.mercosur.fondoPrevision.entities.User;
import org.mercosur.fondoPrevision.exceptions.ParamNotFoundException;
import org.mercosur.fondoPrevision.pdfs.SolicitudFormPdfExporter;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.ParamPrestamoRepository;
import org.mercosur.fondoPrevision.repository.SaldosRepository;
import org.mercosur.fondoPrevision.repository.SolicitudPrestamoRepository;
import org.mercosur.fondoPrevision.repository.TipoPrestamoRepository;
import org.mercosur.fondoPrevision.service.AyudaService;
import org.mercosur.fondoPrevision.service.EstadoDeCtaService;
import org.mercosur.fondoPrevision.service.GplantaService;
import org.mercosur.fondoPrevision.service.ParamService;
import org.mercosur.fondoPrevision.service.PrestamoService;
import org.mercosur.fondoPrevision.service.SolicitudPrestamoService;
import org.mercosur.fondoPrevision.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;

@Controller
public class SolicitudPrestamoController {

	@Autowired
	UserService userService;
	
	@Autowired
	EstadoDeCtaService estadoDeCtaService;
	
	@Autowired
	GplantaService gplantaService;
	
	@Autowired
	GplantaRepository gplantaRepository;
		
	@Autowired
	SolicitudPrestamoService solicitudPrestamoService;
	
	@Autowired
	SolicitudPrestamoRepository solicitudPrestamoRepository;
	
	@Autowired
	ParamService paramService;
	
	@Autowired
	ParamPrestamoRepository paramPrestamoRepository;
	
	@Autowired
	TipoPrestamoRepository tipoPrestamoRepository;
	
	@Autowired
	PrestamoService prestamoService;
	
	@Autowired
	AyudaService ayudaService;
	
	@Autowired
	SaldosRepository saldosRepository;
	
	@GetMapping("/newSolFormByAdmin")
	public String getSolicitudByAdmin(ModelMap model) {
		User user;
		try {
			user = userService.getLoggedUser();
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			String clave = "fsolPrstFondo";
			Ayuda help = ayudaService.getByClave(clave);
			SolicitudPrstForm solicitudPrstForm = new SolicitudPrstForm();
			solicitudPrstForm.setPrestamo(new Prestamo());
			solicitudPrstForm.getPrestamo().setInteresPrestamo(BigDecimal.ZERO);
			solicitudPrstForm.setUser(user);
			
			model.addAttribute("sinPrst", true);
			model.addAttribute("editMode", false);
			model.addAttribute("pprstList", paramPrestamoRepository.findAllOrderBymeses());
			model.addAttribute("listPendientes", new ArrayList<SolicitudPrestamo>());
			model.addAttribute("help", help);
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			model.addAttribute("solicitudPrstForm", solicitudPrstForm);
			model.addAttribute("solNewTab", "active");
			
		}
		catch(Exception e){
			model.addAttribute("formError", e.getMessage());
		}
		return "/solicitudesPrst/solicitud-view";
	}

	@GetMapping("/completeInfo/{id}")
	public String getFuncionarioInformation(@PathVariable(name="id") Long idfunc, ModelMap model) {
		User user;
		try {
			user = userService.getLoggedUser();
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("pprstList", paramPrestamoRepository.findAllOrderBymeses());
			String clave = "fsolPrstFondo";
			Ayuda help = ayudaService.getByClave(clave);
			SolicitudPrstForm solicitudPrstForm = new SolicitudPrstForm();
			solicitudPrstForm.setPrestamo(new Prestamo());
			solicitudPrstForm.getPrestamo().setInteresPrestamo(BigDecimal.ZERO);
			solicitudPrstForm.setUser(user);

			List<ParamPrestamo> lst = new ArrayList<ParamPrestamo>();
			lst = paramPrestamoRepository.findAllOrderBymeses();
			solicitudPrstForm.setLstpprst(lst);
			
			Optional<Gplanta> func = gplantaRepository.findById(idfunc); 
			if(func.isPresent()) {
				try {
					model.addAttribute("listPendientes", solicitudPrestamoService.getByTarjetaSinEnviar(func.get().getTarjeta()));
				}
				catch(Exception e) {
					model.addAttribute("listPendientes", new ArrayList<SolicitudPrestamo>());
				}
				model.addAttribute("nombre", func.get().getNombre());
				model.addAttribute("tarjeta", func.get().getTarjeta());
				solicitudPrstForm.setIdfuncionario(func.get().getIdgplanta());
				model.addAttribute("funcionario", func.get());
				EstadoDeCta estadodeCta = estadoDeCtaService.getEstadoDeCtabyFuncionario(idfunc);
				if(estadodeCta.getLstPrst() == null) {
					model.addAttribute("sinPrst", true);
				}
				else {
					model.addAttribute("sinPrst", false);
				}

				model.addAttribute("help", help);
				model.addAttribute("estadoDeCta", estadodeCta);
				model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
				model.addAttribute("solicitudPrstForm", solicitudPrstForm);
				model.addAttribute("solNewTab", "active");
				
			}
		}
		catch(Exception e) {
			model.addAttribute("solicitudPrstForm", new SolicitudPrstForm());
			model.addAttribute("formError", e.getMessage());			
			model.addAttribute("solNewTab", "active");
		}
		return "/solicitudesPrst/solicitud-view";
		
	}
	
	@GetMapping("/solicitudesUserForm")
	public String getSolicitudesdelUsuario(ModelMap model) {
		try {
			User user = userService.getLoggedUser();
			
			Gplanta funcionario = gplantaRepository.findByTarjeta(user.getTarjeta());
			List<SolicitudPrestamo> solicitudes = solicitudPrestamoRepository.getAllByGplantaId(user.getGplanta_id());
			model.addAttribute("outputMode", true);
			model.addAttribute("funcionario", funcionario);
			model.addAttribute("solicitudesList", solicitudes);
		}
		catch(Exception e) {
			model.addAttribute("outputMode", false);
			model.addAttribute("formError", e.getMessage());
		}
			
			return "consultas/solicitudes-form";
	}

	@GetMapping("/solicitudForm")
	public String getSolicitudForm(ModelMap model) {
		User user;
		try {
			user =  userService.getLoggedUser();
			Optional<Gplanta> func = gplantaRepository.findById(user.getGplanta_id()); 
			String clave = "fsolPrstFondo";
			Ayuda help = ayudaService.getByClave(clave);
			SolicitudPrstForm solicitudPrstForm = new SolicitudPrstForm();
			solicitudPrstForm.setPrestamo(new Prestamo());
			solicitudPrstForm.getPrestamo().setInteresPrestamo(BigDecimal.ZERO);
			solicitudPrstForm.setUser(user);
			if(func.isPresent()) {
				try {
					model.addAttribute("listPendientes", solicitudPrestamoService.getByTarjetaSinEnviar(func.get().getTarjeta()));
				}
				catch(Exception e) {
					model.addAttribute("listPendientes", new ArrayList<SolicitudPrestamo>());
				}
				model.addAttribute("nombre", func.get().getNombre());
				model.addAttribute("tarjeta", func.get().getTarjeta());
				solicitudPrstForm.setIdfuncionario(func.get().getIdgplanta());
				model.addAttribute("funcionario", func.get());
			}
			EstadoDeCta estadodeCta = estadoDeCtaService.getEstadoDeCtabyFuncionario(user.getGplanta_id());
			if(estadodeCta.getLstPrst() == null) {
				model.addAttribute("sinPrst", true);
			}
			else {
				model.addAttribute("sinPrst", false);
			}
			model.addAttribute("pprstList", paramPrestamoRepository.findAllOrderBymeses());
			model.addAttribute("help", help);
			model.addAttribute("estadoDeCta", estadodeCta);
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			model.addAttribute("solicitudPrstForm", solicitudPrstForm);
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("solNewTab", "active");
			
		}
		catch(Exception e) {
			model.addAttribute("solicitudPrstForm", new SolicitudPrstForm());
			model.addAttribute("formError", e.getMessage());
		}
		return "/solicitudesPrst/solicitud-view";
	}
	
	
	@GetMapping("/editSolicitud/{id}")
	public String editarSolicitud(@PathVariable(name="id")Long idsolicitud, ModelMap model) {
	
		Optional<SolicitudPrestamo> solicitud = solicitudPrestamoRepository.findById(idsolicitud);
		if(solicitud.isPresent()) {
			SolicitudPrstForm solicitudPrstForm = new SolicitudPrstForm();
			solicitudPrstForm.setCancelPrst(solicitud.get().getCancelPrstNros());
			solicitudPrstForm.setCapitalNeto(solicitud.get().getImporteNeto());
			solicitudPrstForm.setIdfuncionario(solicitud.get().getFuncionario().getIdgplanta());
			solicitudPrstForm.setIdfsolicitud(solicitud.get().getIdfsolicitud());
			solicitudPrstForm.setIdTipoPrestamo(solicitud.get().getTipoPrestamo().getIdftipoprestamo());
			solicitudPrstForm.setIdparamPrst(solicitud.get().getParamPrestamo().getIdfparamprst());
			solicitudPrstForm.setObsComision(solicitud.get().getMotivo());
			
			Prestamo prst = new Prestamo();
			prst.setCantCuotas(solicitud.get().getCantCuotas());
			prst.setCapitalPrestamo(solicitud.get().getCapitalPrestamo());
			prst.setCuota(solicitud.get().getCuota());
			prst.setInteresPrestamo(solicitud.get().getInteresPrestamo());
			solicitudPrstForm.setPrestamo(prst);
			EstadoDeCta estadodeCta;
			try {
				String clave = "fedSolPrst";
				Ayuda help = ayudaService.getByClave(clave);
				model.addAttribute("help", help);
				model.addAttribute("plantaList", gplantaService.getAllPlanta());
				model.addAttribute("parametro", paramPrestamoRepository.getOne(solicitudPrstForm.getIdparamPrst()));
				estadodeCta = estadoDeCtaService.getEstadoDeCtabyFuncionario(solicitud.get().getFuncionario().getIdgplanta());
				if(estadodeCta.getLstPrst() == null) {
					model.addAttribute("sinPrst", true);
				}
				else {
					model.addAttribute("sinPrst", false);
				}
			}
			catch(Exception e) {
				estadodeCta = new EstadoDeCta();
			}
			model.addAttribute("estadoDeCta", estadodeCta);
			model.addAttribute("solicitudPrstForm", solicitudPrstForm);
			model.addAttribute("funcionario", solicitud.get().getFuncionario());
			model.addAttribute("parametro", solicitud.get().getParamPrestamo());
			model.addAttribute("nombre", solicitud.get().getFuncionario().getNombre());
			model.addAttribute("tarjeta", solicitud.get().getFuncionario().getTarjeta());
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("pprstList", paramPrestamoRepository.findAllOrderBymeses());
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			model.addAttribute("editMode", true);
			model.addAttribute("solNewTab", "active");
			
		}
		return "/solicitudesPrst/solicitud-view";
	}
	
	@GetMapping("/deleteSolicitud/{id}")
	public String deleteSolicitud(ModelMap model, @PathVariable(name="id") Long id) {
		try {
			solicitudPrestamoService.deleteSolicitud(id);
			if(userService.isLoggedUserADMIN()) {
				return "redirect:/home";
			}
		}
		catch(Exception uoie) {
			model.addAttribute("formError", uoie.getMessage());
		}

		return "redirect:/solicitudForm";
	}

	@RequestMapping(value= {"/solicitudNuevaForm"}, params= {"calculoCuota"})
	public String calculoCuotaEnSolicitNew(@Valid @ModelAttribute("solicitudPrstForm") SolicitudPrstForm form, BindingResult result, 
								HttpServletRequest request, ModelMap model){

		if(result.hasErrors()) {
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("solNewTab", "active");
			model.addAttribute("solicitudPrstForm", form);
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			return "/solicitudesPrst/solicitud-view"; 
		}
		Long idsol = form.getIdfsolicitud();
		model.remove("estadodeCta");
		EstadoDeCta estadoCta = new EstadoDeCta();
		Long idfunc = form.getIdfuncionario();
		BigDecimal sumaSaldos = sumaSaldosACancelar(form.getCancelPrst());
		BigDecimal sumaCuotas = BigDecimal.ZERO;
		BigDecimal cuarentaxCiento = BigDecimal.ZERO;
		List<ParamPrestamo> lstpprst = paramPrestamoRepository.findAllOrderBymeses();		
		
		Gplanta fun = gplantaRepository.getOne(idfunc);

		model.addAttribute("nombre", fun.getNombre());
		model.addAttribute("tarjeta", fun.getTarjeta());
		model.addAttribute("idfuncionario", fun.getIdgplanta());
		model.addAttribute("funcionario", fun);
		model.addAttribute("plantaList", gplantaService.getAllPlanta());
		model.addAttribute("solicitpersonal", true);
		model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
		model.addAttribute("pprstList", lstpprst);
		
		try {
			estadoCta = estadoDeCtaService.getEstadoDeCtabyFuncionario(idfunc);
			sumaCuotas = sumaCuotasACancelar(estadoCta, form.getCancelPrst());
			cuarentaxCiento = estadoCta.getCuarentaPorCiento();			
			if(form.getPrestamo().getCantCuotas() == null | form.getPrestamo().getCapitalPrestamo()== null | 
					form.getPrestamo().getInteresPrestamo() == null) {
				model.addAttribute("estadoDeCta", estadoCta);
				if(estadoCta.getLstPrst() == null) {
					model.addAttribute("sinPrst", true);
				}
				else {
					model.addAttribute("sinPrst", false);
				}
				
				model.addAttribute("solNewTab", "active");
				model.addAttribute("solicitudPrstForm", form);
				model.addAttribute("formError", "El capital, la cantidad de cuotas y la tasa de interés son datos requeridos!");		
				return("/solicitudesPrst/solicitud-view");
			}
			if(!chequeoDisponible(fun, form.getPrestamo().getCapitalPrestamo(), sumaSaldos)) {
				model.addAttribute("estadoDeCta", estadoCta);
				if(estadoCta.getLstPrst() == null) {
					model.addAttribute("sinPrst", true);
				}
				else {
					model.addAttribute("sinPrst", false);
				}
				model.addAttribute("solicitudPrstForm", form);
				model.addAttribute("solNewTab", "active");
				model.addAttribute("formError", "No es posible registrar la solicitud porque No posee capital suficiente!");
				return("/solicitudesPrst/solicitud-view");
			}
			else if(!puedeOperar(fun)) {
				model.addAttribute("estadoDeCta", estadoCta);
				if(estadoCta.getLstPrst() == null) {
					model.addAttribute("sinPrst", true);
				}
				else {
					model.addAttribute("sinPrst", false);
				}
				model.addAttribute("solicitudPrstForm", form);
				model.addAttribute("solNewTab", "active");
				model.addAttribute("formError", "No es posible registrar la solicitud dado que posee un prestamo reciente!");
				return("/solicitudesPrst/solicitud-view");
			}
			BigDecimal interes = form.getPrestamo().getInteresPrestamo().divide(new BigDecimal("1200"), MathContext.DECIMAL32);
			BigDecimal factor = BigDecimal.ONE.add(interes).pow(Integer.valueOf(form.getPrestamo().getCantCuotas().toString())).setScale(9,  BigDecimal.ROUND_HALF_UP);
			BigDecimal numerador = interes.multiply(factor);
			BigDecimal denominador = factor.subtract(BigDecimal.ONE);
			BigDecimal coeficiente = numerador.divide(denominador, MathContext.DECIMAL32);
			BigDecimal capxcoef = coeficiente.multiply(form.getPrestamo().getCapitalPrestamo());
			BigDecimal valorCuota = capxcoef.multiply(new BigDecimal("100")).divide(new BigDecimal("100"), RoundingMode.HALF_UP)
									.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal capitalNeto = form.getPrestamo().getCapitalPrestamo().subtract(sumaSaldos);
			BigDecimal cuotascomprometidas = valorCuota;
			if(estadoCta.getSumaDeCuotas() != null) {
				cuotascomprometidas = estadoCta.getSumaDeCuotas().subtract(sumaCuotas).add(valorCuota);				
			}
			form.getPrestamo().setCuota(valorCuota);
			form.setCuarentaPorCiento(cuarentaxCiento);
			form.setCapitalNeto(capitalNeto);
			form.setTotalCuotas(cuotascomprometidas);
			if(superaCuarentaPorCiento(valorCuota, estadoCta, sumaCuotas)) {
				model.addAttribute("formError", "Con la cuota actual supera el 40%!");
			}
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
		}
				
		String uri = request.getRequestURI();
		if(uri.equals("/editSolicitud")) {
			try {
				form.setIdfsolicitud(idsol);
				TipoPrestamo tipo = tipoPrestamoRepository.getOne(form.getIdTipoPrestamo());
				model.addAttribute("editMode", true);
				model.addAttribute("tipoPrst", tipo);	
			}
			catch(Exception e) {
				model.addAttribute("formError", e.getMessage());
			}
		}
		model.addAttribute("estadoDeCta", estadoCta);
		if(estadoCta.getLstPrst() == null) {
			model.addAttribute("sinPrst", true);
		}
		else {
			model.addAttribute("sinPrst", false);
		}
		model.addAttribute("solicitudPrstForm", form);
		model.addAttribute("solNewTab", "active");
		return "/solicitudesPrst/solicitud-view";
	}

	@RequestMapping(value= {"/editSolicitud"}, params= {"calculoCuota"})
	public String calculoCuota(@Valid @ModelAttribute("solicitudPrstForm") SolicitudPrstForm form, BindingResult result, 
								HttpServletRequest request, ModelMap model){

		if(result.hasErrors()) {
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("solNewTab", "active");
			model.addAttribute("solicitudPrstForm", form);
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			return "/solicitudesPrst/solicitud-view"; 
		}
				
		Long idsol = form.getIdfsolicitud();
		Optional<SolicitudPrestamo> solicitud = solicitudPrestamoRepository.findById(idsol);
		if(solicitud.isPresent()) {
			EstadoDeCta estadoCta;
			try {
				estadoCta = estadoDeCtaService.getEstadoDeCtabyFuncionario(solicitud.get().getFuncionario().getIdgplanta());
				BigDecimal sumaSaldos = sumaSaldosACancelar(form.getCancelPrst());
				BigDecimal sumaCuotas = BigDecimal.ZERO;
				BigDecimal cuarentaxCiento = BigDecimal.ZERO;

				sumaCuotas = sumaCuotasACancelar(estadoCta, form.getCancelPrst());
				cuarentaxCiento = estadoCta.getCuarentaPorCiento();			
				if(estadoCta.getLstPrst() == null) {
					model.addAttribute("sinPrst", true);
				}
				else {
					model.addAttribute("sinPrst", false);
				}
				model.addAttribute("estadoDeCta", estadoCta);

				if(form.getPrestamo().getCantCuotas() == null | form.getPrestamo().getCapitalPrestamo()== null | 
						form.getPrestamo().getInteresPrestamo() == null) {				
					model.addAttribute("solNewTab", "active");
					model.addAttribute("solicitudPrstForm", form);
					model.addAttribute("formError", "El capital, la cantidad de cuotas y la tasa de interés son datos requeridos!");		
					return("/solicitudesPrst/solicitud-view");
				}
				if(!chequeoDisponible(solicitud.get().getFuncionario(), form.getPrestamo().getCapitalPrestamo(), sumaSaldos)) {
					model.addAttribute("solicitudPrstForm", form);
					model.addAttribute("solNewTab", "active");
					model.addAttribute("formError", "No es posible registrar la solicitud porque No posee capital suficiente!");
					return("/solicitudesPrst/solicitud-view");
				}
				else if(!puedeOperar(solicitud.get().getFuncionario())) {
					model.addAttribute("solicitudPrstForm", form);
					model.addAttribute("solNewTab", "active");
					model.addAttribute("formError", "No es posible registrar la solicitud dado que posee un prestamo reciente!");
					return("/solicitudesPrst/solicitud-view");
				}
				BigDecimal interes = form.getPrestamo().getInteresPrestamo().divide(new BigDecimal("1200"), MathContext.DECIMAL32);
				BigDecimal factor = BigDecimal.ONE.add(interes).pow(Integer.valueOf(form.getPrestamo().getCantCuotas().toString())).setScale(9,  BigDecimal.ROUND_HALF_UP);
				BigDecimal numerador = interes.multiply(factor);
				BigDecimal denominador = factor.subtract(BigDecimal.ONE);
				BigDecimal coeficiente = numerador.divide(denominador, MathContext.DECIMAL32);
				BigDecimal capxcoef = coeficiente.multiply(form.getPrestamo().getCapitalPrestamo());
				BigDecimal valorCuota = capxcoef.multiply(new BigDecimal("100")).divide(new BigDecimal("100"), RoundingMode.HALF_UP)
										.setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal capitalNeto = form.getPrestamo().getCapitalPrestamo().subtract(sumaSaldos);
				BigDecimal cuotascomprometidas = valorCuota;
				if(estadoCta.getSumaDeCuotas() != null) {
					cuotascomprometidas = estadoCta.getSumaDeCuotas().subtract(sumaCuotas).add(valorCuota);				
				}
				form.getPrestamo().setCuota(valorCuota);
				form.setCuarentaPorCiento(cuarentaxCiento);
				form.setCapitalNeto(capitalNeto);
				form.setTotalCuotas(cuotascomprometidas);
				if(superaCuarentaPorCiento(valorCuota, estadoCta, sumaCuotas)) {
					model.addAttribute("formError", "Con la cuota actual supera el 40%!");
				}
			}
			catch(Exception e) {
				model.addAttribute("formError", e.getMessage());
			}
			model.addAttribute("nombre", solicitud.get().getFuncionario().getNombre());
			model.addAttribute("tarjeta", solicitud.get().getFuncionario().getTarjeta());
			model.addAttribute("funcionario", solicitud.get().getFuncionario());
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			model.addAttribute("parametro", solicitud.get().getParamPrestamo());
			model.addAttribute("editMode", true);
			model.addAttribute("pprstList", paramPrestamoRepository.findAllOrderBymeses());
			model.addAttribute("solicitudPrstForm", form);
			model.addAttribute("solNewTab", "active");
			
		}
		return "/solicitudesPrst/solicitud-view";
	}

	@RequestMapping(value= {"/solicitudNuevaForm", "/editSolicitud"}, params= {"saveSol"})
	public String saveSolicitud(@Valid @ModelAttribute("solicitudPrstForm") SolicitudPrstForm form, BindingResult result, 
			HttpServletRequest request, ModelMap model){
		if(result.hasErrors()) {
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("solNewTab", "active");
			model.addAttribute("solicitudPrstForm", form);
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			return "/solicitudesPrst/solicitud-view"; 
		}
		Long idfunc = form.getIdfuncionario();
		SolicitudPrestamo solicitud = new SolicitudPrestamo();
		if(form.getIdfsolicitud() != null) {
			solicitud = solicitudPrestamoRepository.getOne(form.getIdfsolicitud());
		}
		Optional<Gplanta> fun = gplantaRepository.findById(idfunc);
		if(fun.isPresent()) {
			model.addAttribute("nombre", fun.get().getNombre());
			model.addAttribute("tarjeta", fun.get().getTarjeta());
			if(form.getPrestamo().getCuota() == null || form.getPrestamo().getCantCuotas() == null ||
					form.getPrestamo().getCapitalPrestamo() == null) {
				model.addAttribute("formError", "La cuota no puede ser nula. Por favor calcule la cuota antes de guardar el borrador.");
				model.addAttribute("solicitudPrstForm", form);
				return getSolicitudForm(model);
			}
			LocalDate fecha = LocalDate.now();
			solicitud.setCancelPrstNros(form.getCancelPrst());
			solicitud.setCantCuotas(form.getPrestamo().getCantCuotas());
			solicitud.setCapitalPrestamo(form.getPrestamo().getCapitalPrestamo());
			solicitud.setCuota(form.getPrestamo().getCuota());
			solicitud.setEnviadaAfondo(true);
			solicitud.setProcesada(false);
			solicitud.setAprobada(false);
			solicitud.setAprobada2(false);
			solicitud.setRechazada(false);
			solicitud.setRechazada2(false);
			solicitud.setEnviadaAComision(false);
			solicitud.setFechaSolicitud(fecha);
			solicitud.setFuncionario(fun.get());
			solicitud.setImporteNeto(form.getCapitalNeto());
			solicitud.setInteresPrestamo(form.getPrestamo().getInteresPrestamo());
			solicitud.setTarjeta(fun.get().getTarjeta());
			TipoPrestamo tp = tipoPrestamoRepository.getOne(form.getIdTipoPrestamo());
			solicitud.setTipoPrestamo(tp);
			solicitud.setCodigoPrestamo(tp.getCodigoPrestamo());
			ParamPrestamo pp = paramPrestamoRepository.getOne(form.getIdparamPrst());
			solicitud.setParamPrestamo(pp);
			
		}
		try {
			if(form.getIdfsolicitud() == null) {
				solicitud = solicitudPrestamoService.ingresarSolicitud(solicitud);				
			}
			else {
				solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
			}
			model.remove("pprstList");
			model.addAttribute("listPendientes", solicitudPrestamoService.getByTarjetaSinEnviar(solicitud.getTarjeta()));
			model.addAttribute("formSuccess", "La solicitud ha quedado ha disposición de la Administración del fondo");
			model.addAttribute("sinPrst", true);
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("solicitudPrstForm", new SolicitudPrstForm());
			model.addAttribute("solNewTab", "active");
		} catch (Exception e) {
			model.addAttribute("formError", e.getMessage());
			e.printStackTrace();
		}
		return "/solicitudesPrst/solicitud-view";
	}

	@RequestMapping(value= {"/solicitudNuevaForm"}, params= {"enviarAComision"})
	public String saveAndSendNewSolicitud(@Valid @ModelAttribute("solicitudPrstForm") SolicitudPrstForm form, BindingResult result, 
			HttpServletRequest request, ModelMap model){
		if(result.hasErrors()) {
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("solNewTab", "active");
			model.addAttribute("solicitudPrstForm", form);
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			return "/solicitudesPrst/solicitud-view"; 
		}

		Long idfunc = form.getIdfuncionario();		
		SolicitudPrestamo solicitud = new SolicitudPrestamo();
		Gplanta fun = new Gplanta(); 
		Optional<Gplanta> f =	gplantaRepository.findById(idfunc);
		if(f.isPresent()) {
			fun = f.get();
		}
		else {
			fun.setIdgplanta(idfunc);
		}
		model.addAttribute("nombre", fun.getNombre());
		model.addAttribute("tarjeta", fun.getTarjeta());
		
		if(form.getPrestamo().getCuota() == null || form.getPrestamo().getCantCuotas() == null ||
				form.getPrestamo().getCapitalPrestamo() == null) {
			model.addAttribute("formError", "La cuota no puede ser nula. Por favor calcule la cuota antes de guardar el borrador.");
			model.addAttribute("solicitudPrstForm", form);
			model.addAttribute("solNewTab", "active");
			return "/solicitudesPrst/solicitud-view";
		}
		
		LocalDate fecha = LocalDate.now();
		solicitud.setCancelPrstNros(form.getCancelPrst());
		solicitud.setCantCuotas(form.getPrestamo().getCantCuotas());
		solicitud.setCapitalPrestamo(form.getPrestamo().getCapitalPrestamo());
		solicitud.setCuota(form.getPrestamo().getCuota());
		solicitud.setEnviadaAfondo(true);
		solicitud.setProcesada(false);
		solicitud.setEnviadaAComision(true);
		solicitud.setAprobada(false);
		solicitud.setAprobada2(false);
		solicitud.setRechazada(false);
		solicitud.setRechazada2(false);
		solicitud.setFechaSolicitud(fecha);
		solicitud.setFuncionario(fun);
		solicitud.setImporteNeto(form.getCapitalNeto());
		solicitud.setInteresPrestamo(form.getPrestamo().getInteresPrestamo());
		solicitud.setTarjeta(fun.getTarjeta());
		TipoPrestamo tp = tipoPrestamoRepository.getOne(form.getIdTipoPrestamo());
		solicitud.setTipoPrestamo(tp);
		solicitud.setCodigoPrestamo(tp.getCodigoPrestamo());
		solicitud.setMotivo(form.getObsComision());
		solicitud.setParamPrestamo(paramPrestamoRepository.getOne(form.getIdparamPrst()));

		try {
			if(form.getIdfsolicitud() == null) {
				solicitud = solicitudPrestamoRepository.save(solicitud);				
			}
			else {
				solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
			}
			model.addAttribute("sinPrst", true);
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("tipoPrstList", tipoPrestamoRepository.findAll());
			model.addAttribute("solicitudPrstForm", new SolicitudPrstForm());
			model.addAttribute("formSuccess", "La solicitud ha quedado ha disposición de la Comisión Administradora del fondo");
			model.addAttribute("solNewTab", "active");
		} catch (Exception e) {
			model.addAttribute("formError", e.getMessage());
			e.printStackTrace();
		}
		
		return "/solicitudesPrst/solicitud-view";
	}

	@RequestMapping(value= {"/editSolicitud"}, params= {"enviarAComision"})
	public String saveAndSendSolicitud(@Valid @ModelAttribute("solicitudPrstForm") SolicitudPrstForm form, BindingResult result, 
			HttpServletRequest request, ModelMap model){
		if(result.hasErrors()) {
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("solNewTab", "active");
			model.addAttribute("solicitudPrstForm", form);
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			return "/solicitudesPrst/solicitud-view"; 
		}

		if(form.getIdfsolicitud() == null) {
			model.addAttribute("formError", "Se perdió la identificacion de la solicitud");
			model.addAttribute("solicitudPrstForm", form);
			model.addAttribute("solNewTab", "active");
			return "/solicitudesPrst/solicitud-view";
		}
		SolicitudPrestamo solicitud = solicitudPrestamoRepository.getOne(form.getIdfsolicitud());
		Long idfunc = form.getIdfuncionario();
		Gplanta fun = new Gplanta(); 
		Optional<Gplanta> f =	gplantaRepository.findById(idfunc);
		if(f.isPresent()) {
			fun = f.get();
		}
		else {
			fun.setIdgplanta(idfunc);
		}
		
		model.addAttribute("nombre", fun.getNombre());
		model.addAttribute("tarjeta", fun.getTarjeta());

		if(form.getPrestamo().getCuota() == null || form.getPrestamo().getCantCuotas() == null ||
				form.getPrestamo().getCapitalPrestamo() == null) {
			model.addAttribute("formError", "La cuota no puede ser nula. Por favor calcule la cuota antes de guardar el borrador.");
			model.addAttribute("solicitudPrstForm", form);
			model.addAttribute("solNewTab", "active");
			return "/solicitudesPrst/solicitud-view";
		}
		LocalDate fecha = LocalDate.now();
		solicitud.setCancelPrstNros(form.getCancelPrst());
		solicitud.setCantCuotas(form.getPrestamo().getCantCuotas());
		solicitud.setCapitalPrestamo(form.getPrestamo().getCapitalPrestamo());
		solicitud.setCuota(form.getPrestamo().getCuota());
		solicitud.setEnviadaAfondo(true);
		solicitud.setProcesada(false);
		solicitud.setEnviadaAComision(true);
		solicitud.setAprobada(false);
		solicitud.setAprobada2(false);
		solicitud.setRechazada(false);
		solicitud.setRechazada2(false);
		solicitud.setFechaSolicitud(fecha);
		solicitud.setFuncionario(fun);
		solicitud.setImporteNeto(form.getCapitalNeto());
		solicitud.setInteresPrestamo(form.getPrestamo().getInteresPrestamo());
		solicitud.setTarjeta(fun.getTarjeta());
		TipoPrestamo tp = tipoPrestamoRepository.getOne(form.getIdTipoPrestamo());
		solicitud.setTipoPrestamo(tp);
		solicitud.setCodigoPrestamo(tp.getCodigoPrestamo());
		solicitud.setMotivo(form.getObsComision());
		solicitud.setParamPrestamo(paramPrestamoRepository.getOne(form.getIdparamPrst()));

		try {
			if(form.getIdfsolicitud() == null) {
				solicitud = solicitudPrestamoRepository.save(solicitud);				
			}
			else {
				solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
			}
			model.addAttribute("sinPrst", true);
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("tipoPrstList", tipoPrestamoRepository.findAll());
			model.addAttribute("solicitudPrstForm", new SolicitudPrstForm());
			model.addAttribute("formSuccess", "La solicitud ha quedado ha disposición de la Comisión Administradora del fondo");
			model.addAttribute("solNewTab", "active");
		} catch (Exception e) {
			model.addAttribute("formError", e.getMessage());
			e.printStackTrace();
		}

		return "/solicitudesPrst/solicitud-view";
	}

	@RequestMapping(value= {"/solicitudNuevaForm", "/editSolicitud"}, params= {"borrador"})
	public String saveBorradorSolicitud(final SolicitudPrstForm form, HttpServletRequest request,
			final BindingResult result, ModelMap model){
		Long idfunc = form.getIdfuncionario();
		try {
			SolicitudPrestamo solicitud = new SolicitudPrestamo();
			if(form.getIdfsolicitud() != null) {
				solicitud = solicitudPrestamoRepository.getOne(form.getIdfsolicitud());
			}
			
			Gplanta fun = new Gplanta(); 
			Optional<Gplanta> f =	gplantaRepository.findById(idfunc);
			if(f.isPresent()) {
				fun = f.get();
			}
			else {
				fun.setIdgplanta(idfunc);
			}
			
			model.addAttribute("nombre", fun.getNombre());
			model.addAttribute("tarjeta", fun.getTarjeta());
			model.addAttribute("funcionario", fun);
			
			if(form.getPrestamo().getCuota() == null) {
				model.addAttribute("formError", "La cuota no puede ser nula. Por favor calcule la cuota antes de guardar el borrador.");
				model.addAttribute("solicitudPrstForm", form);
				model.addAttribute("solNewTab", "active");
				return "/solicitudesPrst/solicitud-view";
			}

			LocalDate fecha = LocalDate.now();
			solicitud.setCancelPrstNros(form.getCancelPrst());
			solicitud.setCantCuotas(form.getPrestamo().getCantCuotas());
			solicitud.setCapitalPrestamo(form.getPrestamo().getCapitalPrestamo());			
			solicitud.setCuota(form.getPrestamo().getCuota());
			solicitud.setEnviadaAfondo(false);
			solicitud.setProcesada(false);
			solicitud.setEnviadaAComision(false);
			solicitud.setAprobada(false);
			solicitud.setAprobada2(false);
			solicitud.setRechazada(false);
			solicitud.setRechazada2(false);
			solicitud.setFechaSolicitud(fecha);
			solicitud.setFuncionario(fun);
			solicitud.setImporteNeto(form.getCapitalNeto());
			solicitud.setInteresPrestamo(form.getPrestamo().getInteresPrestamo());
			solicitud.setTarjeta(fun.getTarjeta());
			TipoPrestamo tp = tipoPrestamoRepository.getOne(form.getIdTipoPrestamo());
			solicitud.setTipoPrestamo(tp);
			solicitud.setCodigoPrestamo(tp.getCodigoPrestamo());
			ParamPrestamo pp = paramPrestamoRepository.getOne(form.getIdparamPrst());
			solicitud.setParamPrestamo(pp);
			if(form.getIdfsolicitud() == null) {
				solicitud = solicitudPrestamoRepository.save(solicitud);				
			}
			else {
				solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
			}
			
			model.addAttribute("listPendientes", solicitudPrestamoService.getByTarjetaSinEnviar(fun.getTarjeta()));
			
			model.addAttribute("formSuccess", "La solicitud ha quedado guardada como borrador.");
			model.addAttribute("sinPrst", true);
			model.addAttribute("solicitpersonal", true);
			model.addAttribute("plantaList", gplantaService.getAllPlanta());
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			model.addAttribute("solicitudPrstForm", new SolicitudPrstForm());
			model.addAttribute("solNewTab", "active");
		} catch (Exception e) {
			model.addAttribute("formError", e.getMessage());
		}
		return "/solicitudesPrst/solicitud-view";		
	}

	@GetMapping("/enviarSolicitud/{id}")
	public String enviarSolicitud(@PathVariable(name="id")Long idsolicitud, ModelMap model) {
		SolicitudPrestamo solicitud = solicitudPrestamoRepository.getOne(idsolicitud);
		LocalDate fecha = LocalDate.now();
		solicitud.setEnviadaAfondo(true);
		solicitud.setProcesada(false);
		solicitud.setFechaSolicitud(fecha);
		try {
			solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
			model.addAttribute("tarjeta", solicitud.getTarjeta());
			model.addAttribute("nombre", solicitud.getFuncionario().getNombre());
			model.addAttribute("listPendientes", solicitudPrestamoService.getByTarjetaSinEnviar(solicitud.getTarjeta()));
			model.addAttribute("tiposPrstList", tipoPrestamoRepository.findAll());
			model.addAttribute("formSuccess", "La solicitud ha quedado ha disposición de la Administración del fondo");
		} catch (Exception e) {
			model.addAttribute("formError", e.getMessage());
			e.printStackTrace();
		}
		if(userService.isLoggedUserADMIN()) {
			return getSolicitudByAdmin(model);
		}
		else {
			return getSolicitudForm(model);			
		}
	}

	private BigDecimal sumaSaldosACancelar(String cancelPrst) {
		BigDecimal sumaSaldos = BigDecimal.ZERO;
		if(cancelPrst != null && !cancelPrst.isEmpty()) {
			List<String> lcancel = Arrays.asList(cancelPrst.split(","));
			for(String nro : lcancel) {
				Optional<Prestamo> prst = prestamoService.findByNroprestamo(Integer.valueOf(nro));
				if(prst.isPresent()) {
					sumaSaldos = sumaSaldos.add(prst.get().getSaldoPrestamo());
				}
			}
		}
		return sumaSaldos;
	}
	
	private BigDecimal sumaCuotasACancelar(EstadoDeCta estadodeCta, String cancelPrst) {
		BigDecimal sumaCuotas = BigDecimal.ZERO;
		if(estadodeCta.getLstPrst() != null) {
			List<Prestamo> lstPrst = estadodeCta.getLstPrst();
			if(cancelPrst != null && !cancelPrst.isEmpty()) {
				List<String> lcancel = Arrays.asList(cancelPrst.split(","));
				
				for(String nro : lcancel) {
					Integer inro = Integer.valueOf(nro);
					for(Prestamo p : lstPrst) {
						Integer pnro = p.getNroprestamo();
						if(pnro.equals(inro)) {
							sumaCuotas = sumaCuotas.add(p.getCuota());
						}
					}
				}				
			}
		}
		return sumaCuotas;
	}
	
	private Boolean chequeoDisponible(Gplanta funcionario, BigDecimal capSolicitado, BigDecimal sumaSaldos) throws Exception {
		Boolean ret = false;
		Optional<Saldos> saldos = saldosRepository.findByTarjeta(funcionario.getTarjeta());
		if(saldos.isPresent()) {
			try {				
				BigDecimal capIntegrado = saldos.get().getCapitalIntegActual();
				Parametro pctReserva = paramService.getParametroByDescripcion("Pct de Reserva");
				BigDecimal reserva = saldos.get().getCapitalIntegActual().multiply(pctReserva.getValor(), MathContext.DECIMAL32).divide(new BigDecimal("100"), RoundingMode.HALF_UP);
				BigDecimal tope = capIntegrado.subtract(reserva).setScale(2, RoundingMode.HALF_UP);
				if(capSolicitado.compareTo(tope) > 0) {
					if(capSolicitado.subtract(tope).compareTo(new BigDecimal("1")) < 0) {
						ret = true;
					}
					else {
						ret =  false;
					}
				}
				else {
					ret = true;
				}
			} catch (ParamNotFoundException e) {
				throw new Exception(e.getMessage());
			}			
		}
		return ret;
	}
		
	private Boolean puedeOperar(Gplanta funcionario) throws ParamNotFoundException {
		try {
			Parametro cantcuotasMin = paramService.getParametroByDescripcion("Cuotas a pagar");
			Prestamo ultimoPrst = prestamoService.getLastByFuncionario(funcionario.getIdgplanta());				
			if(ultimoPrst.getCuotasPagas().intValue() >= cantcuotasMin.getValor().intValue()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(NoResultException pne) {
			return true;
		}
	}

	private Boolean superaCuarentaPorCiento(BigDecimal cuotaActual, EstadoDeCta estadodeCta, BigDecimal sumaCuotas) throws Exception {
		BigDecimal cuarenta = estadodeCta.getCuarentaPorCiento();
		BigDecimal cuotascomprometidas = BigDecimal.ZERO;
		if(estadodeCta.getSumaDeCuotas() != null) {
			cuotascomprometidas = estadodeCta.getSumaDeCuotas();
			cuotascomprometidas = cuotascomprometidas.subtract(sumaCuotas);			
		}
		if(cuotascomprometidas.add(cuotaActual).compareTo(cuarenta) > 0) {
			return true;
		}
		return false;
	}
	
	@RequestMapping("/pdfSolicitudreport/{id}")
    public void estadoReport(HttpServletResponse response, @PathVariable(name="id") Long idsolicitud, Model model) throws DocumentException, IOException, Exception{
		SolicitudPrestamo solicitud = solicitudPrestamoRepository.getOne(idsolicitud);
		Gplanta funcionario = gplantaRepository.getOne(solicitud.getFuncionario().getIdgplanta());
		EstadoDeCta estadodeCta = estadoDeCtaService.getEstadoDeCtabyFuncionario(funcionario.getIdgplanta());
		String mesliquidacion = paramService.getMesliquidacion();
	
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=solicitudreport_" + funcionario.getTarjeta().toString() + ".pdf";
		response.setHeader(headerKey, headerValue);
		SolicitudFormPdfExporter exporter = new SolicitudFormPdfExporter(solicitud, estadodeCta, funcionario, mesliquidacion);
		exporter.export(response);
		
	}

	@GetMapping("/autorizarSolicitud/{id}")
	public String solicitudparaautorizar(@PathVariable(name="id") Long idsolicitud, ModelMap model) {
		
		Optional<SolicitudPrestamo> solicitud = solicitudPrestamoRepository.findById(idsolicitud);
		if(solicitud.isPresent()) {
			String prstcancel = solicitud.get().getCancelPrstNros();
			TipoPrestamo tipoPrst = tipoPrestamoRepository.getOne(solicitud.get().getTipoPrestamo().getIdftipoprestamo());
			model.addAttribute("tipoPrestamo", tipoPrst.getDescripcion());
			
			SolicitudPrstForm solicitudPrstForm = new SolicitudPrstForm();
			solicitudPrstForm.setCancelPrst(solicitud.get().getCancelPrstNros());
			solicitudPrstForm.setCapitalNeto(solicitud.get().getImporteNeto());
			solicitudPrstForm.setIdfuncionario(solicitud.get().getFuncionario().getIdgplanta());
			solicitudPrstForm.setIdfsolicitud(solicitud.get().getIdfsolicitud());
			solicitudPrstForm.setIdTipoPrestamo(solicitud.get().getTipoPrestamo().getIdftipoprestamo());
			if(solicitud.get().getMotivo() != null) {
				solicitudPrstForm.setObsComision(solicitud.get().getMotivo());
			}
			
			String msg = solicitud.get().getAprobada() && solicitud.get().getAprobada2()?"La solicitud cuenta con dos aprobaciones": "";
			if(solicitud.get().getAprobada() && !solicitud.get().getAprobada2()) {
				msg = "La solicitud cuenta con una aprobación";
			}
			else if(!solicitud.get().getAprobada() && !solicitud.get().getAprobada2()){
				msg = "La solicitud no cuenta con ninguna aprobación";
			}
			else if(solicitud.get().getAprobada() && solicitud.get().getRechazada()) {
				msg = "La solicitud cuenta con una aprobación y un rechazo";
			}
			
			String msgreject = solicitud.get().getRechazada()?"La solicitud ha sido rechazada un miembro de la Comisión": "";
			
			Prestamo prst = new Prestamo();
			prst.setCantCuotas(solicitud.get().getCantCuotas());
			prst.setCapitalPrestamo(solicitud.get().getCapitalPrestamo());
			prst.setCuota(solicitud.get().getCuota());
			prst.setInteresPrestamo(solicitud.get().getInteresPrestamo());
			solicitudPrstForm.setPrestamo(prst);

			List<Prestamo> lstCancel = new ArrayList<Prestamo>();
			List<Prestamo> lstPendientes = new ArrayList<Prestamo>();
			
			EstadoDeCta estadodeCta;
			try {
				estadodeCta = estadoDeCtaService.getEstadoDeCtabyFuncionario(solicitud.get().getFuncionario().getIdgplanta());
				solicitudPrstForm.setCuarentaPorCiento(estadodeCta.getCuarentaPorCiento());
				BigDecimal sueldoBase = estadodeCta.getBasico().add(estadodeCta.getComplemento());
				model.addAttribute("sueldoBase", sueldoBase);
				
				if(estadodeCta.getLstPrst() == null) {
					model.addAttribute("sinPrst", true);
				}
				else {
					List<Prestamo> lstPrst = estadodeCta.getLstPrst();
					if(prstcancel != null && !prstcancel.isEmpty()) {
						List<String> lcancel = Arrays.asList(prstcancel.split(","));
						for(String nro : lcancel) {
							Integer inro = Integer.valueOf(nro);
							for(Prestamo p : lstPrst) {
								Integer pnro = p.getNroprestamo();
								if(pnro.equals(inro)) {
									lstCancel.add(p);
								}
							}
						}
					}
					for(Prestamo p : lstPrst) {
						if(prstcancel == null || !prstcancel.contains(p.getNroprestamo().toString())) {
							lstPendientes.add(p);
						}
					}			
					model.addAttribute("lstCancel", lstCancel);
					model.addAttribute("lstPendientes", lstPendientes);
					model.addAttribute("sinPrst", false);
				}
				String clave = "fauthPrst";
				Ayuda help = ayudaService.getByClave(clave);
				model.addAttribute("help", help);
			}
			catch(Exception e) {
				estadodeCta = new EstadoDeCta();
			}

			if(!msg.isEmpty()) {
				model.addAttribute("formSuccess", msg);
			}
			if(!msgreject.isEmpty()) {
				model.addAttribute("formError", msgreject);
			}
			model.addAttribute("estadoDeCta", estadodeCta);
			model.addAttribute("solicitudPrstForm", solicitudPrstForm);
			model.addAttribute("solicitpersonal", false);
			model.addAttribute("funcionario", solicitud.get().getFuncionario());
			model.addAttribute("solNewTab", "active");
		}
		return "/solicitudesPrst/solicitud-view";
	}
	
	@RequestMapping(value= {"/devolverSolicitud"}, params= {"aprobarSol"})
	public String devolverAprobada(final SolicitudPrstForm form, HttpServletRequest request,
			final BindingResult result, ModelMap model) {
	
		SolicitudPrestamo solicitud = solicitudPrestamoRepository.getOne(form.getIdfsolicitud());
		if(solicitud.getAprobada()) {
			solicitud.setAprobada2(true);
		}
		else {
			solicitud.setAprobada(true);			
		}
		if(solicitud.getMotivo().isEmpty()) {
			solicitud.setMotivo(form.getObsComision());			
		}
		else {
			String motivo = solicitud.getMotivo();
			solicitud.setMotivo(motivo + " - " + form.getObsComision());
		}
		try {
			solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
			model.addAttribute("formSuccess", "La solicitud ha quedado ha disposición de la Administración del fondo");			
		}
		catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
		}

		return solicitudparaautorizar(solicitud.getIdfsolicitud(), model);
	}

	@RequestMapping(value="/consistenciaCuota")
	public ResponseEntity<String> verConsistencia(@Valid @RequestBody SolicitudPrstForm param, Errors errors){
	
		try {
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }

		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		

	}

	@RequestMapping(value="/actualizarSolicitud")
	public ResponseEntity<String> actualizarSolicitud(@Valid @RequestBody SolicitudPrstForm param, Errors errors){

		try {
			if(errors.hasErrors()) {
				String result=errors.getAllErrors()
						.stream().map(x -> x.getDefaultMessage())
						.collect(Collectors.joining(""));
				throw new Exception(result);
			}
			User user = userService.getLoggedUser();
			
	        Long id = param.getIdfsolicitud();
	        
	        String obs = param.getObsComision();
	        if(obs.contains(user.getUsername())) {
	        	return ResponseEntity.badRequest().body("Esta solicitud ya ha sido aprobada por el usuario " + user.getUsername());
	        }

	        SolicitudPrestamo solicitud = solicitudPrestamoRepository.getOne(id);
			if(solicitud.getAprobada() && solicitud.getMotivo().contains(user.getUsername())) {
	        	return ResponseEntity.badRequest().body("Esta solicitud ya ha sido aprobada por el usuario " + user.getUsername());
			}
			else if(solicitud.getAprobada()) {
				solicitud.setAprobada2(true);
			}
			else {
				solicitud.setAprobada(true);			
			}
			solicitud.setMotivo(obs.concat(" - " + user.getUsername()));			
			solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}
	
	@RequestMapping(value="/rejectSolicitud")
	public ResponseEntity<String> rejectSolicitud(@Valid @RequestBody SolicitudPrstForm param, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
	        
	        Long id = param.getIdfsolicitud();

	    	User user = userService.getLoggedUser();
			
	        String obs = param.getObsComision();
	        if(obs.contains(user.getUsername())) {
	        	return ResponseEntity.badRequest().body("Esta solicitud ya ha sido rechazada por el usuario " + user.getUsername());
	        }

			SolicitudPrestamo solicitud = solicitudPrestamoRepository.getOne(id);
			
			if(solicitud.getRechazada() && solicitud.getMotivo().contains(user.getUsername())) {
	        	return ResponseEntity.badRequest().body("Esta solicitud ya ha sido rechazada por el usuario " + user.getUsername());
			}
			else if(solicitud.getRechazada()) {
	        	solicitud.setRechazada2(true);
			}
			else {
				solicitud.setRechazada(true);			
			}

			solicitud.setMotivo(obs.concat(" - " + user.getUsername()));			
			solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}

	@RequestMapping(value="/eliminarAprobacion")
	public ResponseEntity<String> eliminarAprobacion(@Valid @RequestBody SolicitudPrstForm param, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
	        
	        Long id = param.getIdfsolicitud();
	        
	        String obs = param.getObsComision();

			SolicitudPrestamo solicitud = solicitudPrestamoRepository.getOne(id);
			
			if(solicitud.getAprobada2()) {
				solicitud.setAprobada2(false);
			}
			else {
				solicitud.setAprobada(false);			
			}

			solicitud.setMotivo(obs);
			solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}

	@RequestMapping(value="/eliminarRechazo")
	public ResponseEntity<String> eliminarRechazo(@Valid @RequestBody SolicitudPrstForm param, Errors errors) {
		try {
			//If error, just return a 400 bad request, along with the error message
	        if (errors.hasErrors()) {
	            String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

	            throw new Exception(result);
	        }
	        
	        Long id = param.getIdfsolicitud();
	        
	        String obs = param.getObsComision();

			SolicitudPrestamo solicitud = solicitudPrestamoRepository.getOne(id);
			
			if(solicitud.getRechazada2()) {
				solicitud.setRechazada2(false);
			}
			else {
				solicitud.setRechazada(false);			
			}

			solicitud.setMotivo(obs);
			solicitud = solicitudPrestamoService.updateSolicitud(solicitud);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");		
	}

}
