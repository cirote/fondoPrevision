package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.TipoMovimiento;

@Repository
public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, Integer> {
	
	public TipoMovimiento getByIdftipomovimiento(Integer id);
	public TipoMovimiento findByCodigoMovimiento(Short codigo);
}
