package org.mercosur.fondoPrevision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.Prestamohist;

@Repository
public interface PrestamohistRepository extends JpaRepository<Prestamohist, Long>, PrestamohistRepositoryCustom {

	Prestamohist findByNroprestamo(Integer nroPrestamo);
}
