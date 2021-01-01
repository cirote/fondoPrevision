package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="fmovimientos")
public class Movimientos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2251875553809917030L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="idFMovimientos", nullable = false, unique=true)
	private Long idfmovimientos;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gplanta_id")
	private Gplanta funcionario;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private Date fechaMovimiento;
			
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ftipomovimiento_id")
	private TipoMovimiento tipoMovimiento;
	
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
	
	public Movimientos() {
		super();
	}

	public Movimientos(Integer tarjeta, BigDecimal importeCapSec, BigDecimal importeIntFunc,
			BigDecimal importeMov) {
		this.tarjeta = tarjeta;
		this.importeCapSec = importeCapSec;
		this.importeIntFunc = importeIntFunc;
		this.importeMov = importeMov;
	}
	
	public Long getIdfmovimientos() {
		return idfmovimientos;
	}

	public void setIdfmovimientos(Long idfmovimientos) {
		this.idfmovimientos = idfmovimientos;
	}


	public Gplanta getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Gplanta funcionario) {
		this.funcionario = funcionario;
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

	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
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
