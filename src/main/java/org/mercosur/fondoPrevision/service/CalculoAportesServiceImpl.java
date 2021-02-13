package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.mercosur.fondoPrevision.dto.AportesSummary;
import org.mercosur.fondoPrevision.dto.FuncionarioConSueldoMes;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.entities.Parametro;
import org.mercosur.fondoPrevision.entities.Saldos;
import org.mercosur.fondoPrevision.entities.SaldosHistoria;
import org.mercosur.fondoPrevision.entities.SueldoMes;
import org.mercosur.fondoPrevision.entities.TipoMovimiento;
import org.mercosur.fondoPrevision.repository.FcodigosRepository;
import org.mercosur.fondoPrevision.repository.GcargoRepository;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.GvinculoFuncionarioCargoRepository;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.repository.ParametroRepository;
import org.mercosur.fondoPrevision.repository.SaldosHistoriaRepository;
import org.mercosur.fondoPrevision.repository.SaldosRepository;
import org.mercosur.fondoPrevision.repository.SueldoMesRepository;
import org.mercosur.fondoPrevision.repository.TipoMovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculoAportesServiceImpl implements CalculoAportesService {

	@Autowired
	MovimientosRepository movimientosRepository;
	
	@Autowired
	SaldosRepository saldosRepository;
	
	@Autowired
	SaldosHistoriaRepository saldosHistoriaRepository;
		
	@Autowired
	SueldoMesRepository sueldoMesRepository;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Autowired
	GplantaService gplantaService;
	
	@Autowired
	GcargoRepository gcargoRepository;
	
	@Autowired
	TipoMovimientoRepository tipoMovimientoRepository;
	
	@Autowired
	FcodigosRepository fcodigosRepository;
	
	@Autowired
	ParametroRepository parametroRepository;
	
	@Autowired
	GvinculoFuncionarioCargoRepository gvinculoFunCargoRepository;
	
	@Autowired
	LogfondoService logfondoService;
	
	
	@Override
	public Boolean controlDeEjecucion(String mesLiquidacion) throws Exception {
		Iterable<Movimientos> lstMovs = movimientosRepository.getByCodigoMovAndMes(Short.valueOf("2"), mesLiquidacion);
		if(StreamSupport.stream(lstMovs.spliterator(), false).count() > 0) {
			for (Movimientos mov : lstMovs) {
				if(mov.getObservaciones() != null && (mov.getObservaciones().contains("Por cierre de cuenta"))) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
	
	private List<String> buscarCtasNuevas(String mesLiquidacion) throws Exception {
		try {
			Iterable<Movimientos> lstMovs = movimientosRepository.getByCodigoMovAndMes(Short.valueOf("1"), mesLiquidacion);
			List<String> tarjetasQL = new ArrayList<String>();
			if(StreamSupport.stream(lstMovs.spliterator(), false).count() > 0) {
				for(Movimientos mov:lstMovs) {
					tarjetasQL.add(mov.getTarjeta().toString());
				}
			}
			return tarjetasQL;
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}	
	
	@Override
	public String calculoDeAportes(String mesLiquidacion) throws Exception {

		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		java.sql.Date fecha = new java.sql.Date(currentDate.getTime());
		BigDecimal dispactual = BigDecimal.ZERO;
		BigDecimal integactual = BigDecimal.ZERO;
		
		BigDecimal aporteSec = BigDecimal.ZERO;
		BigDecimal aporteFun = BigDecimal.ZERO;
		BigDecimal aporteTot = BigDecimal.ZERO;
		BigDecimal divisor = BigDecimal.valueOf((long)100);
		BigDecimal basico = BigDecimal.ZERO;
		BigDecimal complemento = BigDecimal.ZERO;
		BigDecimal basicosec = BigDecimal.ZERO;
		String observ = "Aportes mensuales";
		
			//codigoMovimiento = 2 --> aportes
		TipoMovimiento tipoMov = tipoMovimientoRepository.findByCodigoMovimiento(Short.valueOf("2"));
			//fcodigos = 69 --> aporte del funcionario al fondo
		Iterable<Parametro> lstPar = parametroRepository.getSomeByDesc("Aporte");
		List<String>tarjetasQL = buscarCtasNuevas(mesLiquidacion);
		List<FuncionarioConSueldoMes> lstSMUTF = getAllFuncionarios();
		
		for(FuncionarioConSueldoMes fcsm : lstSMUTF) {
			if(!tarjetasQL.contains(fcsm.getTarjeta().toString())) {
				basico = fcsm.getBasico();
				complemento = fcsm.getComplemento();
			
				for(Parametro p:lstPar) {
					if(p.getDescripcion().contains("funcionario") || p.getDescripcion().contains("Funcionario")) {
						aporteFun = basico.multiply(p.getValor()).divide(divisor).setScale(2, RoundingMode.HALF_UP);
					}
					else if(p.getDescripcion().contains("patronal")) {
						if(complemento.compareTo(BigDecimal.ZERO) > 0) {
							basicosec = basico.add(complemento);
						}
						aporteSec = (basicosec.multiply(p.getValor()).divide(divisor)).setScale(2, RoundingMode.HALF_UP);
					}
				}
				
				aporteTot = aporteSec.add(aporteFun);
				
				Gplanta f = gplantaRepository.getOne(fcsm.getIdfuncionario());
				Optional<Saldos>saldosf = saldosRepository.findByTarjeta(f.getTarjeta());
				if(saldosf.isPresent()) {
					Saldos saldosactuales = saldosf.get();
					dispactual = saldosactuales.getCapitalDispActual();
					integactual = saldosactuales.getCapitalIntegActual();
					saldosactuales.setFecha(currentDate);
					saldosactuales.setCapitalDispAntes(dispactual);
					saldosactuales.setCapitalIntegAntes(integactual);
					saldosactuales.setCapitalDispActual(saldosactuales.getCapitalDispActual().add(aporteTot));
					saldosactuales.setCapitalIntegActual(saldosactuales.getCapitalIntegActual().add(aporteTot));
					saldosactuales.setNumerales(saldosactuales.getNumerales().add(aporteTot));
					saldosactuales.setMesliquidacion(mesLiquidacion);
					saldosactuales = saldosRepository.save(saldosactuales);

					SaldosHistoria saldosh = saldosHistoriaRepository.getUltimoByTarjeta(f.getTarjeta());				

					SaldosHistoria saldosHnew = new SaldosHistoria();
					saldosHnew.setCapitalDispAntes(saldosh.getCapitalDispActual());
					saldosHnew.setCapitalIntegAntes(saldosh.getCapitalIntegActual());
					saldosHnew.setGplanta_id(saldosh.getGplanta_id());
					saldosHnew.setMesliquidacion(mesLiquidacion);
					saldosHnew.setTarjeta(saldosh.getTarjeta());
					saldosHnew.setFecha(currentDate);
					saldosHnew.setCapitalDispActual(saldosactuales.getCapitalDispActual());
					saldosHnew.setCapitalIntegActual(saldosactuales.getCapitalIntegActual());
					saldosHnew.setNumerales(saldosactuales.getNumerales());
					saldosHnew.setMotivo(observ);
					
					saldosHnew = saldosHistoriaRepository.save(saldosHnew);
				
				}
				else {
					throw new Exception("Saldos del funcionario " + f.getNombre() + " no encontrados!");
				}
				BigDecimal saldoant = BigDecimal.ZERO;
				BigDecimal saldoact = aporteTot;
				
				try {
					Movimientos mov = movimientosRepository.getLastByFunc(f.getTarjeta());				
					saldoant = mov.getSaldoActual();
					saldoact = mov.getSaldoActual().add(aporteTot);
				}
				catch(Exception e) {
					throw new Exception(e);
				}
					
				Movimientos newmov = new Movimientos();
				newmov.setFechaMovimiento(fecha);
				newmov.setCodigoMovimiento(tipoMov.getCodigoMovimiento());
				newmov.setTipoMovimiento(tipoMov);
				newmov.setFuncionario(f);
				newmov.setTarjeta(f.getTarjeta());
				newmov.setImporteCapSec(aporteSec);
				newmov.setImporteIntFunc(aporteFun);
				newmov.setImporteMov(aporteTot);
				newmov.setMesliquidacion(mesLiquidacion);
				newmov.setNroCuota(0);
				newmov.setNroPrestamo(0);
				newmov.setObservaciones(observ);
				newmov.setSaldoActual(saldoact);
				newmov.setSaldoAnterior(saldoant);
				newmov = movimientosRepository.save(newmov);
			}
		}
		logfondoService.agregarLog("Cálculo de Aportes", "correspondiente al mes: " + mesLiquidacion);
		return "success";
	}

	
	@Override
	public List<AportesSummary> obtenerResultadosAportes(String mesliquidacion) throws Exception {
		List<AportesSummary> lstAportes = new ArrayList<AportesSummary>();
		try {
			List<Movimientos> lstMov = movimientosRepository.getByCodigosAndMes("1, 2", mesliquidacion);
			for(Movimientos m : lstMov) {
				Optional<Saldos> saldos = saldosRepository.findByTarjeta(m.getTarjeta());
				AportesSummary aps = new AportesSummary();
				aps.setAporteFun(m.getImporteIntFunc());
				aps.setAporteSec(m.getImporteCapSec());
				aps.setAporteTotal(m.getImporteMov());
				if(saldos.isPresent()) {
					aps.setCapDispActual(saldos.get().getCapitalDispActual());
					aps.setCapDispAntes(saldos.get().getCapitalDispAntes());
					aps.setCapIntegActual(saldos.get().getCapitalIntegActual());
					aps.setCapIntegAntes(saldos.get().getCapitalIntegAntes());
				}
				aps.setFecha(m.getFechaMovimiento());
				aps.setNombre(m.getFuncionario().getNombre());
				aps.setTarjeta(m.getTarjeta());
				lstAportes.add(aps);
			}
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return lstAportes;
	}

	@Override
	public Boolean actualizacionBasicoyComplemento(Gplanta funcionario, String mesLiquidacion, BigDecimal basico, BigDecimal complemento)
			throws Exception {
		Calendar calendar = Calendar.getInstance();
		Date fecha = calendar.getTime();
		
		SueldoMes sueldoMes = new SueldoMes();
		sueldoMes.setAniomes(mesLiquidacion);
		sueldoMes.setComplemento(complemento);
		sueldoMes.setFecha(fecha);
		sueldoMes.setFuncionario(funcionario);
		sueldoMes.setSueldomes(basico);
		sueldoMes.setTarjeta(funcionario.getTarjeta());
		sueldoMes.setMotivo("modificación mensual");
		sueldoMes = sueldoMesRepository.save(sueldoMes);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FuncionarioConSueldoMes> getAllFuncionarios() throws Exception {
		Iterable<Gplanta> lstPlanta = gplantaService.getAllPlanta();
		List<FuncionarioConSueldoMes> lstResult = new ArrayList<FuncionarioConSueldoMes>();
		String mesliq = parametroRepository.findByMesliquidacionDistinct();
		BigDecimal basico = BigDecimal.ZERO;
		BigDecimal complemento = BigDecimal.ZERO;
		Calendar calendar = Calendar.getInstance();
		Date hoy = calendar.getTime();
		
		for(Gplanta gp : lstPlanta) {
			List<SueldoMes> lstsueldos = gp.getLstsueldos();
			Boolean encontrado = false;
			if(!lstsueldos.isEmpty()) {
				for(SueldoMes sm : lstsueldos) {
					if(sm.getAniomes().equals(mesliq)) {
						basico = sm.getSueldomes();
						complemento = sm.getComplemento();
						encontrado = true;
					}
				}
			}
			if(!encontrado) {
				basico = gp.getGcargo().getBasico();
				complemento = gp.getGcargo().getComplemento();
				SueldoMes sm = new SueldoMes();
				sm.setAniomes(mesliq);
				sm.setComplemento(complemento);
				sm.setFecha(hoy);
				sm.setFuncionario(gp);
				sm.setMotivo("datos mensuales para Aguinaldo");
				sm.setSueldomes(basico);
				sm.setTarjeta(gp.getTarjeta());
				sm = sueldoMesRepository.save(sm);
				
			}
			FuncionarioConSueldoMes funcsm = new FuncionarioConSueldoMes(gp.getIdgplanta(), gp.getTarjeta(),
					gp.getNombre(), basico, complemento, gp.getIngreso(), gp.getGcargo().getDescripCargo());
			lstResult.add(funcsm);
		}
		return lstResult;
	}

	@Override
	public List<FuncionarioConSueldoMes> getAllFuncionariosByUnidad(String unidad) throws Exception {
		Iterable<Gplanta> lstPlanta = gplantaService.getAllByUnidad(unidad);
		List<FuncionarioConSueldoMes> lstResult = new ArrayList<FuncionarioConSueldoMes>();
		String mesliq = parametroRepository.findByMesliquidacionDistinct();
		BigDecimal basico = BigDecimal.ZERO;
		BigDecimal complemento = BigDecimal.ZERO;
		
		for(Gplanta gp : lstPlanta) {
			basico = gp.getGcargo().getBasico();
			complemento = gp.getGcargo().getComplemento();
			List<SueldoMes> lstsueldos = gp.getLstsueldos();
			if(lstsueldos != null) {
				for(SueldoMes sm : lstsueldos) {
					if(sm.getAniomes().equals(mesliq)) {
						basico = sm.getSueldomes();
						complemento = sm.getComplemento();
					}
				}
			}
			FuncionarioConSueldoMes funcsm = new FuncionarioConSueldoMes(gp.getIdgplanta(), gp.getTarjeta(),
					gp.getNombre(), basico, complemento, gp.getIngreso(), gp.getGcargo().getDescripCargo());
			lstResult.add(funcsm);
		}
		return lstResult;
	}
	
	private BigDecimal sumaBasicos(String aniomes1, String aniomes2, Integer tarjeta) throws Exception {
		BigDecimal totbasico = BigDecimal.ZERO;

		List<SueldoMes> lstSueldoMes = sueldoMesRepository.getByPeriodoAndTarjeta(aniomes1, aniomes2, tarjeta);
		for(SueldoMes sm : lstSueldoMes) {
			totbasico = totbasico.add(sm.getSueldomes());
		}
		return totbasico;
	}

	private BigDecimal sumaComplementos(String aniomes1, String aniomes2, Integer tarjeta) throws Exception{
		BigDecimal totcomplemento = BigDecimal.ZERO;

		List<SueldoMes> lstSueldoMes = sueldoMesRepository.getByPeriodoAndTarjeta(aniomes1, aniomes2, tarjeta);
		for(SueldoMes sm : lstSueldoMes) {
			totcomplemento = totcomplemento.add(sm.getComplemento());
		}
		return totcomplemento;		
	}

	@Override
	public Boolean controlDeEjecucionAguinaldo(String mesliquidacion) throws Exception {
		Iterable<Movimientos> lstMovs = movimientosRepository.getByCodigoMovAndMes(Short.valueOf("9"), mesliquidacion);
		if(StreamSupport.stream(lstMovs.spliterator(), false).count() > 0) {
			for (Movimientos mov : lstMovs) {
				if(mov.getObservaciones() != null && (mov.getObservaciones().contains("Por cierre de cuenta"))) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public String calculoDeAportesAguinaldo(String aniomes1, String aniomes2) throws Exception {
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		java.sql.Date fecha = new java.sql.Date(currentDate.getTime());
		BigDecimal aporteSec = BigDecimal.ZERO;
		BigDecimal aporteFun = BigDecimal.ZERO;
		BigDecimal aporteTot = BigDecimal.ZERO;
		BigDecimal divisor = BigDecimal.valueOf((long)100);
		BigDecimal divDoce = BigDecimal.valueOf((long) 12);
		BigDecimal basico = BigDecimal.ZERO;
		BigDecimal basicosec = BigDecimal.ZERO;
		BigDecimal complemento = BigDecimal.ZERO;
		String observ = "Aportes Aguinaldo";
		
			//codigoMovimiento = 9 --> aportes aguinaldo
		TipoMovimiento tipoMov = tipoMovimientoRepository.findByCodigoMovimiento(Short.valueOf("9"));
		Iterable<Parametro> lstPar = parametroRepository.getSomeByDesc("Aporte");
		List<FuncionarioConSueldoMes> lstSMUTF = getAllFuncionarios();
		
		for(FuncionarioConSueldoMes fcsm : lstSMUTF) {
			basico = sumaBasicos(aniomes1, aniomes2, fcsm.getTarjeta());
			basico = basico.divide(divDoce, RoundingMode.HALF_UP);
			complemento = sumaComplementos(aniomes1, aniomes2,fcsm.getTarjeta());
			complemento = complemento.divide(divDoce, RoundingMode.HALF_UP);
			
			for(Parametro p:lstPar) {
				if(p.getDescripcion().contains("funcionario") || p.getDescripcion().contains("Funcionario")) {
					aporteFun = basico.multiply(p.getValor()).divide(divisor).setScale(2, RoundingMode.HALF_UP);
				}
				else if(p.getDescripcion().contains("patronal")) {
					if(complemento.compareTo(BigDecimal.ZERO) > 0) {
						basicosec = basico.add(complemento);
					}
					aporteSec = (basicosec.multiply(p.getValor()).divide(divisor)).setScale(2, RoundingMode.HALF_UP);
				}
			}
			
			aporteTot = aporteSec.add(aporteFun);
			String mesLiquidacion = parametroRepository.findByMesliquidacionDistinct();
			Gplanta f = gplantaRepository.getOne(fcsm.getIdfuncionario());
			Optional<Saldos>saldosf = saldosRepository.findByTarjeta(f.getTarjeta());
			BigDecimal dispactual = BigDecimal.ZERO;
			BigDecimal integactual = BigDecimal.ZERO;
			if(saldosf.isPresent()) {
				Saldos saldosactuales = saldosf.get();
				dispactual = saldosactuales.getCapitalDispActual();
				integactual = saldosactuales.getCapitalIntegActual();
				saldosactuales.setFecha(currentDate);
				saldosactuales.setCapitalDispAntes(dispactual);
				saldosactuales.setCapitalIntegAntes(integactual);
				saldosactuales.setCapitalDispActual(saldosactuales.getCapitalDispActual().add(aporteTot));
				saldosactuales.setCapitalIntegActual(saldosactuales.getCapitalIntegActual().add(aporteTot));
				saldosactuales.setNumerales(saldosactuales.getNumerales().add(aporteTot));
				saldosactuales.setMesliquidacion(mesLiquidacion);
				saldosactuales = saldosRepository.save(saldosactuales);
				
				SaldosHistoria saldosh = saldosHistoriaRepository.getUltimoByTarjeta(f.getTarjeta());				

				SaldosHistoria saldosHnew = new SaldosHistoria();
				saldosHnew.setCapitalDispAntes(saldosh.getCapitalDispActual());
				saldosHnew.setCapitalIntegAntes(saldosh.getCapitalIntegActual());
				saldosHnew.setGplanta_id(saldosh.getGplanta_id());
				saldosHnew.setMesliquidacion(mesLiquidacion);
				saldosHnew.setTarjeta(saldosh.getTarjeta());
				saldosHnew.setFecha(currentDate);
				saldosHnew.setCapitalDispActual(saldosactuales.getCapitalDispActual());
				saldosHnew.setCapitalIntegActual(saldosactuales.getCapitalIntegActual());
				saldosHnew.setNumerales(saldosactuales.getNumerales());
				saldosHnew.setMotivo(observ);
				
				saldosHnew = saldosHistoriaRepository.save(saldosHnew);
			}
			else {
				throw new Exception("Saldos del funcionario " + f.getNombre() + " no encontrados!");
			}
			BigDecimal saldoant = BigDecimal.ZERO;
			BigDecimal saldoact = aporteTot;
			
			try {
				Movimientos mov = movimientosRepository.getLastByFunc(f.getTarjeta());				
				saldoant = mov.getSaldoActual();
				saldoact = mov.getSaldoActual().add(aporteTot);
			}
			catch(Exception e) {
				throw new Exception(e);
			}
				
			Movimientos newmov = new Movimientos();
			newmov.setFechaMovimiento(fecha);
			newmov.setCodigoMovimiento(tipoMov.getCodigoMovimiento());
			newmov.setTipoMovimiento(tipoMov);
			newmov.setFuncionario(f);
			newmov.setTarjeta(f.getTarjeta());
			newmov.setImporteCapSec(aporteSec);
			newmov.setImporteIntFunc(aporteFun);
			newmov.setImporteMov(aporteTot);
			newmov.setMesliquidacion(mesLiquidacion);
			newmov.setNroCuota(0);
			newmov.setNroPrestamo(0);
			newmov.setObservaciones(observ);
			newmov.setSaldoActual(saldoact);
			newmov.setSaldoAnterior(saldoant);
			newmov = movimientosRepository.save(newmov);
			
		}
		
		logfondoService.agregarLog("Aportes Aguinaldo");
		return "success";
	}

	@Override
	public List<AportesSummary> obtenerResultadosAportesAguinaldo(String mesliquidacion) throws Exception {
		List<AportesSummary> lstAportes = new ArrayList<AportesSummary>();
		try {
			List<Movimientos> lstMov = movimientosRepository.getByCodigosAndMes("9", mesliquidacion);
			for(Movimientos m : lstMov) {
				Optional<Saldos> saldos = saldosRepository.findByTarjeta(m.getTarjeta());
				AportesSummary aps = new AportesSummary();
				aps.setAporteFun(m.getImporteIntFunc());
				aps.setAporteSec(m.getImporteCapSec());
				aps.setAporteTotal(m.getImporteMov());
				if(saldos.isPresent()) {
					aps.setCapDispActual(saldos.get().getCapitalDispActual());
					aps.setCapDispAntes(saldos.get().getCapitalDispAntes());
					aps.setCapIntegActual(saldos.get().getCapitalIntegActual());
					aps.setCapIntegAntes(saldos.get().getCapitalIntegAntes());
				}
				aps.setFecha(m.getFechaMovimiento());
				aps.setNombre(m.getFuncionario().getNombre());
				aps.setTarjeta(m.getTarjeta());
				lstAportes.add(aps);
			}
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return lstAportes;
	}

	@Override
	public List<AportesSummary> obtenerResultadosMensualYAguinaldo(String mesliquidacion) throws Exception {
		List<AportesSummary> lstAportes = new ArrayList<AportesSummary>();
		try {
			List<Movimientos> lstMov = movimientosRepository.getSumasByCodigosAndMes("1, 2, 9", mesliquidacion);
			for(Movimientos m : lstMov) {
				Gplanta funcionario = gplantaService.getFuncionarioByTarjeta(m.getTarjeta());
				List<SaldosHistoria> lstSaldos = saldosHistoriaRepository.getByTarjetaAndMesliquidacion(m.getTarjeta(), mesliquidacion);
				BigDecimal capdispantes = lstSaldos.get(0).getCapitalDispAntes();
				BigDecimal capintegantes = lstSaldos.get(0).getCapitalIntegAntes();
				BigDecimal capdispactual = capdispantes.add(m.getImporteMov());
				BigDecimal capintegactual = capintegantes.add(m.getImporteMov());
				AportesSummary aps = new AportesSummary();
				aps.setAporteFun(m.getImporteIntFunc());
				aps.setAporteSec(m.getImporteCapSec());
				aps.setAporteTotal(m.getImporteMov());
				aps.setCapDispActual(capdispactual);
				aps.setCapDispAntes(capdispantes);
				aps.setCapIntegActual(capintegactual);
				aps.setCapIntegAntes(capintegantes);
				aps.setNombre(funcionario.getNombre());
				aps.setTarjeta(m.getTarjeta());					
				lstAportes.add(aps);
			}
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return lstAportes;
	}

}
