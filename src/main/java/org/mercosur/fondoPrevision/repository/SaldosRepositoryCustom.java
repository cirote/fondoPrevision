package org.mercosur.fondoPrevision.repository;

import java.util.List;

import org.mercosur.fondoPrevision.entities.Saldos;

public interface SaldosRepositoryCustom {

	List<Saldos> getSaldosConsolidables() throws Exception;
	List<Saldos> getByMesLiquidacion(String mesliq) throws Exception;
	String getMesLiquidacion();
	public void deleteByTarjeta(Integer tarjeta) throws Exception;
}
