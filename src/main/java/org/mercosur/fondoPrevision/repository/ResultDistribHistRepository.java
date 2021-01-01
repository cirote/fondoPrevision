package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.ResultDistribHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultDistribHistRepository extends JpaRepository<ResultDistribHist, Long>, ResultDistribHistRepositoryCustom {

}
