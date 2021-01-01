package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.Fcodigos;

public interface FcodigosRepositoryCustom {

	public Iterable<Fcodigos> getCodInList(String list);
}
