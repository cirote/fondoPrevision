package org.mercosur.fondoPrevision.service;

import java.util.List;

import org.mercosur.fondoPrevision.dto.AportesSummary;
import org.mercosur.fondoPrevision.dto.CuotasPagas;
import org.mercosur.fondoPrevision.dto.MovimientosForDisplay;


public interface MovimientosService {

	public List<MovimientosForDisplay> getMovimientosList(Long idfuncionario, String desde, String hasta) throws Exception;
	
	public List<CuotasPagas> getCuotasPagasList(String mesliquidacion, String unidad) throws Exception;
	
	public List<AportesSummary> getAportesNomina(String lstcodigos, String mesliquidacion, String unidad) throws Exception;
}
