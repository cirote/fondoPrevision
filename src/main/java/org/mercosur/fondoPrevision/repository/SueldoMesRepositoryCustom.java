package org.mercosur.fondoPrevision.repository;

import java.util.List;

import org.mercosur.fondoPrevision.entities.SueldoMes;

public interface SueldoMesRepositoryCustom {

	List<SueldoMes> getAllByMesliquidacion(String mesliquidacion) throws Exception;
	
	List<SueldoMes> getByPeriodoAndTarjeta(String aniomes1, String aniomes2, Integer tarjeta) throws Exception;
	
	public void deleteByTarjeta(Integer tarjeta) throws Exception;
}
