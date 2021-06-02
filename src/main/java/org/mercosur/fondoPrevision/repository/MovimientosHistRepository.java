package org.mercosur.fondoPrevision.repository;

import java.util.List;

import org.mercosur.fondoPrevision.entities.MovimientosHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientosHistRepository extends JpaRepository<MovimientosHist, Long>, MovimientosHistRepositoryCustom{

	@Query("Select mh.tarjeta from MovimientosHist mh where mh.codigoMovimiento = 7 and mh.mesliquidacion =:mesliquidacion")
	public List<Integer> getTarjetasEgresos(String mesliquidacion);
}
