package org.mercosur.fondoPrevision.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import org.mercosur.fondoPrevision.entities.DatosDistribucion;

public class DistribForm {

	
	private DatosDistribucion datosDistrib;
	
	@NotBlank(message="Se debe ingresar el mes/anio del comienzo del período")
	private String mesInicial;
	
	@NotBlank(message="Se debe ingresar el mes/anio del fin del periodo")
	private String mesFinal;
	
	private BigDecimal saldoCtaGlobal;
	
	private BigDecimal interesColocaciones;
	
	private BigDecimal totalAdistrib;
	
	public DistribForm() {
		super();
	}

	public DatosDistribucion getDatosDistrib() {
		return datosDistrib;
	}

	public void setDatosDistrib(DatosDistribucion datosDistrib) {
		this.datosDistrib = datosDistrib;
	}

	public String getMesInicial() {
		return mesInicial;
	}

	public void setMesInicial(String mesInicial) {
		this.mesInicial = mesInicial;
	}

	public String getMesFinal() {
		return mesFinal;
	}

	public void setMesFinal(String mesFinal) {
		this.mesFinal = mesFinal;
	}

	public BigDecimal getSaldoCtaGlobal() {
		return saldoCtaGlobal;
	}

	public void setSaldoCtaGlobal(BigDecimal saldoCtaGlobal) {
		this.saldoCtaGlobal = saldoCtaGlobal;
	}

	public BigDecimal getInteresColocaciones() {
		return interesColocaciones;
	}

	public void setInteresColocaciones(BigDecimal interesColocaciones) {
		this.interesColocaciones = interesColocaciones;
	}

	public BigDecimal getTotalAdistrib() {
		return totalAdistrib;
	}

	public void setTotalAdistrib(BigDecimal totalAdistrib) {
		this.totalAdistrib = totalAdistrib;
	}
}
