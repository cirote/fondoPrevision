package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.ResultadoDistribucion;

@Repository
public interface ResultadoDistribucionRepository extends JpaRepository<ResultadoDistribucion, Long>, ResultadoDistribucionRepositoryCustom {

}
