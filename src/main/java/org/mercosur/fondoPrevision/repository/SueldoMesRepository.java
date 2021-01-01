package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.SueldoMes;

@Repository
public interface SueldoMesRepository extends JpaRepository<SueldoMes, Long>, SueldoMesRepositoryCustom {

	@Query("Select sm from SueldoMes sm where sm.aniomes =:aniomes and sm.tarjeta =:tarjeta")
	public SueldoMes getSueldoMesByAniomesAndTarjeta(@Param("aniomes") String aniomes, @Param("tarjeta") Integer tarjeta);
}
