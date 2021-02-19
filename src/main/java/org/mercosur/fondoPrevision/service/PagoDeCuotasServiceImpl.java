package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mercosur.fondoPrevision.dto.CuotasPagas;
import org.mercosur.fondoPrevision.entities.CuentaGlobal;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.Prestamohist;
import org.mercosur.fondoPrevision.entities.SaldoPrestamosAcum;
import org.mercosur.fondoPrevision.entities.Saldos;
import org.mercosur.fondoPrevision.entities.SaldosHistoria;
import org.mercosur.fondoPrevision.entities.TipoMovimiento;
import org.mercosur.fondoPrevision.repository.CuentaGlobalRepository;
import org.mercosur.fondoPrevision.repository.FcodigosRepository;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.repository.PrestamoRepository;
import org.mercosur.fondoPrevision.repository.PrestamohistRepository;
import org.mercosur.fondoPrevision.repository.SaldoPrestamosAcumRepository;
import org.mercosur.fondoPrevision.repository.SaldosHistoriaRepository;
import org.mercosur.fondoPrevision.repository.SaldosRepository;
import org.mercosur.fondoPrevision.repository.SaldosUltimoMesRepository;
import org.mercosur.fondoPrevision.repository.TipoMovimientoRepository;


@Service
public class PagoDeCuotasServiceImpl implements PagoDeCuotasService{

	@Autowired
	PrestamoRepository prestamoRepository;
	
	@Autowired
	PrestamohistRepository prestamohistRepository;
	
	@Autowired
	ParamService paramService;
	
	@Autowired
	LogfondoService logfondoService;
	
	@Autowired
	SaldosRepository saldosRepository;
	
	@Autowired
	CuentaGlobalRepository cuentaGlobalRepository;
		
	@Autowired
	SaldoPrestamosAcumRepository saldoPrestamosAcumRepository;
	
	@Autowired
	MovimientosRepository movimientosRepository;
	
	@Autowired
	TipoMovimientoRepository tipoMovimientoRepository;
	
	@Autowired
	FcodigosRepository fcodigosRepository;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Autowired
	SaldosUltimoMesRepository saldosUltimoMesRepository;
	
	@Autowired
	SaldosHistoriaRepository saldosHistoriaRepository;
	
