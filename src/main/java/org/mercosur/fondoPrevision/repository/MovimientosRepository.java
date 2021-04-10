package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.mercosur.fondoPrevision.entities.Movimientos;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long>, MovimientosRepositoryCustom {

	@Query("select mv from Movimientos mv where mv.tarjeta =:tarjeta")
	public List<Movimientos> findAllByTarjeta(Integer tarjeta);
}
