package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PrestamoSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4738181529640182871L;

	private Long idprestamo;
	private Integer tarjeta;
	private String nombre;
	private Integer nroprestamo;
	private String tipoprestamo;
	private BigDecimal capitalPrst;
	private BigDecimal tasa;
	private BigDecimal cuotaPrst;
	private Short cantCuotas;
	private Short cuotasPagas;
	private BigDecimal saldoPrst;
	private Date fechaSaldo;
	
	public PrestamoSummary() {
		super();
	}

	public PrestamoSummary(Integer tarjeta, String nombre, Integer nroprestamo, String tipoprestamo,
			BigDecimal capitalPrst, BigDecimal cuotaPrst, Short cantCuotas, Short cuotasPagas,
			BigDecimal saldoPrst, Date fechaSaldo) {
		this.tarjeta = tarjeta;
		this.nombre = nombre;
		this.nroprestamo = nroprestamo;
		this.tipoprestamo = tipoprestamo;
		this.capitalPrst = capitalPrst;
		this.cuotaPrst = cuotaPrst;
		this.cantCuotas = cantCuotas;
		this.cuotasPagas = cuotasPagas;
		this.saldoPrst = saldoPrst;
		this.fechaSaldo = fechaSaldo;
	}
	
	public Long getIdprestamo() {
		return idprestamo;
	}

	public void setIdprestamo(Long idprestamo) {
		this.idprestamo = idprestamo;
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

	public Integer getNroprestamo() {
		return nroprestamo;
	}

	public void setNroprestamo(Integer nroprestamo) {
		this.nroprestamo = nroprestamo;
	}

	public String getTipoprestamo() {
		return tipoprestamo;
	}

	public void setTipoprestamo(String tipoprestamo) {
		this.tipoprestamo = tipoprestamo;
	}

	public BigDecimal getCapitalPrst() {
		return capitalPrst;
	}

	public void setCapitalPrst(BigDecimal capitalPrst) {
		this.capitalPrst = capitalPrst;
	}

	public BigDecimal getCuotaPrst() {
		return cuotaPrst;
	}

	public void setCuotaPrst(BigDecimal cuotaPrst) {
		this.cuotaPrst = cuotaPrst;
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

	public BigDecimal getSaldoPrst() {
		return saldoPrst;
	}

	public void setSaldoPrst(BigDecimal saldoPrst) {
		this.saldoPrst = saldoPrst;
	}

	public Date getFechaSaldo() {
		return fechaSaldo;
	}

	public void setFechaSaldo(Date fechaSaldo) {
		this.fechaSaldo = fechaSaldo;
	}

	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}
	
	
}