	@Override
	public void pagoDeCuotas() throws Exception {
		BigDecimal bdCuotaCap = BigDecimal.ZERO;
		BigDecimal bdCuota = BigDecimal.ZERO;
		BigDecimal bdInteres = BigDecimal.ZERO;
		BigDecimal bdSaldo = BigDecimal.ZERO;
		Calendar calendar = Calendar.getInstance();
		Date fecha = (Date) calendar.getTime();
		java.sql.Date fechasql = new java.sql.Date(fecha.getTime()); 
		LocalDate date = LocalDate.now();
		String mesliquidacion = paramService.getMesliquidacion();
		if(chequeoDeEjecucion(mesliquidacion) == (long)-1) {
			throw new Exception("No fue posible determinar si ya se ejecutó el pago de cuotas...");
		}
		else if(chequeoDeEjecucion(mesliquidacion) > (long)0) {
			throw new Exception("Ya se ejecutó el pago de cuotas en el mes : " + mesliquidacion);
		}
		List<Prestamo> lstPrst = prestamoRepository.getNoNuevos();
		for(Prestamo fp : lstPrst) {
			Short cuotasPagas = fp.getCuotasPagas();
			cuotasPagas = ((Integer)(cuotasPagas.intValue() + 1)).shortValue();
				
			CuentaGlobal ctaglobal = new CuentaGlobal();
			
			bdInteres = fp.getSaldoPrestamo().multiply(fp.getInteresPrestamo());
			BigDecimal meses = new BigDecimal("1200");
			
			bdInteres = bdInteres.divide(meses, 2, RoundingMode.HALF_EVEN);
			bdCuotaCap = fp.getCuota().subtract(bdInteres).setScale(2, RoundingMode.HALF_EVEN);
			bdCuota = fp.getCuota();
			if(cuotasPagas.intValue() == fp.getCantCuotas().intValue()){
				bdCuota = fp.getSaldoPrestamo().add(bdInteres);
				bdCuotaCap = fp.getSaldoPrestamo();
			}			
			bdSaldo = fp.getSaldoPrestamo().subtract(bdCuotaCap);
			fp.setCuotasPagas(cuotasPagas);
			fp.setFechaSaldo(date);
			fp.setSaldoPrestamo(bdSaldo);
			
				// Se guarda el interes de cada cuota que se paga en la cuenta Global
				// que luego será parte de la suma que se distribuye en la distribución anual de utilidades
			ctaglobal.setFecha(fechasql);
			ctaglobal.setImporte(bdInteres);
			ctaglobal.setMesliquidacion(mesliquidacion);
			ctaglobal.setNroCuota(cuotasPagas);
			ctaglobal.setNroPrestamo(fp.getNroprestamo());
			
			ctaglobal = cuentaGlobalRepository.save(ctaglobal);
			
				// Si es la ultima cuota del prestamo, se debe pasar al histórico
			if(cuotasPagas.intValue() == fp.getCantCuotas().intValue()){
				// se pasa al histórico
				Prestamohist prstHist = new Prestamohist();
				prstHist.setCantCuotas(fp.getCantCuotas());
				prstHist.setCapitalPrestamo(fp.getCapitalPrestamo());
				prstHist.setCuota(fp.getCuota());
				prstHist.setCuotasPagas(cuotasPagas);
				prstHist.setFechaPrestamo(fp.getFechaPrestamo());
				prstHist.setFechaSaldo(fp.getFechaSaldo());
				prstHist.setGplanta_id(fp.getFuncionario().getIdgplanta());
				prstHist.setInteresPrestamo(fp.getInteresPrestamo());
				prstHist.setNroprestamo(fp.getNroprestamo());
				prstHist.setSaldoPrestamo(fp.getSaldoPrestamo());
				prstHist.setTarjeta(fp.getTarjeta());
				prstHist.setFtipoprestamo_id(fp.getTipoPrestamo().getIdftipoprestamo());
				prstHist.setCodigoPrestamo(fp.getCodigoPrestamo());
				prstHist = prestamohistRepository.save(prstHist);
			}
			/*
			 * Se guarda también el prestamo porque no se puede borrar dentro del loop
			 * después se hace un delete global
			 */
			fp = prestamoRepository.save(fp);
			
			// se registra el movimiento 
			// idTipoMovimientos = 4 es pago de cuotas.
			TipoMovimiento tipoMov = tipoMovimientoRepository.findByCodigoMovimiento(Short.valueOf("4"));
			Movimientos last = movimientosRepository.getLastByFunc(fp.getTarjeta());
			BigDecimal bdSaldoAnt = last.getSaldoActual();
			BigDecimal bdSaldoAct = bdSaldoAnt.add(bdCuotaCap).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			Movimientos newMov = new Movimientos();
			newMov.setFechaMovimiento(fechasql);
			newMov.setTipoMovimiento(tipoMov);
			newMov.setFuncionario(last.getFuncionario());
			newMov.setTarjeta(fp.getTarjeta());
			newMov.setImporteCapSec(bdCuotaCap);
			newMov.setImporteIntFunc(bdInteres);
			newMov.setImporteMov(bdCuota);
			newMov.setTipoMovimiento(tipoMov);
			newMov.setCodigoMovimiento(tipoMov.getCodigoMovimiento());
			newMov.setMesliquidacion(mesliquidacion);
			newMov.setNroCuota(cuotasPagas.intValue());
			newMov.setNroPrestamo(fp.getNroprestamo());
			newMov.setSaldoActual(bdSaldoAct);
			newMov.setSaldoAnterior(bdSaldoAnt);
			newMov.setObservaciones("Pago de Cuotas");
			newMov = movimientosRepository.save(newMov);
			
			
			// Se actualiza el saldo acumulado de prestamos
		SaldoPrestamosAcum fsapresent = new SaldoPrestamosAcum();
		Optional<SaldoPrestamosAcum>fsa = saldoPrestamosAcumRepository.findByTarjeta(fp.getTarjeta());
		if(fsa.isPresent()){
			fsapresent = fsa.get();
			bdSaldo = fsa.get().getSaldoPrestAcumulado();
			bdSaldo = bdSaldo.subtract(bdCuotaCap).setScale(2, BigDecimal.ROUND_HALF_UP);
			fsapresent.setSaldoPrestAcumulado(bdSaldo);
		}
		else {
			throw new Exception("No se encontró el registro con el saldo acumulado de prestamos del func.: " + 
					fp.getFuncionario().getNombre());
		}
		fsapresent.setFecha(fechasql);
		fsapresent = saldoPrestamosAcumRepository.save(fsapresent);
		
			// se actualiza el capital disponible en Fsaldos
		Optional<Saldos> saldos = saldosRepository.findByTarjeta(fp.getTarjeta());
		if(saldos.isPresent()) {
			Saldos saldosPresent = saldos.get();
			BigDecimal dispactual = saldosPresent.getCapitalDispActual();
				// se pasa el cap. disp. actual al anterior antes de modificar
				// el disp. actual con la amortización del prestamo.
			saldosPresent.setCapitalDispAntes(dispactual);			
				// se calcula el nuevo cap. disp. actual restando
				// el saldo acumulado de prestamos al cap. integ.
			BigDecimal capDispAct = saldosPresent.getCapitalIntegActual().subtract(bdSaldo).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			saldosPresent.setCapitalDispActual(capDispAct);
			saldosPresent.setFecha(fechasql);
			
			saldosPresent = saldosRepository.save(saldosPresent);	

			SaldosHistoria saldosh = saldosHistoriaRepository.getUltimoByTarjeta(fp.getTarjeta());				

			SaldosHistoria saldosHnew = new SaldosHistoria();
			saldosHnew.setCapitalDispAntes(saldosh.getCapitalDispActual());
			saldosHnew.setCapitalIntegAntes(saldosh.getCapitalIntegActual());
			saldosHnew.setGplanta_id(saldosh.getGplanta_id());
			saldosHnew.setMesliquidacion(mesliquidacion);
			saldosHnew.setTarjeta(saldosh.getTarjeta());
			saldosHnew.setFecha(fechasql);
			saldosHnew.setNumerales(saldosPresent.getNumerales());
			saldosHnew.setCapitalDispActual(saldosPresent.getCapitalDispActual());
			saldosHnew.setCapitalIntegActual(saldosPresent.getCapitalIntegActual());
			saldosHnew.setMotivo("Pago de cuotas");
			
			saldosHnew = saldosHistoriaRepository.save(saldosHnew);
			
		}
		else {
			throw new Exception("No se encontró el registro de Saldos de: " + fp.getFuncionario().getNombre());
		}
			
		}
		prestamoRepository.deleteAmortizados();
		
		logfondoService.agregarLog("Pago de Cuotas", mesliquidacion + " finalizó exitosamente");
		
	}
	
