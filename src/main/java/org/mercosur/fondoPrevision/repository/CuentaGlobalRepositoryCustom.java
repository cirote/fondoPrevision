package org.mercosur.fondoPrevision.repository;

import java.math.BigDecimal;
import java.util.List;

import org.mercosur.fondoPrevision.entities.CuentaGlobal;

public interface CuentaGlobalRepositoryCustom {

	BigDecimal sumaMensual(String mesliquidacion) throws Exception;
	
	List<CuentaGlobal> getAllByYear(String anio) throws Exception;
	
	List<String> getAllMesliquidacionByYear(String desde) throws Exception;
	
	List<String> getAllMesliquidacion() throws Exception;
	
	List<CuentaGlobal> getDetalleMesLiquidacion(String mesliquidacion) throws Exception;
	
	BigDecimal sumaAnual(String anio) throws Exception;
	
	BigDecimal sumaPeriodo(String mesinicio, String mesfin) throws Exception;
	
	BigDecimal sumaPorPeriodoyTarjetas(String mesinicio, String mesfin, String tarjetasQL) throws Exception;
}
