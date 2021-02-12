package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CapitalesForDisplay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5567228479491453001L;

	private Integer nrofuncionario;
	
	private String nombre;
	
	private String mesliquidacion;
	
	private BigDecimal capitalDispAnterior;
	private BigDecimal importeDistribucion;
	private BigDecimal amortizacion;
	private BigDecimal cancelaciones;
	private BigDecimal prstnuevos;
	private BigDecimal totalMovPrst;
	private BigDecimal totalMovAportes;
	private BigDecimal otros;
	private BigDecimal retiros;
	private BigDecimal capitalDispActual;
	private BigDecimal capitalIntegActual;
	
	public CapitalesForDisplay() {
		super();
	}

	public Integer getNrofuncionario() {
		return nrofuncionario;
	}

	public void setNrofuncionario(Integer nrofuncionario) {
		this.nrofuncionario = nrofuncionario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}

	public BigDecimal getCapitalDispAnterior() {
		return capitalDispAnterior;
	}

	public void setCapitalDispAnterior(BigDecimal capitalDispAnterior) {
		this.capitalDispAnterior = capitalDispAnterior;
	}

	public BigDecimal getAmortizacion() {
		return amortizacion;
	}

	public void setAmortizacion(BigDecimal amortizacion) {
		this.amortizacion = amortizacion;
	}

	public BigDecimal getCancelaciones() {
		return cancelaciones;
	}

	public void setCancelaciones(BigDecimal cancelaciones) {
		this.cancelaciones = cancelaciones;
	}

	public BigDecimal getPrstnuevos() {
		return prstnuevos;
	}

	public void setPrstnuevos(BigDecimal prstnuevos) {
		this.prstnuevos = prstnuevos;
	}

	public BigDecimal getTotalMovPrst() {
		return totalMovPrst;
	}

	public void setTotalMovPrst(BigDecimal totalMovPrst) {
		this.totalMovPrst = totalMovPrst;
	}

	public BigDecimal getTotalMovAportes() {
		return totalMovAportes;
	}

	public void setTotalMovAportes(BigDecimal totalMovAportes) {
		this.totalMovAportes = totalMovAportes;
	}

	public BigDecimal getCapitalDispActual() {
		return capitalDispActual;
	}

	public void setCapitalDispActual(BigDecimal capitalDispActual) {
		this.capitalDispActual = capitalDispActual;
	}

	public BigDecimal getCapitalIntegActual() {
		return capitalIntegActual;
	}

	public void setCapitalIntegActual(BigDecimal capitalIntegActual) {
		this.capitalIntegActual = capitalIntegActual;
	}

	public BigDecimal getImporteDistribucion() {
		return importeDistribucion;
	}

	public void setImporteDistribucion(BigDecimal importeDistribucion) {
		this.importeDistribucion = importeDistribucion;
	}

	public BigDecimal getOtros() {
		return otros;
	}

	public void setOtros(BigDecimal otros) {
		this.otros = otros;
	}

	public BigDecimal getRetiros() {
		return retiros;
	}

	public void setRetiros(BigDecimal retiros) {
		this.retiros = retiros;
	}
}
