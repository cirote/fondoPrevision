package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.ParamPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamPrestamoRepository extends JpaRepository<ParamPrestamo, Integer>, ParamPrestamoRepositoryCustom{


}
