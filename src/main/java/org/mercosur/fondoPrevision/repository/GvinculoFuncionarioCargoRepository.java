package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.GvinculoFuncionarioCargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GvinculoFuncionarioCargoRepository extends JpaRepository<GvinculoFuncionarioCargo, Long>, GvinculoFuncionarioCargoRepositoryCustom {

}
