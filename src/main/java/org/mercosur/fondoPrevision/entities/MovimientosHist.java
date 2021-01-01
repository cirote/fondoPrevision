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
@Table(name="fmovimientoshist")
public class MovimientosHist implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5672318584543318603L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFMovimientosHist")
	private Long idmovimientoshist;
	
	@Column
	private Long idGPlanta;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private Date fechaMovimiento;

	@Column
	private Integer idFTipoMovimiento;
	
	@Column
	private Short codigoMovimiento;
	
	@Column
	private Integer nroPrestamo;
	
	@Column
	private Integer nroCuota;
	
	@Column
	private BigDecimal importeMov;
	
	@Column
	private BigDecimal importeCapSec;
	
	@Column
	private BigDecimal importeIntFunc;
	
	@Column
	private BigDecimal saldoAnterior;
	
	@Column
	private BigDecimal saldoActual;
	
	@Column
	private String mesliquidacion;
	
	@Column
	private String observaciones;

	public MovimientosHist() {
		super();
	}

	public Long getIdmovimientoshist() {
		return idmovimientoshist;
	}

	public void setIdmovimientoshist(Long idmovimientoshist) {
		this.idmovimientoshist = idmovimientoshist;
	}


	public Long getIdGPlanta() {
		return idGPlanta;
	}

	public void setIdGPlanta(Long idGPlanta) {
		this.idGPlanta = idGPlanta;
	}

	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public Integer getIdFTipoMovimiento() {
		return idFTipoMovimiento;
	}

	public void setIdFTipoMovimiento(Integer idFTipoMovimiento) {
		this.idFTipoMovimiento = idFTipoMovimiento;
	}

	public Short getCodigoMovimiento() {
		return codigoMovimiento;
	}

	public void setCodigoMovimiento(Short codigoMovimiento) {
		this.codigoMovimiento = codigoMovimiento;
	}

	public Integer getNroPrestamo() {
		return nroPrestamo;
	}

	public void setNroPrestamo(Integer nroPrestamo) {
		this.nroPrestamo = nroPrestamo;
	}

	public Integer getNroCuota() {
		return nroCuota;
	}

	public void setNroCuota(Integer nroCuota) {
		this.nroCuota = nroCuota;
	}

	public BigDecimal getImporteMov() {
		return importeMov;
	}

	public void setImporteMov(BigDecimal importeMov) {
		this.importeMov = importeMov;
	}

	public BigDecimal getImporteCapSec() {
		return importeCapSec;
	}

	public void setImporteCapSec(BigDecimal importeCapSec) {
		this.importeCapSec = importeCapSec;
	}

	public BigDecimal getImporteIntFunc() {
		return importeIntFunc;
	}

	public void setImporteIntFunc(BigDecimal importeIntFunc) {
		this.importeIntFunc = importeIntFunc;
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

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	

}
