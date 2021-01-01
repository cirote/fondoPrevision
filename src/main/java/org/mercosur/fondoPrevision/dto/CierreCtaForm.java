package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class CierreCtaForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6346856913356780870L;

	private Long idfuncionario;
	
	private String fechaEgreso;
	
	private BigDecimal liquidacionfinal;
	
	private BigDecimal resultEjercicio;
	
	private BigDecimal licenciaCompleta;
	
	private BigDecimal licenciaSinComp;
	
	public CierreCtaForm() {
		super();
	}


	public BigDecimal getLiquidacionfinal() {
		return liquidacionfinal;
	}

	public void setLiquidacionfinal(BigDecimal liquidacionfinal) {
		this.liquidacionfinal = liquidacionfinal;
	}

	public BigDecimal getResultEjercicio() {
		return resultEjercicio;
	}

	public void setResultEjercicio(BigDecimal resultEjercicio) {
		this.resultEjercicio = resultEjercicio;
	}

	public Long getIdfuncionario() {
		return idfuncionario;
	}

	public void setIdfuncionario(Long idfuncionario) {
		this.idfuncionario = idfuncionario;
	}


	public String getFechaEgreso() {
		return fechaEgreso;
	}


	public void setFechaEgreso(String fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}


	public BigDecimal getLicenciaCompleta() {
		return licenciaCompleta;
	}


	public void setLicenciaCompleta(BigDecimal licenciaCompleta) {
		this.licenciaCompleta = licenciaCompleta;
	}


	public BigDecimal getLicenciaSinComp() {
		return licenciaSinComp;
	}


	public void setLicenciaSinComp(BigDecimal licenciaSinComp) {
		this.licenciaSinComp = licenciaSinComp;
	}

}
