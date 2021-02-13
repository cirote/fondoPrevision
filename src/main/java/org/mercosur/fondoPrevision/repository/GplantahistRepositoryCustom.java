package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityNotFoundException;

import org.mercosur.fondoPrevision.entities.Gplantahist;

public interface GplantahistRepositoryCustom {

	public void insertByTarjeta(Integer tarjeta) throws Exception;
}
