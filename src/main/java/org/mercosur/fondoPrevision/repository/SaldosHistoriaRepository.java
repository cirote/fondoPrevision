package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.SaldosHistoria;

@Repository
public interface SaldosHistoriaRepository extends JpaRepository<SaldosHistoria, Long>, SaldosHistoriaRepositoryCustom {

}
