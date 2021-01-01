package org.mercosur.fondoPrevision.repository;

import java.util.List;

import org.mercosur.fondoPrevision.entities.Prestamohist;

public interface PrestamohistRepositoryCustom {

	public List<Prestamohist> findPrstCancelados() throws Exception;
	public void insertByTarjeta(Integer tarjeta) throws Exception;
	
}
