package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name="fprestamos")
public class Prestamo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8969537049590009125L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="idFPrestamos", nullable = false, unique=true)
	private Long idfprestamos;
	
	@Column
	private Integer nroprestamo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gplanta_id")
	private Gplanta funcionario;

	@Column
	private Integer tarjeta;
	
	@Column
	private LocalDate fechaPrestamo;
	
	@Column
	private BigDecimal capitalPrestamo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ftipoPrestamo_id")
	private TipoPrestamo tipoPrestamo;
	
	@Column
	private Short codigoPrestamo;
	
	@Column
	private BigDecimal interesPrestamo;
	
	@Column
	private BigDecimal cuota;
	
	@Column
	private Short cantCuotas;
	
	@Column
	private Short cuotasPagas;
	
	@Column
	private BigDecimal saldoPrestamo;
	
	@Column
	private LocalDate fechaSaldo;
	
	@Column
	private Boolean prestamoNuevo;
	
	public Prestamo() {
		super();
	}

	public Prestamo(BigDecimal capital, BigDecimal interes, Short cantCuotas, BigDecimal cuota) {
		this.capitalPrestamo = capital;
		this.interesPrestamo = interes;
		this.cantCuotas = cantCuotas;
		this.cuota = cuota;
	}
	
	public Long getIdfprestamos() {
		return idfprestamos;
	}

	public void setIdfprestamos(Long idfprestamos) {
		this.idfprestamos = idfprestamos;
	}

	public Integer getNroprestamo() {
		return nroprestamo;
	}

	public void setNroprestamo(Integer nroprestamo) {
		this.nroprestamo = nroprestamo;
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

	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public BigDecimal getCapitalPrestamo() {
		return capitalPrestamo;
	}

	public void setCapitalPrestamo(BigDecimal capitalPrestamo) {
		this.capitalPrestamo = capitalPrestamo;
	}

	public TipoPrestamo getTipoPrestamo() {
		return tipoPrestamo;
	}

	public void setTipoPrestamo(TipoPrestamo tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	public Short getCodigoPrestamo() {
		return codigoPrestamo;
	}

	public void setCodigoPrestamo(Short codigoPrestamo) {
		this.codigoPrestamo = codigoPrestamo;
	}

	public BigDecimal getInteresPrestamo() {
		return interesPrestamo;
	}

	public void setInteresPrestamo(BigDecimal interesPrestamo) {
		this.interesPrestamo = interesPrestamo;
	}

	public BigDecimal getCuota() {
		return cuota;
	}

	public void setCuota(BigDecimal cuota) {
		this.cuota = cuota;
	}

	public Short getCantCuotas() {
		return cantCuotas;
	}

	public void setCantCuotas(Short cantCuotas) {
		this.cantCuotas = cantCuotas;
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

	public LocalDate getFechaSaldo() {
		return fechaSaldo;
	}

	public void setFechaSaldo(LocalDate fechaSaldo) {
		this.fechaSaldo = fechaSaldo;
	}

	public Boolean getPrestamoNuevo() {
		return prestamoNuevo;
	}

	public void setPrestamoNuevo(Boolean prestamoNuevo) {
		this.prestamoNuevo = prestamoNuevo;
	}
	
	
}
