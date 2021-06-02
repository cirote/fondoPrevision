package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mercosur.fondoPrevision.dto.ResultadoDistribSummary;
import org.mercosur.fondoPrevision.entities.DatosDistribucion;
import org.mercosur.fondoPrevision.entities.Fcodigos;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.entities.ResultadoDistribucion;
import org.mercosur.fondoPrevision.entities.Saldos;
import org.mercosur.fondoPrevision.entities.SaldosHistoria;
import org.mercosur.fondoPrevision.entities.TipoMovimiento;
import org.mercosur.fondoPrevision.repository.DatosDistribucionRepository;
import org.mercosur.fondoPrevision.repository.FcodigosRepository;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.repository.ResultadoDistribucionRepository;
import org.mercosur.fondoPrevision.repository.SaldosHistoriaRepository;
import org.mercosur.fondoPrevision.repository.SaldosRepository;
import org.mercosur.fondoPrevision.repository.TipoMovimientoRepository;

@Service
public class DistribucionUtilidadesServiceImpl implements DistribucionUtilidadesService {

	@Autowired
	SaldosHistoriaRepository saldosHistoriaRepository;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Autowired
	SaldosRepository saldosRepository;
	
	@Autowired
	ResultadoDistribucionRepository resultadoDistribucionRepository;
	
	@Autowired
	DatosDistribucionRepository datosDistribucionRepository;
	
	@Autowired
	ParamService paramService;
	
	@Autowired
	TipoMovimientoRepository tipoMovimientoRepository;
	
	@Autowired
	MovimientosRepository movimientosRepository;
	
	@Autowired
	FcodigosRepository fcodigosRepository;
		
	@Autowired
	LogfondoService logfondoService;
	
	@Override
	public DatosDistribucion registrarDatos(String mesdistrib) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DatosDistribucion salvarDatosDistribucion(String mesFinejercicio, BigDecimal sumaDistrib,
			BigDecimal sumaNumerales) throws Exception {

		DatosDistribucion datos = new DatosDistribucion();
		datos.setMesDistrib(mesFinejercicio);
		datos.setResultadoADistrib(sumaDistrib);
		datos.setTotBaseDistrib(sumaNumerales);
		
		return datosDistribucionRepository.save(datos);
	}


