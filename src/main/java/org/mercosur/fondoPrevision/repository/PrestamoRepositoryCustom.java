package org.mercosur.fondoPrevision.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.mercosur.fondoPrevision.entities.Prestamo;

public interface PrestamoRepositoryCustom {

	public Optional<Integer> getUltimoNroPrst();
	
	List<Prestamo> findByFuncionario(Long idfuncionario);
	
	Prestamo getUltimoByFuncionario(Long idfuncionario) throws NoResultException;
	
	public void deleteAmortizados() throws Exception;
	
	public void deleteByTarjeta(Integer tarjeta) throws Exception;
	
	List<Long> findIdGplantaDistinct() throws Exception;
}
