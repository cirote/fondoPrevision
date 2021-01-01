package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.mercosur.fondoPrevision.dto.CuentaGlobalSummary;
import org.mercosur.fondoPrevision.entities.CuentaGlobal;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.Prestamohist;
import org.mercosur.fondoPrevision.repository.CuentaGlobalRepository;
import org.mercosur.fondoPrevision.repository.DatosDistribucionRepository;
import org.mercosur.fondoPrevision.repository.GplantaRepository;
import org.mercosur.fondoPrevision.repository.PrestamoRepository;
import org.mercosur.fondoPrevision.repository.PrestamohistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaGlobalServiceImpl implements CuentaGlobalService{

	@Autowired
	CuentaGlobalRepository cuentaGlobalRepository;
	
	@Autowired
	DatosDistribucionRepository datosDistribucionRepository;
	
	@Autowired
	ParamService paramService;
	
	@Autowired
	PrestamoRepository prestamoRepository;
	
	@Autowired
	PrestamohistRepository prestamoHistRepository;
	
	@Autowired
	GplantaRepository gplantaRepository;
	
	@Override
	public List<CuentaGlobal> getResumenByMes() throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		List<CuentaGlobal> lstResumen = new ArrayList<CuentaGlobal>();
		CuentaGlobal lineaResumen;
		String primeraniomes = datosDistribucionRepository.getUltimoMesDistrib();
		List<String> lstMeses;
		if(primeraniomes == null) {
			lstMeses = cuentaGlobalRepository.getAllMesliquidacion();
		}
		else {
			lstMeses = cuentaGlobalRepository.getAllMesliquidacionByYear(primeraniomes);			
		}
		for(String am : lstMeses) {
			BigDecimal sumaMensual = cuentaGlobalRepository.sumaMensual(am);
			lineaResumen = new CuentaGlobal();
			lineaResumen.setFecha(currentDate);
			lineaResumen.setMesliquidacion(am);
			lineaResumen.setNroCuota(Short.valueOf("0"));
			lineaResumen.setNroPrestamo(0);
			lineaResumen.setImporte(sumaMensual);
			lstResumen.add(lineaResumen);	
		}
		
		return lstResumen;
	}

	@Override
	public List<CuentaGlobalSummary> getDetalleByOneMes(String mesliquidacion) throws Exception {
		List<CuentaGlobal> lstDetalles = cuentaGlobalRepository.findAllByMesliquidacion(mesliquidacion);
		String titular = "";
		List<CuentaGlobalSummary> lstResult = new ArrayList<CuentaGlobalSummary>();
		for(CuentaGlobal cg : lstDetalles) {
			Optional<Prestamo> prst = prestamoRepository.findByNroprestamo(cg.getNroPrestamo());
			if(!prst.isPresent()) {
				Prestamohist prsthist = (Prestamohist) prestamoHistRepository.findByNroprestamo(cg.getNroPrestamo());
				Gplanta f = gplantaRepository.getOne(prsthist.getGplanta_id());
				titular = f.getNombre();
			}
			else {
				titular = prst.get().getFuncionario().getNombre();
			}
			CuentaGlobalSummary newline = new CuentaGlobalSummary(cg.getFecha(), cg.getMesliquidacion(), cg.getNroPrestamo(),
					titular, cg.getNroCuota(), cg.getImporte());
			lstResult.add(newline);
		}
		return lstResult;
	}

	@Override
	public BigDecimal getSumaTotalLastYear(String aniomes) throws Exception {
		String mesinicio = datosDistribucionRepository.getUltimoMesDistrib();
		if(mesinicio == null) {
			if(Integer.valueOf(aniomes.substring(4)) >= 5) {
				mesinicio = aniomes.substring(0, 4) + "05";
			}
			else {
				Integer ianio = Integer.valueOf(aniomes.substring(0, 4)) + 1;
				String anio = ianio.toString();
				mesinicio = anio + "05";
			}
			return cuentaGlobalRepository.sumaPeriodo(mesinicio, aniomes);
		}
		return cuentaGlobalRepository.sumaPeriodo(mesinicio, aniomes);
	}

	@Override
	public BigDecimal getSumaByPeriodo(String mesinicio, String mesfin) throws Exception {
		
		return cuentaGlobalRepository.sumaPeriodo(mesinicio, mesfin);
	}

	@Override
	public BigDecimal sumaPorPeriodoyTarjetas(String mesinicio, String mesfin, String tarjetasQL) throws Exception {
		return cuentaGlobalRepository.sumaPorPeriodoyTarjetas(mesinicio, mesfin, tarjetasQL);
	}

}
