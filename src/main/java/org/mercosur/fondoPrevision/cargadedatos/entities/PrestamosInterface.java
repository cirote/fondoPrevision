package org.mercosur.fondoPrevision.cargadedatos.entities;

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
@Table(name="prestamosinterface")
public class PrestamosInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4964935385482562249L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idprestamosinterface")
	private Integer idprestamosinterface;
	
	@Column
	private Integer nroprestamo;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private LocalDate fechaprestamo;
	
	@Column
	private BigDecimal capital;
	
	@Column
	private BigDecimal interes;
	
	@Column
	private Short plazo;
	
	@Column
	private BigDecimal cuota;
	
	@Column
	private Short cuotaspagas;
	
	@Column
	private BigDecimal saldo;
	
	public PrestamosInterface() {
		super();
	}
	public Integer getIdprestamosinterface() {
		return idprestamosinterface;
	}
	public void setIdprestamosinterface(Integer idprestamosinterface) {
		this.idprestamosinterface = idprestamosinterface;
	}
	public Integer getNroprestamo() {
		return nroprestamo;
	}
	public void setNroprestamo(Integer nroprestamo) {
		this.nroprestamo = nroprestamo;
	}
	public Integer getTarjeta() {
		return tarjeta;
	}
	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}
	public LocalDate getFechaprestamo() {
		return fechaprestamo;
	}
	public void setFechaprestamo(LocalDate fechaprestamo) {
		this.fechaprestamo = fechaprestamo;
	}
	public BigDecimal getCapital() {
		return capital;
	}
	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}
	public BigDecimal getInteres() {
		return interes;
	}
	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}
	public Short getPlazo() {
		return plazo;
	}
	public void setPlazo(Short plazo) {
		this.plazo = plazo;
	}
	public BigDecimal getCuota() {
		return cuota;
	}
	public void setCuota(BigDecimal cuota) {
		this.cuota = cuota;
	}
	public Short getCuotaspagas() {
		return cuotaspagas;
	}
	public void setCuotaspagas(Short cuotaspagas) {
		this.cuotaspagas = cuotaspagas;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
}
