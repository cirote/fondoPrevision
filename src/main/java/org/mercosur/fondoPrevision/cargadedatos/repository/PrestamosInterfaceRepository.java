package org.mercosur.fondoPrevision.cargadedatos.repository;

import org.mercosur.fondoPrevision.cargadedatos.entities.PrestamosInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamosInterfaceRepository extends JpaRepository<PrestamosInterface, Integer> {

}
