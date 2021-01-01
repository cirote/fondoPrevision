package org.mercosur.fondoPrevision.repository;

import java.util.List;

import org.mercosur.fondoPrevision.entities.GvinculoFuncionarioCargo;

public interface GvinculoFuncionarioCargoRepositoryCustom {

	public List<GvinculoFuncionarioCargo> getAllByTarjeta(Integer tarjeta) throws Exception;
	
	public GvinculoFuncionarioCargo getLastByTarjeta(Integer tarjeta) throws Exception;
	
	public void deleteByTarjeta(Integer tarjeta) throws Exception;
}
