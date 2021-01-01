package org.mercosur.fondoPrevision.cargadedatos.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="saldosinterface")
public class SaldosInterface implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8708487728032191238L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idsaldosinterface")
	private Integer idsaldosinterface;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private BigDecimal capitalIntegAntes;
	
	@Column
	private BigDecimal capitalIntegActual;
	
	@Column
	private BigDecimal capitalDispAntes;
	
	@Column
	private BigDecimal capitalDispActual;
	
	@Column
	private String mesliquidacion;

	
	public SaldosInterface() {
		super();
	}
	
	public Integer getIdsaldosinterface() {
		return idsaldosinterface;
	}

	public void setIdsaldosinterface(Integer idsaldosinterface) {
		this.idsaldosinterface = idsaldosinterface;
	}

	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}

	public BigDecimal getCapitalIntegAntes() {
		return capitalIntegAntes;
	}

	public void setCapitalIntegAntes(BigDecimal capitalIntegAntes) {
		this.capitalIntegAntes = capitalIntegAntes;
	}

	public BigDecimal getCapitalIntegActual() {
		return capitalIntegActual;
	}

	public void setCapitalIntegActual(BigDecimal capitalIntegActual) {
		this.capitalIntegActual = capitalIntegActual;
	}

	public BigDecimal getCapitalDispAntes() {
		return capitalDispAntes;
	}

	public void setCapitalDispAntes(BigDecimal capitalDispAntes) {
		this.capitalDispAntes = capitalDispAntes;
	}

	public BigDecimal getCapitalDispActual() {
		return capitalDispActual;
	}

	public void setCapitalDispActual(BigDecimal capitalDispActual) {
		this.capitalDispActual = capitalDispActual;
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}
}
