package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.Gplanta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GplantaRepository extends JpaRepository<Gplanta, Long>, GplantaRepositoryCustom{

	public Gplanta findByTarjeta(Integer tarjeta);
	
}
