package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.CategoriaLog;

@Repository
public interface CategoriaLogRepository extends JpaRepository<CategoriaLog, Integer>, CategoriaLogRepositoryCustom {

}
