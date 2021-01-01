package org.mercosur.fondoPrevision.repository;

import java.util.Optional;

import org.mercosur.fondoPrevision.entities.SaldoPrestamosAcum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaldoPrestamosAcumRepository extends JpaRepository<SaldoPrestamosAcum, Long>, SaldoPrestamosAcumRepositoryCustom{

	Optional<SaldoPrestamosAcum> findByTarjeta(Integer tarjeta);
	
}