	@Override
	public List<ResultadoDistribSummary> distribuirUtilidades(String tarjetasQL, String anioMes1, String anioMes2,
			DatosDistribucion datos) throws Exception{
		
		Calendar calendar = Calendar.getInstance();
		Date fecha = calendar.getTime();
		java.sql.Date fechaHoy = new java.sql.Date(fecha.getTime());
		
		String mesLiquidacion = paramService.getMesliquidacion();
		String mesDistribucion = getMesDistrib(mesLiquidacion, anioMes2);
		
		Iterable<Gplanta> lstFuncs = gplantaRepository.getFuncsNotInGroup(tarjetasQL);
		BigDecimal bdSuma = datos.getTotBaseDistrib();
		BigDecimal bdSumaDistrib = datos.getResultadoADistrib();
		
		for(Gplanta f : lstFuncs){
			BigDecimal bdNumerales = saldosHistoriaRepository.numeralesFuncionario(anioMes2, f.getTarjeta());
			BigDecimal bdPctFunc = bdNumerales.multiply(new BigDecimal("100")).divide(bdSuma, 5, RoundingMode.HALF_EVEN);
			BigDecimal bdLeToca = bdPctFunc.multiply(bdSumaDistrib).divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
			
			if(actualizacionSaldos(bdLeToca, f.getTarjeta(), fechaHoy, mesDistribucion)){
				Fcodigos codigos = fcodigosRepository.findByCodigo(Short.valueOf("28"));
				TipoMovimiento tipoMov = tipoMovimientoRepository.findByCodigoMovimiento(Short.valueOf("6"));
				
				ResultadoDistribucion fresultDistrib = new ResultadoDistribucion();
				fresultDistrib.setFcodigos(codigos);
				fresultDistrib.setFuncionario(f);
				fresultDistrib.setTarjeta(f.getTarjeta());
				fresultDistrib.setFecha(fechaHoy);
				fresultDistrib.setMesInicial(anioMes1);
				fresultDistrib.setMesFinal(anioMes2);
				fresultDistrib.setMesLiquidacion(mesDistribucion);
				fresultDistrib.setNumeralesFuncionario(bdNumerales);
				fresultDistrib.setNumeralesTodos(bdSuma);
				fresultDistrib.setTotalADistribuir(bdSumaDistrib);
				fresultDistrib.setPctFuncionario(bdPctFunc);
				fresultDistrib.setDistribucionFuncionario(bdLeToca);
				resultadoDistribucionRepository.save(fresultDistrib);
				
				Movimientos mov = movimientosRepository.getLastByFunc(f.getTarjeta());
				
				BigDecimal bdsaldoAnterior = mov.getSaldoActual();
				BigDecimal bdsaldoActual = bdsaldoAnterior.add(bdLeToca);
				
				Movimientos newMov = new Movimientos();
				newMov.setFechaMovimiento(fechaHoy);
				newMov.setTipoMovimiento(tipoMov);
				newMov.setFuncionario(f);
				newMov.setTarjeta(f.getTarjeta());
				newMov.setTipoMovimiento(tipoMov);
				newMov.setCodigoMovimiento((short)6);
				newMov.setNroPrestamo(0);
				newMov.setNroCuota(0);
				newMov.setImporteMov(bdLeToca);
				newMov.setImporteCapSec(BigDecimal.ZERO);
				newMov.setImporteIntFunc(bdLeToca);
				newMov.setSaldoAnterior(bdsaldoAnterior);
				newMov.setSaldoActual(bdsaldoActual);
				newMov.setMesliquidacion(mesDistribucion);
				newMov.setObservaciones("Distribucion de Utilidades");
				movimientosRepository.save(newMov);
				
			}
			
		}
		logfondoService.agregarLog("Distribución de Utilidades", "Distribución del período: " + anioMes1 + " - " + anioMes2 + " finalizó exitosamente!");
		return this.obtenerResultados(mesDistribucion);
	}

