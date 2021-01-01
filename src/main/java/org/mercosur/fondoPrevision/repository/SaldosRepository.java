package org.mercosur.fondoPrevision.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.Saldos;

@Repository
public interface SaldosRepository extends JpaRepository<Saldos, Long>, SaldosRepositoryCustom {

	Optional<Saldos> findByTarjeta(Integer tarjeta);
}
