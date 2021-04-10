package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.SaldosHistoria;

@Repository
public interface SaldosHistoriaRepository extends JpaRepository<SaldosHistoria, Long>, SaldosHistoriaRepositoryCustom {

	@Query("select count(sh) from SaldosHistoria sh where sh.tarjeta =:tarjeta ")
	public Integer getCuantosRegistros(Integer tarjeta);
	
}
