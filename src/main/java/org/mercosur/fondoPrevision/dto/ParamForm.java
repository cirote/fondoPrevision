package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import org.mercosur.fondoPrevision.entities.ParamPrestamo;
import org.mercosur.fondoPrevision.entities.Parametro;

public class ParamForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8053563398734262307L;

	private Parametro parametro;
	
	private Long idParametro;
	
	private ParamPrestamo parPrestamo;
		
	private Integer idParamPrst;
	
	public ParamForm() {
		super();
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public ParamPrestamo getParPrestamo() {
		return parPrestamo;
	}

	public void setParPrestamo(ParamPrestamo parPrestamo) {
		this.parPrestamo = parPrestamo;
	}

	public Integer getIdParamPrst() {
		return idParamPrst;
	}

	public void setIdParamPrst(Integer idParamPrst) {
		this.idParamPrst = idParamPrst;
	}

	public Long getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Long idParametro) {
		this.idParametro = idParametro;
	}

}