	@Override
	public List<ResultadoDistribSummary> obtenerResultados(String mesDistribucion){
		List<ResultadoDistribSummary> lstDistribucion = new ArrayList<ResultadoDistribSummary>();
		
		List<ResultadoDistribucion> lstDist = resultadoDistribucionRepository.getByMesDistrib(mesDistribucion);
		
		SaldosHistoria saldosH = null;
		
		for(ResultadoDistribucion frd: lstDist){
			try {
			saldosH = saldosHistoriaRepository.getByTarjetaAndMesDistribucion(frd.getTarjeta(), mesDistribucion);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
			ResultadoDistribSummary resDis = new ResultadoDistribSummary();
			resDis.setTarjeta(frd.getTarjeta());
			resDis.setNombre(frd.getFuncionario().getNombre());
			if(saldosH == null){
				resDis.setCapitalIntegActual(new BigDecimal("0"));
				resDis.setCapitalDispActual(new BigDecimal("0"));
			}
			else{
				resDis.setCapitalDispAntes(saldosH.getCapitalDispAntes());
				resDis.setCapitalIntegAntes(saldosH.getCapitalIntegAntes());
				resDis.setCapitalIntegActual(saldosH.getCapitalIntegActual());
				resDis.setCapitalDispActual(saldosH.getCapitalDispActual());
			}
			resDis.setPctfuncionario(frd.getPctFuncionario());
			resDis.setTotalADistribuir(frd.getTotalADistribuir());
			resDis.setResultadoDistrib(frd.getDistribucionFuncionario());
			
			lstDistribucion.add(resDis);
		}
		return lstDistribucion;
	}
	
	private String getMesDistrib(String anioMesParam, String anioMes2) {
		String anioMesRet = anioMesParam;
		Integer mp = Integer.valueOf(anioMesParam);
		Integer ms = Integer.valueOf(anioMes2);
		if(mp == ms) {
			anioMesRet = String.valueOf(mp + 1);
		}
		return anioMesRet;
	}
	

	
	private Boolean actualizacionSaldos(BigDecimal bdLeToca, Integer tarjeta, Date fechaHoy, String mesDistribucion){
		Optional<Saldos> saldos = saldosRepository.findByTarjeta(tarjeta);
		Saldos saldospresent = new Saldos();
		
		if(saldos.isPresent()) {
			saldospresent = saldos.get();
			
			BigDecimal bddispActual = saldospresent.getCapitalDispActual();
			BigDecimal bdintegActual = saldospresent.getCapitalIntegActual();
			
			
			saldospresent.setFecha(fechaHoy);
			saldospresent.setCapitalDispAntes(bddispActual);
			saldospresent.setCapitalDispActual(bddispActual.add(bdLeToca));
			saldospresent.setCapitalIntegAntes(bdintegActual);
			saldospresent.setCapitalIntegActual(bdintegActual.add(bdLeToca));
			saldospresent.setNumerales(bdintegActual.add(bdLeToca));
			saldospresent.setMesliquidacion(mesDistribucion);
					
			saldospresent = saldosRepository.save(saldospresent);

			try {
				SaldosHistoria saldosh = saldosHistoriaRepository.getUltimoByTarjeta(tarjeta);				

				SaldosHistoria saldosHnew = new SaldosHistoria();
				saldosHnew.setCapitalDispAntes(saldosh.getCapitalDispActual());
				saldosHnew.setCapitalIntegAntes(saldosh.getCapitalIntegActual());
				saldosHnew.setGplanta_id(saldosh.getGplanta_id());
				saldosHnew.setMesliquidacion(mesDistribucion);
				saldosHnew.setTarjeta(saldosh.getTarjeta());
				saldosHnew.setFecha(fechaHoy);
				saldosHnew.setNumerales(saldospresent.getNumerales());
				saldosHnew.setCapitalDispActual(saldospresent.getCapitalDispActual());
				saldosHnew.setCapitalIntegActual(saldospresent.getCapitalIntegActual());
				saldosHnew.setMotivo("Distribución de utilidades");
				
				saldosHnew = saldosHistoriaRepository.save(saldosHnew);
				return true;
			}
			catch(Exception ex) {
				return false;
			}
		}
		return false;
	}

	@Override
	public Boolean distribucionRealizada(String mesDistrib) throws Exception {
		try {
			DatosDistribucion dd = datosDistribucionRepository.getPorMesDistribucion(mesDistrib);
			if(dd != null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}

	@Override
	public BigDecimal getSumaNumeralesPeriodoyTarjetas(String mes2, String tarjetas) throws Exception {
		return saldosHistoriaRepository.totalNumeralesPorTajetasSinDistribucion(mes2, tarjetas);
	}

	@Override
	public BigDecimal getSumaADistribuir(String mesDistrib) throws Exception{
		return resultadoDistribucionRepository.getSumaADistrib(mesDistrib);
	}
	
	@Override
	public List<String> getMesesDistribucion() throws Exception {
		return resultadoDistribucionRepository.getMesesDistribucion();
	}

	@Override
	public BigDecimal getSumaNumeralesSinDistribucion(String aniomes) throws Exception {
		return saldosHistoriaRepository.totalNumeralesSinDistribucion(aniomes);
	}

	@Override
	public BigDecimal getSumaNumeralesConDistribucion(String aniomes) throws Exception {
		return saldosHistoriaRepository.totalNumeralesConDistribucion(aniomes);
	}

	@Override
	public BigDecimal getSumaNumeralesSinDistribucionPorTarjetas(String aniomes, String tarjetas) throws Exception {
		return saldosHistoriaRepository.totalNumeralesPorTajetasSinDistribucion(aniomes, tarjetas);
	}

	@Override
	public BigDecimal getSumaNumeralesConDistribucionPorTarjetas(String aniomes, String tarjetas) throws Exception {
		return saldosHistoriaRepository.totalNumeralesPorTarjetasConDistribucion(aniomes, tarjetas);
	}

}
