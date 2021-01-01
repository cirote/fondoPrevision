package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.Movimientos;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long>, MovimientosRepositoryCustom {

}
