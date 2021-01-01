package org.mercosur.fondoPrevision.service;

import java.util.List;

import org.mercosur.fondoPrevision.dto.CuotasPagas;

public interface PagoDeCuotasService {

	void pagoDeCuotas() throws Exception;
	void consolidacionDeSaldos(String mesLiquidacion) throws Exception;
	List<CuotasPagas> resultadoPagoDeCuotas() throws Exception;
}
