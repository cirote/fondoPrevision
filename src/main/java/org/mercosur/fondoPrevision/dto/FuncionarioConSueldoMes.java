package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FuncionarioConSueldoMes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -436758619193019939L;

	private Long idfuncionario;
	private Integer tarjeta;
	private String nombre;
	private BigDecimal basico;
	private BigDecimal complemento;
	private Date ingreso;
	private String descripCargo;
	
	public FuncionarioConSueldoMes() {
		
	}

	public FuncionarioConSueldoMes(Long id, Integer tarjeta, String nombre, BigDecimal basico,
			BigDecimal complemento, Date ingreso, String descripcargo) {
		this.idfuncionario = id;
		this.tarjeta = tarjeta;
		this.nombre = nombre;
		this.basico = basico;
		this.complemento = complemento;
		this.ingreso = ingreso;
		this.descripCargo = descripcargo;
	}
	
	public Long getIdfuncionario() {
		return idfuncionario;
	}

	public void setIdfuncionario(Long idfuncionario) {
		this.idfuncionario = idfuncionario;
	}

	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getBasico() {
		return basico;
	}

	public void setBasico(BigDecimal basico) {
		this.basico = basico;
	}

	public BigDecimal getComplemento() {
		return complemento;
	}

	public void setComplemento(BigDecimal complemento) {
		this.complemento = complemento;
	}

	public Date getIngreso() {
		return ingreso;
	}

	public void setIngreso(Date ingreso) {
		this.ingreso = ingreso;
	}

	public String getDescripCargo() {
		return descripCargo;
	}

	public void setDescripCargo(String descripCargo) {
		this.descripCargo = descripCargo;
	}
	
}
