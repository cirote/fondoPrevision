package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MovimientosForDisplay implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7002549667039174968L;

	private Date fecha;
	private Integer tarjeta;
	private String nombre;
	private String concepto;
	private BigDecimal importeCapSec;
	private BigDecimal importeIntFun;
	private BigDecimal importeTotal;
	private BigDecimal saldoAnterior;
	private BigDecimal saldoActual;
	
	public MovimientosForDisplay() {
		super();
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public BigDecimal getImporteCapSec() {
		return importeCapSec;
	}

	public void setImporteCapSec(BigDecimal importeCapSec) {
		this.importeCapSec = importeCapSec;
	}

	public BigDecimal getImporteIntFun() {
		return importeIntFun;
	}

	public void setImporteIntFun(BigDecimal importeIntFun) {
		this.importeIntFun = importeIntFun;
	}

	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public BigDecimal getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public BigDecimal getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(BigDecimal saldoActual) {
		this.saldoActual = saldoActual;
	}
}
