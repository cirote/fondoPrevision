package org.mercosur.fondoPrevision.repository;

public interface MovimientosHistRepositoryCustom {

	public void insertAllByTarjeta(Integer tarjeta) throws Exception;
	
	public void insertAllFromMovs() throws Exception;
	
}
