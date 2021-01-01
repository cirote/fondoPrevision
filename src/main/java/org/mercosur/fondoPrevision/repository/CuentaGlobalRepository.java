package org.mercosur.fondoPrevision.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.CuentaGlobal;

@Repository
public interface CuentaGlobalRepository extends JpaRepository<CuentaGlobal, Long>, CuentaGlobalRepositoryCustom{

	List<CuentaGlobal> findAllByMesliquidacion(String mesliquidacion);
}
