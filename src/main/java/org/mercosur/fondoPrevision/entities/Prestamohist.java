package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fprestamoshist")
public class Prestamohist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7787524658473133303L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="idFPrestamosHist", nullable = false, unique=true)
	private Long idfprestamoshist;
	
	@Column
	private Integer nroprestamo;
	
	@Column
	private Long gplanta_id;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private LocalDate fechaPrestamo;
	
	@Column
	private BigDecimal capitalPrestamo;
	
	@Column
	private Integer ftipoprestamo_id;
	
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
	
	public Prestamohist() {
		super();
	}

	public Long getIdfprestamoshist() {
		return idfprestamoshist;
	}

	public void setIdfprestamoshist(Long idfprestamoshist) {
		this.idfprestamoshist = idfprestamoshist;
	}

	public Integer getNroprestamo() {
		return nroprestamo;
	}

	public void setNroprestamo(Integer nroprestamo) {
		this.nroprestamo = nroprestamo;
	}

	public Long getGplanta_id() {
		return gplanta_id;
	}

	public void setGplanta_id(Long gplanta_id) {
		this.gplanta_id = gplanta_id;
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

	public Integer getFtipoprestamo_id() {
		return ftipoprestamo_id;
	}

	public void setFtipoprestamo_id(Integer ftipoprestamo_id) {
		this.ftipoprestamo_id = ftipoprestamo_id;
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
