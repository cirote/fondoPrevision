package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.Prestamohist;
import org.mercosur.fondoPrevision.entities.SaldoPrestamosAcum;
import org.mercosur.fondoPrevision.entities.Saldos;
import org.mercosur.fondoPrevision.entities.SaldosHistoria;
import org.mercosur.fondoPrevision.entities.TipoMovimiento;
import org.mercosur.fondoPrevision.entities.TipoPrestamo;
import org.mercosur.fondoPrevision.exceptions.FuncionarioNoEncontradoException;
import org.mercosur.fondoPrevision.exceptions.PrestamoNotFoundException;
import org.mercosur.fondoPrevision.repository.FcodigosRepository;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.repository.PrestamoRepository;
import org.mercosur.fondoPrevision.repository.PrestamohistRepository;
import org.mercosur.fondoPrevision.repository.SaldoPrestamosAcumRepository;
import org.mercosur.fondoPrevision.repository.SaldosHistoriaRepository;
import org.mercosur.fondoPrevision.repository.SaldosRepository;
import org.mercosur.fondoPrevision.repository.TipoMovimientoRepository;
import org.mercosur.fondoPrevision.repository.TipoPrestamoRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService {

	@Autowired
	ParamService parametroService;
	
	@Autowired
	PrestamoRepository prestamoRepository;
	
	@Autowired
	TipoPrestamoRepository tipoPrestamoRepository;
	
	@Autowired
	GplantaService gplantaService;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Autowired
	SaldosRepository saldosRepository;
	
	@Autowired
	SaldosHistoriaRepository saldosHistoriaRepository;
	
	@Autowired
	MovimientosRepository movimientosRepository;
	
	@Autowired
	TipoMovimientoRepository tipomovimientoRepository;
	
	@Autowired
	PrestamohistRepository prestamoshistRepository;
	
	@Autowired
	SaldoPrestamosAcumRepository saldoPrestacumRepository;
	
	@Autowired
	FcodigosRepository fcodigosRepository;
	
	@Autowired
	LogfondoService logfondoService;
	
	@Override
	public List<Prestamo> getAllPrst() {
		return prestamoRepository.findAll();
	}

	@Override
	public List<TipoPrestamo> getAllTipoPrst() {
		return tipoPrestamoRepository.findAll();
	}
	
	@Override
	public List<Prestamo> getAllPrstByFunc(Long idfuncionario) {
		List<Prestamo> lstPrsts = prestamoRepository.findByFuncionario(idfuncionario);
		return lstPrsts;
	}

	@Override
	public Optional<Prestamo> findByNroprestamo(Integer nro) {
		Optional<Prestamo> prst = prestamoRepository.findByNroprestamo(nro);
		return prst;
	}

	@Override
	public Prestamo savePrst(Prestamo prst, Long idfunc, Integer idTipo) throws Exception {
		Optional<Gplanta> func = gplantaRepository.findById(idfunc);
		Optional<Integer> nroprst = prestamoRepository.getUltimoNroPrst();
		Optional<TipoPrestamo> tipoPrst = tipoPrestamoRepository.findById(idTipo);
		String mesliquidacion = parametroService.getMesliquidacion();

		//java.sql.Date fecha = new java.sql.Date(Calendar.getInstance().getTime().getTime());		

		// Modificación Dic. 2020
		// A solicitud de Adm. del fondo, si se ingresa un prst. después del cierre del mes pero 
		// antes de que dicho mes termine se debe registrar con ese mesliquidación.
		// para eso se compara el mes de la fecha en que se ingresa el prst. con el mes del mesliquidación
		// si este último es distinto se modifica la variable mesliquidacion.
	
		Integer mesdehoy = prst.getFechaPrestamo().getMonthValue();
		Integer aniodehoy = prst.getFechaPrestamo().getYear();
		Integer mesdeParam = Integer.valueOf(mesliquidacion.substring(4));
		Integer aniodeParam = Integer.valueOf(mesliquidacion.substring(0, 4));
		if(aniodehoy == aniodeParam && mesdehoy != mesdeParam) {
			mesliquidacion = aniodeParam.toString().concat(mesdehoy < 10? "0" + mesdehoy.toString() : mesdehoy.toString());
		}
		else if(aniodehoy != aniodeParam) {
			mesliquidacion = aniodehoy.toString().concat(mesdehoy < 10 ? "0" + mesdehoy.toString() : mesdehoy.toString());
		}
		
		LocalDate fecha = prst.getFechaPrestamo();
			// 1- Salva el préstamo.
		if(nroprst.isPresent()) {
			prst.setNroprestamo(nroprst.get() + 1);
		}
		else {prst.setNroprestamo(1);
		}
		if(tipoPrst.isPresent()) {
			prst.setTipoPrestamo(tipoPrst.get());
			prst.setCodigoPrestamo(tipoPrst.get().getCodigoPrestamo());
		}
		else {
			throw new Exception("No se encontró el Tipo del Prestamo");
		}
		if(func.isPresent()) {
			prst.setCuotasPagas(Short.valueOf("0"));
			prst.setFechaSaldo(fecha);
			prst.setSaldoPrestamo(prst.getCapitalPrestamo());
			prst.setFuncionario(func.get());
			prst.setTarjeta(func.get().getTarjeta());

			prst = prestamoRepository.save(prst);
			
			java.sql.Date fechasql = java.sql.Date.valueOf(fecha);
			
				// 2 - Actualiza Saldos acumulados de prestamos por funcionario.
			Optional<SaldoPrestamosAcum> saldoAcum = saldoPrestacumRepository.findByTarjeta(func.get().getTarjeta());
			SaldoPrestamosAcum saldoac = new SaldoPrestamosAcum();
			if(saldoAcum.isPresent()) {
				saldoac = saldoAcum.get();
				saldoac.setFecha(fechasql);
				saldoac.setSaldoPrestAcumulado(saldoac.getSaldoPrestAcumulado().add(prst.getCapitalPrestamo()));
			}
			else {
				saldoac.setFecha(fechasql);
				saldoac.setFuncionario(func.get());
				saldoac.setSaldoPrestAcumulado(prst.getCapitalPrestamo());
				saldoac.setTarjeta(func.get().getTarjeta());
			}
			saldoPrestacumRepository.save(saldoac);
						
				// 3 - Actualiza los Capitales disponible en Saldos y se genera registro nuevo en SaldosHistoria
			Optional<Saldos>saldosf = saldosRepository.findByTarjeta(func.get().getTarjeta());
			if(saldosf.isPresent()) {
				Saldos saldosactuales = saldosf.get();
				BigDecimal dispactual = saldosactuales.getCapitalDispActual();
				saldosactuales.setFecha(fechasql);
				saldosactuales.setCapitalDispAntes(dispactual);
				saldosactuales.setCapitalDispActual(saldosactuales.getCapitalDispActual().subtract(prst.getCapitalPrestamo()));
				saldosactuales.setMesliquidacion(mesliquidacion);
				saldosactuales = saldosRepository.save(saldosactuales);

					// 3.1 - Se pasan los saldos al historico
				SaldosHistoria saldosh = saldosHistoriaRepository.getUltimoByTarjeta(func.get().getTarjeta());				
		
				SaldosHistoria saldosHnew = new SaldosHistoria();
				saldosHnew.setCapitalDispAntes(saldosh.getCapitalDispActual());
				saldosHnew.setCapitalIntegAntes(saldosh.getCapitalIntegActual());
				saldosHnew.setGplanta_id(saldosh.getGplanta_id());
				saldosHnew.setMesliquidacion(mesliquidacion);
				saldosHnew.setTarjeta(saldosh.getTarjeta());
				saldosHnew.setFecha(fechasql);
				saldosHnew.setNumerales(saldosactuales.getNumerales());
				saldosHnew.setCapitalDispActual(saldosactuales.getCapitalDispActual());
				saldosHnew.setCapitalIntegActual(saldosactuales.getCapitalIntegActual());
				saldosHnew.setMotivo("Préstamo Nuevo");
				
				saldosHnew = saldosHistoriaRepository.save(saldosHnew);
			
			}
			else {
				throw new Exception("Saldos del funcionario no encontrados!");
			}


				// 4 - Registra el movimiento actualizando saldos
				//codigoMovimiento = 3 --> Solicitud de Prestamo
			TipoMovimiento tipoMov = tipomovimientoRepository.findByCodigoMovimiento(Short.valueOf("3"));
			

			BigDecimal saldoant = BigDecimal.ZERO;
			BigDecimal saldoact = BigDecimal.ZERO;
			
			try {
				Movimientos mov = movimientosRepository.getLastByFunc(func.get().getTarjeta());				
				saldoant = mov.getSaldoActual();
				saldoact = mov.getSaldoActual().subtract(prst.getCapitalPrestamo());
			}
			catch(Exception e) {
				throw new Exception(e);
			}
				
			Movimientos newmov = new Movimientos();
			newmov.setFechaMovimiento(fechasql);
			newmov.setCodigoMovimiento(tipoMov.getCodigoMovimiento());
			newmov.setTipoMovimiento(tipoMov);
			newmov.setFuncionario(func.get());
			newmov.setTarjeta(func.get().getTarjeta());
			newmov.setImporteCapSec(BigDecimal.ZERO);
			newmov.setImporteIntFunc(BigDecimal.ZERO);
			newmov.setImporteMov(prst.getCapitalPrestamo());
			newmov.setMesliquidacion(mesliquidacion);
			newmov.setNroCuota(0);
			newmov.setNroPrestamo(prst.getNroprestamo());
			newmov.setObservaciones("Prestamo nuevo");
			newmov.setSaldoActual(saldoact);
			newmov.setSaldoAnterior(saldoant);
			newmov = movimientosRepository.save(newmov);
							
			logfondoService.agregarLog("Solicitud Prestamo", "Se ingresó exitosamente. Prst nro: " + prst.getNroprestamo().toString());
		}
		else {
			throw new FuncionarioNoEncontradoException();
		}
		
		return prst;
	}

	@Override
	public Prestamo getPrstById(Long id) throws PrestamoNotFoundException {
		
		return prestamoRepository.getOne(id);
	}

	@Override
	public Integer getProxNroPrst() {
		Integer nrosig = 1;
		Optional<Integer> nro = prestamoRepository.getUltimoNroPrst();
		if(nro.isPresent()) {
			nrosig = nro.get() + 1;
		}
		
		return nrosig;
	}

	@Override
	public Prestamo getLastByFuncionario(Long idfuncionario) throws NoResultException{
		Prestamo prst = prestamoRepository.getUltimoByFuncionario(idfuncionario);
		return prst;
	}

	@Override
	public void deletePrst(Long idprst) throws Exception {
		Prestamo prst = prestamoRepository.getOne(idprst);
		Gplanta func = prst.getFuncionario();
		Optional<Saldos> saldosfunc = saldosRepository.findByTarjeta(func.getTarjeta());
		Optional<SaldoPrestamosAcum> saldosAcum = saldoPrestacumRepository.findByTarjeta(func.getTarjeta());
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		java.sql.Date fecha = new java.sql.Date(currentDate.getTime());
		
			//1 - Suma el saldo del prestamo al Cap. Disponible en la tabla Saldos.
		
		if(saldosfunc.isPresent()) {
			saldosfunc.get().setCapitalDispActual(saldosfunc.get().getCapitalDispActual().add(prst.getSaldoPrestamo()));
			saldosfunc.get().setFecha(fecha);
			saldosRepository.save(saldosfunc.get());
		}
		else {
			throw new Exception("Registro de Saldos del funcionario, no encontrado!.");
		}
			// 2 - Se resta el saldo del prest. del Acumulado.
		if(saldosAcum.isPresent()) {
			saldosAcum.get().setSaldoPrestAcumulado(saldosAcum.get().getSaldoPrestAcumulado().subtract(prst.getSaldoPrestamo()));
			saldosAcum.get().setFecha(fecha);
			SaldoPrestamosAcum sacum = saldosAcum.get();
			saldoPrestacumRepository.save(sacum);
		}
		else {
			throw new Exception("No se encontró el registro que contiene el saldo acumulado de prestamos!");
		}
		
			// 3 - Se elimina el movimiento. Solicitud de Prestamo es tipo 3
		TipoMovimiento tipomov = tipomovimientoRepository.findByCodigoMovimiento(Short.valueOf("3"));
		Movimientos mov = movimientosRepository.getLastByFuncAndTipo(func.getTarjeta(), tipomov.getIdftipomovimiento());
		
		movimientosRepository.delete(mov);
		prestamoRepository.delete(prst);
		
		logfondoService.agregarLog("Eliminación de Préstamo", "Prestamo nro.: " + prst.getNroprestamo().toString());
	}

	@Override
	public void cancelPrst(Long idprst) throws Exception {
		Prestamo prst = prestamoRepository.getOne(idprst);
		Gplanta func = prst.getFuncionario();
		Optional<Saldos> saldosfunc = saldosRepository.findByTarjeta(func.getTarjeta());
		Optional<SaldoPrestamosAcum> saldosAcum = saldoPrestacumRepository.findByTarjeta(func.getTarjeta());
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		java.sql.Date fecha = new java.sql.Date(currentDate.getTime());
		
			//1 - Suma el saldo del prestamo al Cap. Disponible en la tabla Saldos.
		
		if(saldosfunc.isPresent()) {
			saldosfunc.get().setCapitalDispActual(saldosfunc.get().getCapitalDispActual().add(prst.getSaldoPrestamo()));
			saldosfunc.get().setFecha(fecha);
			saldosRepository.save(saldosfunc.get());
		}
		else {
			throw new Exception("Registro de Saldos del funcionario, no encontrado!.");
		}
			// 2 - Se resta el saldo del prest. del Acumulado.
		if(saldosAcum.isPresent()) {
			saldosAcum.get().setSaldoPrestAcumulado(saldosAcum.get().getSaldoPrestAcumulado().subtract(prst.getSaldoPrestamo()));
			saldosAcum.get().setFecha(fecha);
		}
		else {
			throw new Exception("No se encontró el registro que contiene el saldo acumulado de prestamos!");
		}
		
			// 3 - Se registra el movimiento. Cancelación es tipo 5
		TipoMovimiento tipomov = tipomovimientoRepository.findByCodigoMovimiento(Short.valueOf("5"));
		Movimientos mov = movimientosRepository.getLastByFunc(func.getTarjeta());

		Movimientos newmov = new Movimientos();
		newmov.setFechaMovimiento(fecha);
		newmov.setTipoMovimiento(tipomov);
		newmov.setCodigoMovimiento(tipomov.getCodigoMovimiento());
		newmov.setFuncionario(func);
		newmov.setTarjeta(func.getTarjeta());
		newmov.setNroPrestamo(prst.getNroprestamo());
		newmov.setNroCuota(null);
		newmov.setImporteMov(prst.getSaldoPrestamo());
		newmov.setImporteCapSec(BigDecimal.ZERO);
		newmov.setImporteIntFunc(BigDecimal.ZERO);
		newmov.setSaldoAnterior(mov.getSaldoActual());
		newmov.setSaldoActual(mov.getSaldoActual().add(prst.getSaldoPrestamo()));
		newmov.setMesliquidacion(parametroService.getMesliquidacion());
		newmov.setObservaciones("Cancelación de Préstamo");
		movimientosRepository.save(newmov);

		// 4 - Pasa el prestamo a la historia.
		
		Prestamohist prsthist = new Prestamohist();
		prsthist.setCantCuotas(prst.getCantCuotas());
		prsthist.setCapitalPrestamo(prst.getCapitalPrestamo());
		prsthist.setCodigoPrestamo(prst.getCodigoPrestamo());
		prsthist.setCuota(prst.getCuota());
		prsthist.setCuotasPagas(prst.getCuotasPagas());
		prsthist.setFechaPrestamo(prst.getFechaPrestamo());
		prsthist.setFechaSaldo(prst.getFechaSaldo());
		prsthist.setFtipoprestamo_id(prst.getTipoPrestamo().getIdftipoprestamo());
		prsthist.setGplanta_id(func.getIdgplanta());
		prsthist.setInteresPrestamo(prst.getInteresPrestamo());
		prsthist.setNroprestamo(prst.getNroprestamo());
		prsthist.setSaldoPrestamo(prst.getSaldoPrestamo());
		prsthist.setTarjeta(prst.getTarjeta());
		prsthist = prestamoshistRepository.save(prsthist);
		
			// 5 - Elimina el prestamo de la tabla Prestamos.
		
		prestamoRepository.delete(prst);
		
		logfondoService.agregarLog("Cancelación de Préstamo", "Prestamo nro.: " + prst.getNroprestamo().toString());

	}

	@Override
	public List<Prestamo> getPrestCancelados() throws Exception {
		
		List<Prestamohist> lstPrstHist = prestamoshistRepository.findPrstCancelados();
		List<Prestamo> lstPrst = new ArrayList<Prestamo>();
		for(Prestamohist ph : lstPrstHist) {
			Prestamo p = new Prestamo();
			p.setCantCuotas(ph.getCantCuotas());
			p.setCapitalPrestamo(ph.getCapitalPrestamo());
			p.setCodigoPrestamo(ph.getCodigoPrestamo());
			p.setCuota(ph.getCuota());
			p.setCuotasPagas(ph.getCuotasPagas());
			p.setFechaPrestamo(ph.getFechaPrestamo());
			p.setFechaSaldo(ph.getFechaSaldo());
			p.setFuncionario(gplantaRepository.getOne(ph.getGplanta_id()));
			p.setInteresPrestamo(ph.getInteresPrestamo());
			p.setNroprestamo(ph.getNroprestamo());
			p.setSaldoPrestamo(ph.getSaldoPrestamo());
			p.setTarjeta(ph.getTarjeta());
			p.setTipoPrestamo(tipoPrestamoRepository.getOne(ph.getFtipoprestamo_id()));
			lstPrst.add(p);
		}
		return lstPrst;
	}

	@Override
	public Prestamo updatePrst(Prestamo prst, Long idfunc, Integer idTipo) throws Exception {
		Prestamo existPrst = prestamoRepository.getOne(prst.getIdfprestamos());
		TipoPrestamo tipo = tipoPrestamoRepository.getOne(idTipo);
		if(!existPrst.getTipoPrestamo().getCodigoPrestamo().equals(tipo.getCodigoPrestamo())) {
			existPrst.setTipoPrestamo(tipo);
			existPrst.setCodigoPrestamo(tipo.getCodigoPrestamo());
		}
		existPrst.setCantCuotas(prst.getCantCuotas());
		existPrst.setCapitalPrestamo(prst.getCapitalPrestamo());
		existPrst.setCuota(prst.getCuota());
		existPrst.setCuotasPagas(prst.getCuotasPagas());
		existPrst.setInteresPrestamo(prst.getInteresPrestamo());
		existPrst.setSaldoPrestamo(prst.getSaldoPrestamo());
		existPrst = prestamoRepository.save(existPrst);
		return existPrst;
	}

	@Override
	public Prestamo savePrstFromCancel(Prestamo prst, Long idfunc, Integer idTipo) throws Exception {
		TipoPrestamo tipo = tipoPrestamoRepository.getOne(idTipo);
		prst.setTipoPrestamo(tipo);
		prst = prestamoRepository.save(prst);
		return prst;
	}


}
