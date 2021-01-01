package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class CuotasPagas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7952401332078325345L;

	private Date fecha;
	
	private Integer tarjeta;
	
	private String nombre;
	
	private Integer nroPrestamo;

	private LocalDate fechaPrestamo;
	
	private BigDecimal cuotaCap;
	
	private BigDecimal cuotaInteres;
	
	private BigDecimal cuotaTotal;
	
	private String mesliquidacion;
	
	private Short cantCuotas;
	
	private Short cuotasPagas;
	
	private BigDecimal saldoPrestamo;
	
	public CuotasPagas() {
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

	public Integer getNroPrestamo() {
		return nroPrestamo;
	}

	public void setNroPrestamo(Integer nroPrestamo) {
		this.nroPrestamo = nroPrestamo;
	}

	public BigDecimal getCuotaCap() {
		return cuotaCap;
	}

	public void setCuotaCap(BigDecimal cuotaCap) {
		this.cuotaCap = cuotaCap;
	}

	public BigDecimal getCuotaInteres() {
		return cuotaInteres;
	}

	public void setCuotaInteres(BigDecimal cuotaInteres) {
		this.cuotaInteres = cuotaInteres;
	}

	public BigDecimal getCuotaTotal() {
		return cuotaTotal;
	}

	public void setCuotaTotal(BigDecimal cuotaTotal) {
		this.cuotaTotal = cuotaTotal;
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}

	public Short getCuotasPagas() {
		return cuotasPagas;
	}

	public void setCuotasPagas(Short cuotasPagas) {
		this.cuotasPagas = cuotasPagas;
	}

	public BigDecimal getSaldoPrestamo() {
		return saldoPrestamo;
	}

	public void setSaldoPrestamo(BigDecimal saldoPrestamo) {
		this.saldoPrestamo = saldoPrestamo;
	}

	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public Short getCantCuotas() {
		return cantCuotas;
	}

	public void setCantCuotas(Short cantCuotas) {
		this.cantCuotas = cantCuotas;
	}
	
	
}
