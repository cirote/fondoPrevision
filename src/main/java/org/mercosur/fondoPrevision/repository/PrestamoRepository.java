package org.mercosur.fondoPrevision.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long>, PrestamoRepositoryCustom {

	Optional<Prestamo> findByNroprestamo(Integer nro);
	
	@Query("select fp from Prestamo fp where fp.prestamoNuevo = true")
	public List<Prestamo> getNuevos();

	@Query("select fp from Prestamo fp where fp.prestamoNuevo = false")
	public List<Prestamo> getNoNuevos();

}
