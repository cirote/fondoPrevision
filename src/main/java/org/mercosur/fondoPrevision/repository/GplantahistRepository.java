package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.Gplantahist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GplantahistRepository extends JpaRepository<Gplantahist, Long>, GplantahistRepositoryCustom{

	@Query("select gh from Gplantahist gh where gh.tarjeta =:tarjeta")
	public Gplantahist getByTarjeta(Integer tarjeta);
}
