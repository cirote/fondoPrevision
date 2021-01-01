package org.mercosur.fondoPrevision.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.Ayuda;

@Repository
public interface AyudaRepository extends CrudRepository<Ayuda, Long> {

	public Optional<Ayuda> findByClave(String clave);
}
