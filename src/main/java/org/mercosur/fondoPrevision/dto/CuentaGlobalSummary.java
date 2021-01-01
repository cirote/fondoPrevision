package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CuentaGlobalSummary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3499253769672977643L;

	private Date fecha;
	
	private String mesliquidacion;
	
	private Integer nroPrestamo;
	
	private String titular;
	
	private Short nroCuota;
	
	private BigDecimal importe;

	
	public CuentaGlobalSummary() {
		super();
	}
	
	public CuentaGlobalSummary(Date fecha, String mesliquidacion, Integer nroPrestamo, String titular, 
			Short nroCuota, BigDecimal importe) {
		this.fecha = fecha;
		this.mesliquidacion = mesliquidacion;
		this.nroPrestamo = nroPrestamo;
		this.titular = titular;
		this.nroCuota = nroCuota;
		this.importe = importe;
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

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
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
	
	
}
