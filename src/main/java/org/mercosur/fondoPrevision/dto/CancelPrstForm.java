package org.mercosur.fondoPrevision.dto;

import java.math.BigDecimal;

public class CancelPrstForm {

	private Long idprestamo;
	
	private Integer nroprestamo;
	
	private Long idfuncionario;
	
	private BigDecimal capitalPrst;
	
	private Integer cantCuotas;
	
	private BigDecimal tasa;
	
	public CancelPrstForm() {
		super();
	}

	public Long getIdfuncionario() {
		return idfuncionario;
	}

	public void setIdfuncionario(Long idfuncionario) {
		this.idfuncionario = idfuncionario;
	}

	public BigDecimal getCapitalPrst() {
		return capitalPrst;
	}

	public void setCapitalPrst(BigDecimal capitalPrst) {
		this.capitalPrst = capitalPrst;
	}

	public Integer getCantCuotas() {
		return cantCuotas;
	}

	public void setCantCuotas(Integer cantCuotas) {
		this.cantCuotas = cantCuotas;
	}

	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	public Long getIdprestamo() {
		return idprestamo;
	}

	public void setIdprestamo(Long idprestamo) {
		this.idprestamo = idprestamo;
	}

	public Integer getNroprestamo() {
		return nroprestamo;
	}

	public void setNroprestamo(Integer nroprestamo) {
		this.nroprestamo = nroprestamo;
	}
	
}
