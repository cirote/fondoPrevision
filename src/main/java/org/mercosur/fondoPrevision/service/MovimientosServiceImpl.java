package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mercosur.fondoPrevision.dto.AportesSummary;
import org.mercosur.fondoPrevision.dto.CuotasPagas;
import org.mercosur.fondoPrevision.dto.MovimientosForDisplay;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.SueldoMes;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.repository.PrestamoRepository;
import org.mercosur.fondoPrevision.repository.SueldoMesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientosServiceImpl implements MovimientosService {

	@Autowired
	MovimientosRepository movimientosRepository;
	
	@Autowired
	PrestamoRepository prestRepository;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Autowired
	SueldoMesRepository sueldoMesRepository;
	
	@Override
	public List<MovimientosForDisplay> getMovimientosList(Long idfuncionario, String desde, String hasta) throws Exception {
		List<Movimientos> lstMovs = movimientosRepository.getByFuncAndPeriodo(idfuncionario, desde, hasta);
		List<MovimientosForDisplay> lstMfd = new ArrayList<MovimientosForDisplay>();
		MovimientosForDisplay mfd;
		StringBuilder concepto;
		for(Movimientos m : lstMovs) {
			concepto = new StringBuilder();
			concepto.append(m.getTipoMovimiento().getDescripcion());
			switch (m.getTipoMovimiento().getIdftipomovimiento()){
			case 3:{
				concepto.append(" Nro. Prst. " + m.getNroPrestamo().toString());
				concepto.append(" Cuota Nro. " + m.getNroCuota().toString());
				break;
			}
			case 4:{
				concepto.append(" Nro. Prst. " + m.getNroPrestamo().toString());
				concepto.append(" Cuota Nro. " + m.getNroCuota().toString());
				break;
			}
			case 5:{
				concepto.append(" Nro. Prst. " + m.getNroPrestamo().toString());
				break;
			}
			}

			mfd = new MovimientosForDisplay();
			mfd.setFecha(m.getFechaMovimiento());
			mfd.setTarjeta(m.getFuncionario().getTarjeta());
			mfd.setNombre(m.getFuncionario().getNombre());
			mfd.setConcepto(concepto.toString());
			mfd.setImporteCapSec(m.getImporteCapSec());
			mfd.setImporteIntFun(m.getImporteIntFunc());
			mfd.setImporteTotal(m.getImporteMov());
			mfd.setSaldoAnterior(m.getSaldoAnterior());
			mfd.setSaldoActual(m.getSaldoActual());
			
			lstMfd.add(mfd);
		}
		return lstMfd;
	}

	@Override
	public List<CuotasPagas> getCuotasPagasList(String mesliquidacion, String unidad) throws Exception {
		
		Iterable<Movimientos> lstMovs = movimientosRepository.getByCodigoMovAndMes((short) 4, mesliquidacion);
		List<CuotasPagas> lstCuotas = new ArrayList<CuotasPagas>();
		CuotasPagas cuota;
		Optional<Prestamo> prst;
		for(Movimientos m : lstMovs) {
			if(m.getFuncionario().getUnidad().equals(unidad)) {
				cuota = new CuotasPagas();
				prst = prestRepository.findByNroprestamo(m.getNroPrestamo());
				if(prst.isPresent()) {
					cuota.setCantCuotas(prst.get().getCantCuotas());
					cuota.setCuotasPagas(prst.get().getCuotasPagas());
					cuota.setFechaPrestamo(prst.get().getFechaPrestamo());
					cuota.setNroPrestamo(prst.get().getNroprestamo());
					cuota.setNombre(m.getFuncionario().getNombre());
					cuota.setCuotaTotal(m.getImporteMov());
					lstCuotas.add(cuota);
				}				
			}
		}
		return lstCuotas;
	}

	@Override
	public List<AportesSummary> getAportesNomina(String lstcodigos, String mesliquidacion, String unidad) throws Exception {

		List<Movimientos> lstMovs = movimientosRepository.getSumasByCodigosAndMes(lstcodigos, mesliquidacion);
		List<AportesSummary> lstAportes = new ArrayList<AportesSummary>();
		AportesSummary aportes;
		
		for(Movimientos m : lstMovs) {
			Gplanta funcionario = gplantaRepository.findByTarjeta(m.getTarjeta());
			if(funcionario.getUnidad().equals(unidad)) {
				BigDecimal nominales = funcionario.getGcargo().getBasico();
				nominales = nominales.add(funcionario.getGcargo().getComplemento());
				if(lstcodigos.contains("9")) {
					BigDecimal nominalAguinaldo = agregaNominalesAguinaldo(nominales, mesliquidacion, funcionario.getTarjeta());
					nominales = nominales.add(nominalAguinaldo);
				}
				aportes = new AportesSummary();
				aportes.setTarjeta(funcionario.getTarjeta());
				aportes.setNombre(funcionario.getNombre());
				aportes.setTotalNominales(nominales);
				aportes.setAporteFun(m.getImporteIntFunc());
				aportes.setAporteSec(m.getImporteCapSec());
				aportes.setAporteTotal(m.getImporteMov());
				
				lstAportes.add(aportes);
			}
		}
		return lstAportes;
	}

	private BigDecimal agregaNominalesAguinaldo(BigDecimal nominales, String mesliquidacion, Integer tarjeta) {
		BigDecimal total = BigDecimal.ZERO;
		String medio = "06";
		if(mesliquidacion.substring(4).equals(medio)) {
			String mesinicial = mesliquidacion.substring(0, 4) + "01";
			try {
				List<SueldoMes> lstsueldos = sueldoMesRepository.getByPeriodoAndTarjeta(mesinicial, mesliquidacion, tarjeta);
				BigDecimal suma = BigDecimal.ZERO;
				for(SueldoMes sm:lstsueldos) {
					suma = suma.add(sm.getSueldomes());
					suma = suma.add(sm.getComplemento());
				}
				total = suma.divide(new BigDecimal("12"), RoundingMode.HALF_UP);
			}
			catch(Exception e) {
				return null;
			}
		}
		return total;
	}
}
