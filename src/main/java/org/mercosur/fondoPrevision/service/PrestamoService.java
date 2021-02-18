package org.mercosur.fondoPrevision.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.TipoPrestamo;
import org.mercosur.fondoPrevision.exceptions.ParamNotFoundException;
import org.mercosur.fondoPrevision.exceptions.PrestamoNotFoundException;

public interface PrestamoService {

	List<Prestamo> getAllPrst();
	
	Prestamo getPrstById(Long id) throws PrestamoNotFoundException;
	
	List<TipoPrestamo> getAllTipoPrst();
	
	List<Prestamo> getAllPrstByFunc(Long idfuncionario);
	
	Integer getProxNroPrst() throws ParamNotFoundException;
	
	Optional<Prestamo> findByNroprestamo(Integer nro);
	
	Prestamo getLastByFuncionario(Long idfuncionario) throws NoResultException;
	
	Prestamo savePrst(Prestamo prst, Long idfunc, Integer idTipo) throws Exception;
	
	Prestamo updatePrst(Prestamo prst, Long idfunc, Integer idTipo) throws Exception;
	
	Prestamo savePrstFromCancel(Prestamo prst, Long idfunc, Integer idTipo) throws Exception;
	void deletePrst(Long idprst) throws Exception;
	
	void cancelPrst(Long idprst) throws Exception;
	
	List<Prestamo> getPrestCancelados() throws Exception;
	
}
