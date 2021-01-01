package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.TipoPrestamo;

@Repository
public interface TipoPrestamoRepository extends JpaRepository<TipoPrestamo, Integer> {

	TipoPrestamo getBycodigoPrestamo(Short codigo);
}
