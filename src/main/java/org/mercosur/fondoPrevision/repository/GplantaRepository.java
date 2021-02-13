package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityNotFoundException;

import org.mercosur.fondoPrevision.entities.Gplanta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GplantaRepository extends JpaRepository<Gplanta, Long>, GplantaRepositoryCustom{

	public Gplanta findByTarjeta(Integer tarjeta) throws EntityNotFoundException;
	
}
