package org.mercosur.fondoPrevision.repository;

import java.util.List;

import org.mercosur.fondoPrevision.entities.ParamPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamPrestamoRepository extends JpaRepository<ParamPrestamo, Integer>, ParamPrestamoRepositoryCustom{

	@Query("select pp from ParamPrestamo pp order by pp.meses")
	public List<ParamPrestamo> findAllOrderBymeses();

}
