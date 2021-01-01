package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.util.List;

import org.mercosur.fondoPrevision.dto.CuentaGlobalSummary;
import org.mercosur.fondoPrevision.entities.CuentaGlobal;

public interface CuentaGlobalService {

	public List<CuentaGlobal> getResumenByMes() throws Exception;
	
	public List<CuentaGlobalSummary> getDetalleByOneMes(String mesliquidacion) throws Exception;
	
	public BigDecimal getSumaTotalLastYear(String aniomes) throws Exception;
	
	public BigDecimal getSumaByPeriodo(String mesinicio, String mesfin) throws Exception;
	
	public BigDecimal sumaPorPeriodoyTarjetas(String mesinicio, String mesfin, String tarjetasQL) throws Exception;
}
