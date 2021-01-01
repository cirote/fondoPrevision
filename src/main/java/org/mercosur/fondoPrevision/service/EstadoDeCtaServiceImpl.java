package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mercosur.fondoPrevision.dto.AportesSummary;
import org.mercosur.fondoPrevision.dto.EstadoDeCta;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.entities.Parametro;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.SaldoPrestamosAcum;
import org.mercosur.fondoPrevision.entities.Saldos;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.MovimientosRepository;
import org.mercosur.fondoPrevision.repository.ParametroRepository;
import org.mercosur.fondoPrevision.repository.PrestamoRepository;
import org.mercosur.fondoPrevision.repository.SaldoPrestamosAcumRepository;
import org.mercosur.fondoPrevision.repository.SaldosRepository;

@Service
public class EstadoDeCtaServiceImpl implements EstadoDeCtaService{

	@Autowired
	PrestamoRepository prestamoRepository;
	
	@Autowired
	MovimientosRepository movimientosRepository;
	
	@Autowired
	SaldoPrestamosAcumRepository saldosPrstAcumRepository;
	
	@Autowired
	ParametroRepository parametroRepository;
	
	@Autowired
	SaldosRepository saldosRepository;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Override
	public EstadoDeCta getEstadoDeCtabyFuncionario(Long idfuncionario) throws Exception {
		EstadoDeCta estadocta = new EstadoDeCta();
		Parametro param = parametroRepository.getOneByDesc("Pct de Reserva");
		
		estadocta.setPctReserva(param.getValor().divide(new BigDecimal("100")));
		
		BigDecimal dispOperable = BigDecimal.ZERO;
		BigDecimal saldoPrst = BigDecimal.ZERO;
		BigDecimal sumaCuotas = BigDecimal.ZERO;
		BigDecimal saldoDisponible = BigDecimal.ZERO;
		
		Gplanta funcionario = new Gplanta();
		funcionario = gplantaRepository.getOne(idfuncionario);
		estadocta.setFuncionario(funcionario);
		estadocta.setIdfuncionario(funcionario.getIdgplanta());
		
		List<Prestamo> lstPrst = prestamoRepository.findByFuncionario(idfuncionario);
		if(!lstPrst.isEmpty()) {
			estadocta.setLstPrst(lstPrst);
			for(Prestamo p:lstPrst) {
				sumaCuotas = sumaCuotas.add(p.getCuota());
			}
			
			estadocta.setSumaDeCuotas(sumaCuotas);
			
			Optional<SaldoPrestamosAcum> saldoPrstAcum = saldosPrstAcumRepository.findByTarjeta(funcionario.getTarjeta());
			if(saldoPrstAcum.isPresent()) {
				saldoPrst = saldoPrstAcum.get().getSaldoPrestAcumulado();
			}
			else {
				throw new Exception("El funcionario tiene prestamo(s) pero no se encuentra registro de Saldos Acumulados");
			}			
		}
		estadocta.setSaldoPrstAcum(saldoPrst);
		Optional<Saldos> saldosfunc = saldosRepository.findByTarjeta(funcionario.getTarjeta());
		if(saldosfunc.isPresent()) {
			BigDecimal pctReserva = saldosfunc.get().getCapitalIntegActual().multiply(param.getValor()).divide(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
			dispOperable = saldosfunc.get().getCapitalIntegActual().subtract(pctReserva).setScale(2, RoundingMode.HALF_UP);
			saldoDisponible = dispOperable.subtract(saldoPrst);
		}
		else {
			throw new Exception("No se encuentra registro de saldos del funcionario!!");
		}
				
		BigDecimal sueldo = funcionario.getGcargo().getBasico().add(funcionario.getGcargo().getComplemento());
		
		BigDecimal cuarenta = sueldo.multiply(new BigDecimal("40")).divide(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
		
		estadocta.setTotalNominal(sueldo);
		estadocta.setBasico(funcionario.getGcargo().getBasico());
		estadocta.setComplemento(funcionario.getGcargo().getComplemento());
		estadocta.setCapDispOperable(dispOperable);
		estadocta.setCuarentaPorCiento(cuarenta);
		estadocta.setSaldoDisponible(saldoDisponible);
		estadocta.setCapIntegActual(saldosfunc.get().getCapitalIntegActual());
		
			// para agregar los últimos aportes
		List<String> lstMeses = movimientosRepository.getMesesLiquidacion();
		String mesliquidacion = lstMeses.get(lstMeses.size() - 1);
		List<Movimientos> lstMovs = movimientosRepository.getByFuncAndPeriodo(funcionario.getIdgplanta(), mesliquidacion, mesliquidacion);
		List<AportesSummary> lstAportes = new ArrayList<AportesSummary>();
		AportesSummary newline;
		BigDecimal totapfun = BigDecimal.ZERO;
		BigDecimal totapsec = BigDecimal.ZERO;
		BigDecimal totaporte = BigDecimal.ZERO;
		
		for(Movimientos m : lstMovs) {
			newline = new AportesSummary();
			if(m.getCodigoMovimiento() == (short)2) {
				newline.setAporteFun(m.getImporteIntFunc());
				newline.setAporteSec(m.getImporteCapSec());
				newline.setAporteTotal(m.getImporteMov());
				newline.setConcepto("Aporte Mensual");
				totapfun = totapfun.add(newline.getAporteFun());
				totapsec = totapsec.add(newline.getAporteSec());
				totaporte = totaporte.add(newline.getAporteTotal());
				lstAportes.add(newline);
			}
			else if(m.getCodigoMovimiento() == (short) 9) {
				newline = new AportesSummary();
				newline.setAporteFun(m.getImporteIntFunc());
				newline.setAporteSec(m.getImporteCapSec());
				newline.setAporteTotal(m.getImporteMov());
				newline.setConcepto("Aporte s/Aguinaldo");
				totapfun = totapfun.add(newline.getAporteFun());
				totapsec = totapsec.add(newline.getAporteSec());
				totaporte = totaporte.add(newline.getAporteTotal());
				lstAportes.add(newline);
			}
		}
		
		if(lstAportes.size() > 1) {
			newline = new AportesSummary();
			newline.setAporteFun(totapfun);
			newline.setAporteSec(totapsec);
			newline.setAporteTotal(totaporte);
			newline.setConcepto("Totales");
			lstAportes.add(newline);
		}
		estadocta.setLstAportes(lstAportes);
		
		return estadocta;
	}

}
