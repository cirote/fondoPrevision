package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fcuentaglobal")
public class CuentaGlobal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1832583471628104117L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFcuentaglobal", unique = true, nullable = false)
	private Long idfcuentaglobal;
	
	@Column
	private Date fecha;
	
	@Column
	private String mesliquidacion;
	
	@Column 
	private Integer nroPrestamo;
	
	@Column
	private Short nroCuota;
	
	@Column
	private BigDecimal importe;
	
	@Column
	private String observaciones;
	
	
	public CuentaGlobal() {
		super();
	}


	public Long getIdfcuentaglobal() {
		return idfcuentaglobal;
	}


	public void setIdfcuentaglobal(Long idfcuentaglobal) {
		this.idfcuentaglobal = idfcuentaglobal;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getMesliquidacion() {
		return mesliquidacion;
	}


	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}


	public Integer getNroPrestamo() {
		return nroPrestamo;
	}


	public void setNroPrestamo(Integer nroPrestamo) {
		this.nroPrestamo = nroPrestamo;
	}


	public Short getNroCuota() {
		return nroCuota;
	}


	public void setNroCuota(Short nroCuota) {
		this.nroCuota = nroCuota;
	}


	public BigDecimal getImporte() {
		return importe;
	}


	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