	private Long chequeoDeEjecucion(String mesliquidacion) {
		try {
			return movimientosRepository.getCountPorAnioMes(mesliquidacion, 4);
		}
		catch(Exception e) {
			return (long) -1;
		}
	}
	
	// Cambio 21/08/2020
	/* en la consolidación de Saldos se trabaja solo sobre las tablas prestamos y saldosAcumulados de prestamos
	 * Se quita la marca de nuevo a los prestamos que la tienen
	 * se genera nuevamente el saldo acumlado.
	 * 
	 */
	@Override
	public void consolidacionDeSaldos(String mesLiquidacion) throws Exception{
		
			// Quita marca de nuevo en los prestamos
		List<Prestamo> lstPrst = prestamoRepository.getNuevos();
		if(!lstPrst.isEmpty()){
			for(Prestamo p : lstPrst){
				p.setPrestamoNuevo(false);
				prestamoRepository.save(p);
			}
		}
		
			//Consolida SaldosPrestamosAcum - Saldos de préstamos acumulados
		
		List<Long> lstIds = prestamoRepository.findIdGplantaDistinct();
		for(Long id : lstIds) {
			Gplanta funcionario = gplantaRepository.getOne(id);
			BigDecimal sumaprst = BigDecimal.ZERO;
			for(Prestamo prst : funcionario.getLstPrsts()) {
				sumaprst = sumaprst.add(prst.getSaldoPrestamo());
			}
			Optional<SaldoPrestamosAcum> saldosacum = saldoPrestamosAcumRepository.findByTarjeta(funcionario.getTarjeta());
			if(saldosacum.isPresent()) {
				SaldoPrestamosAcum sa = saldosacum.get();
				sa.setSaldoPrestAcumulado(sumaprst);
				sa = saldoPrestamosAcumRepository.save(sa);				
			}
		}
		
		logfondoService.agregarLog("Pago de Cuotas", "Finalizó exitosamente la Consolidación de Saldos");

	}

	@Override
	public List<CuotasPagas> resultadoPagoDeCuotas() throws Exception {
		String mesliquidacion = paramService.getMesliquidacion();
		List<CuotasPagas> lstResult = new ArrayList<CuotasPagas>();
		
		List<Movimientos> lstMovs = (List<Movimientos>) movimientosRepository.getByCodigoMovAndMes(Short.valueOf("4"), mesliquidacion);
		for(Movimientos m : lstMovs) {
			Optional<Prestamo> prst = prestamoRepository.findByNroprestamo(m.getNroPrestamo());
			Prestamo prstpresent = prst.isPresent() ? prst.get():null;
			BigDecimal saldoprst = BigDecimal.ZERO;
			if(prstpresent == null) {
				Prestamohist prsth = prestamohistRepository.findByNroprestamo(m.getNroPrestamo());
				saldoprst = prsth.getSaldoPrestamo();
			}
			else {
				saldoprst = prstpresent.getSaldoPrestamo();
			}
			
			CuotasPagas cp = new CuotasPagas();
			cp.setCuotaCap(m.getImporteCapSec());
			cp.setCuotaInteres(m.getImporteIntFunc());
			cp.setCuotaTotal(m.getImporteMov());
			cp.setCuotasPagas(Short.valueOf(m.getNroCuota().toString()));
			cp.setFecha(m.getFechaMovimiento());
			cp.setMesliquidacion(mesliquidacion);
			cp.setNombre(m.getFuncionario().getNombre());
			cp.setNroPrestamo(m.getNroPrestamo());
			cp.setTarjeta(m.getTarjeta());
			cp.setSaldoPrestamo(saldoprst);
			lstResult.add(cp);
		}
		return lstResult;
	}

}
