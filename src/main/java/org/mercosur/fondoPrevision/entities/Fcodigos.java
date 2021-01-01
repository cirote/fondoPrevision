package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fcodigos")
public class Fcodigos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8769192781865637306L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFCodigos", unique = true, nullable = false)
	private Short idfcodigos;
	
	@Column
	private Short codigo;
	
	@Column
	private String descripcion;
	
	@Column
	private Boolean adicion;
	
	@Column
	private Boolean descuento;
	
	@Column
	private Integer collistliquidos;
	
	@Column
	private Boolean habilitado;
	
	@Column
	private String observaciones;
	
	public Fcodigos() {
		super();
	}

	public Short getIdfcodigos() {
		return idfcodigos;
	}

	public void setIdfcodigos(Short idfcodigos) {
		this.idfcodigos = idfcodigos;
	}

	public Short getCodigo() {
		return codigo;
	}

	public void setCodigo(Short codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getAdicion() {
		return adicion;
	}

	public void setAdicion(Boolean adicion) {
		this.adicion = adicion;
	}

	public Boolean getDescuento() {
		return descuento;
	}

	public void setDescuento(Boolean descuento) {
		this.descuento = descuento;
	}

	public Integer getCollistliquidos() {
		return collistliquidos;
	}

	public void setCollistliquidos(Integer collistliquidos) {
		this.collistliquidos = collistliquidos;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
