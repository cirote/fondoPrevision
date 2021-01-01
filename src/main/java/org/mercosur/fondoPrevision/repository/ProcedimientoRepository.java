package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.Procedimiento;

@Repository
public interface ProcedimientoRepository extends JpaRepository<Procedimiento, Integer> {

}
