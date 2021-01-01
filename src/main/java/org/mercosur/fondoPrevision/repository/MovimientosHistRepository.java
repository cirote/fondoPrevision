package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.MovimientosHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientosHistRepository extends JpaRepository<MovimientosHist, Long>, MovimientosHistRepositoryCustom{

}
