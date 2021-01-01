package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.CategoriaLog;

public interface CategoriaLogRepositoryCustom {

	public Iterable<CategoriaLog> getSomeLikeDesc(String descripcion) throws Exception;
} 
