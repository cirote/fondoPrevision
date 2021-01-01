package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ftipoprestamo")
public class TipoPrestamo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3047530538489422865L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="idFTipoPrestamo", nullable = false, unique=true)
	private Integer idftipoprestamo;
	
	@Column
	private Short codigoPrestamo;
	
	@Column
	private String descripcion;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoPrestamo")
	private List<Prestamo> lstPrestamos = new ArrayList<Prestamo>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoPrestamo")
	private List<SolicitudPrestamo> lstSolicitudes = new ArrayList<SolicitudPrestamo>();
	
	public TipoPrestamo() {
		super();
	}


	public Integer getIdftipoprestamo() {
		return idftipoprestamo;
	}


	public void setIdftipoprestamo(Integer idftipoprestamo) {
		this.idftipoprestamo = idftipoprestamo;
	}


	public Short getCodigoPrestamo() {
		return codigoPrestamo;
	}


	public void setCodigoPrestamo(Short codigoPrestamo) {
		this.codigoPrestamo = codigoPrestamo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public List<Prestamo> getLstPrestamos() {
		return lstPrestamos;
	}


	public void setLstPrestamos(List<Prestamo> lstPrestamos) {
		this.lstPrestamos = lstPrestamos;
	}


	public List<SolicitudPrestamo> getLstSolicitudes() {
		return lstSolicitudes;
	}


	public void setLstSolicitudes(List<SolicitudPrestamo> lstSolicitudes) {
		this.lstSolicitudes = lstSolicitudes;
	}
	
	
}
