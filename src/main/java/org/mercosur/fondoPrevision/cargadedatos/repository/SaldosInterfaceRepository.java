package org.mercosur.fondoPrevision.cargadedatos.repository;

import org.mercosur.fondoPrevision.cargadedatos.entities.SaldosInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaldosInterfaceRepository extends JpaRepository<SaldosInterface, Integer> {

}
