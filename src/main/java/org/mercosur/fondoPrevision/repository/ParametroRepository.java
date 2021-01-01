package org.mercosur.fondoPrevision.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.Parametro;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Long>, ParametroRepositoryCustom{

	public Optional<Parametro> findByMesliquidacionAndDescripcion(String mesliquidacion, String descripcion);
}
