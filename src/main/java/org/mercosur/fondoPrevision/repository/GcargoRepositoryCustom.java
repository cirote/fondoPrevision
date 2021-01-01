package org.mercosur.fondoPrevision.repository;

import java.util.List;

import org.mercosur.fondoPrevision.entities.Gcargo;

public interface GcargoRepositoryCustom {

	List<Gcargo> getAllByUnidad(String unidad) throws Exception;
	
}
