package org.mercosur.fondoPrevision.repository;

import java.util.Date;
import java.util.List;

import org.mercosur.fondoPrevision.entities.Gplanta;

public interface GplantaRepositoryCustom {

	public Iterable<Gplanta> getFuncsNotInGroup(String tarjetasQL) throws Exception;
	
	public List<Gplanta> getFuncsInGroup(String tarjetasQL) throws Exception;
	
	public void updateUltimosIngresos() throws Exception;
	
	public List<Integer> getTarjetasIngresos(Date fecha) throws Exception;
	
	List<Gplanta> getAllByUnidad(String unidad) throws Exception;
	
	public Integer getLastTarjeta() throws Exception;
	
	public Boolean findTarjeta(Integer tarjeta) throws Exception;
	
	public void deleteByTarjeta(Integer tarjeta) throws Exception;

	
}
