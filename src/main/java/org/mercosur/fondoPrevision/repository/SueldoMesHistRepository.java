package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.SueldoMesHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SueldoMesHistRepository extends JpaRepository<SueldoMesHist, Long>, SueldoMesHistRepositoryCustom {

}
