package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.ResultadoDistribucion;

@Repository
public interface ResultadoDistribucionRepository extends JpaRepository<ResultadoDistribucion, Long>, ResultadoDistribucionRepositoryCustom {

	@Query("select rd from ResultadoDistribucion rd where rd.tarjeta =:tarjeta and rd.mesLiquidacion =:mesLiquidacion")
	public ResultadoDistribucion getByTarjetaAndMesliquidacion(Integer tarjeta, String mesLiquidacion);
}
