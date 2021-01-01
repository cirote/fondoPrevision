package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.Fcodigos;

@Repository
public interface FcodigosRepository extends JpaRepository<Fcodigos, Integer>, FcodigosRepositoryCustom {

	public Fcodigos getByIdfcodigos(Integer id);
	
	public Fcodigos findByCodigo(Short codigo);
	
}
