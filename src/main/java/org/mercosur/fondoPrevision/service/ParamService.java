package org.mercosur.fondoPrevision.service;

import org.mercosur.fondoPrevision.entities.ParamPrestamo;
import org.mercosur.fondoPrevision.entities.Parametro;
import org.mercosur.fondoPrevision.exceptions.ParamNotFoundException;

public interface ParamService {

	public Iterable<Parametro> getAllParametros();
	
	public Iterable<ParamPrestamo> getAllParamPrestamo();
	
	public Parametro getParametroById(Long id) throws ParamNotFoundException;
	
	public ParamPrestamo getParamPrestamoById(Integer id) throws ParamNotFoundException;
	
	public Parametro getParametroByMesliquidacionAndDescripcion(String mesliquidacion, String descripcion) throws ParamNotFoundException;
	
	public Parametro getParametroByDescripcion(String desc) throws ParamNotFoundException;
	
	public Parametro createParametro(Parametro formParam) throws Exception;
	
	public ParamPrestamo createParamPrestamo(ParamPrestamo param) throws Exception;

	public Parametro updateParametro(Parametro param) throws Exception;
	
	public ParamPrestamo updateParamPrestamo(ParamPrestamo param) throws Exception;

	public void deleteParametro(Long id) throws ParamNotFoundException, Exception;
	
	public void deleteParamPrestamo(Integer id) throws ParamNotFoundException, Exception;

	public String getMesliquidacion();
	
	public String updateMesLiquidacion(String mesLiq) throws Exception;
	
}
