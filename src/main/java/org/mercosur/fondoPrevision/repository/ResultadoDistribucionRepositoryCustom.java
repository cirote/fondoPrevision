package org.mercosur.fondoPrevision.repository;

import java.math.BigDecimal;
import java.util.List;

import org.mercosur.fondoPrevision.entities.ResultadoDistribucion;

public interface ResultadoDistribucionRepositoryCustom {

	List<ResultadoDistribucion> getByMesDistrib(String aniomes);
	
	public BigDecimal getTotNumerales(String anioMes);
	
	public BigDecimal getTotIntereses(String anioMes);
	
	public BigDecimal getSumaADistrib(String anioMes) throws Exception;
	
	List<String> getMesesDistribucion();
	
	public void deleteByTarjeta(Integer tarjeta) throws Exception;
}
