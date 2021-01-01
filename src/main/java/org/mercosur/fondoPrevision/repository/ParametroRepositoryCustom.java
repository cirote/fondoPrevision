package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.Parametro;
import org.mercosur.fondoPrevision.exceptions.ParamNotFoundException;

public interface ParametroRepositoryCustom {
	public String pasajeAhistoricoParametros() throws Exception;
	
	public String actualizaMesLiquidacion(String mesLiq) throws Exception;

	public String findByMesliquidacionDistinct();
	
	public Iterable<Parametro> getSomeByDesc(String descrip) throws Exception;
	
	public Parametro getOneByDesc(String descrip) throws ParamNotFoundException;
}
