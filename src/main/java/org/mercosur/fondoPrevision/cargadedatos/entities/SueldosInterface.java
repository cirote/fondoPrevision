package org.mercosur.fondoPrevision.cargadedatos.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sueldosinterface")
public class SueldosInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4522376070055085436L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idsueldosinterface")
	private Integer idsueldosinterface;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private String nombre;
	
	@Column
	private BigDecimal sueldo;
	
	@Column
	private BigDecimal complemento;

	
	public SueldosInterface() {
		super();
	}
	
	public Integer getIdsueldosinterface() {
		return idsueldosinterface;
	}

	public void setIdsueldosinterface(Integer idsueldosinterface) {
		this.idsueldosinterface = idsueldosinterface;
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

	public BigDecimal getSueldo() {
		return sueldo;
	}

	public void setSueldo(BigDecimal sueldo) {
		this.sueldo = sueldo;
	}

	public BigDecimal getComplemento() {
		return complemento;
	}

	public void setComplemento(BigDecimal complemento) {
		this.complemento = complemento;
	}
	
	
	
}
