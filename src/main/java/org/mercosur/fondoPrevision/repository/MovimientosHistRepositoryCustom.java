package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.MovimientosHist;

public interface MovimientosHistRepositoryCustom {

	public void insertAllByTarjeta(Integer tarjeta) throws Exception;
	
	public void insertAllFromMovs() throws Exception;

	public MovimientosHist getLastByFuncAndTipo(Integer tarjeta, Integer idtipo) throws Exception;
	
}
