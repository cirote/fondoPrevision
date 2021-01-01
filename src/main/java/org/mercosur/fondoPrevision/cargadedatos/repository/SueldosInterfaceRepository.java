package org.mercosur.fondoPrevision.cargadedatos.repository;

import org.mercosur.fondoPrevision.cargadedatos.entities.SueldosInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SueldosInterfaceRepository extends JpaRepository<SueldosInterface, Integer> {

}
