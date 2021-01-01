package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResultadoDistribSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4651769009839757455L;
	private Integer tarjeta;
	private String nombre;
	private BigDecimal capitalIntegAntes;
	private BigDecimal capitalIntegActual;
	private BigDecimal capitalDispAntes;
	private BigDecimal capitalDispActual;
	private BigDecimal totalADistribuir;
	private BigDecimal pctfuncionario;
	private BigDecimal resultadoDistrib;
	
	public ResultadoDistribSummary(){
		
	}
	
	public ResultadoDistribSummary(Integer tarjeta, String nombre, BigDecimal capitalIntegAntes, 
				BigDecimal capitalIntegActual, BigDecimal capitalDispAntes, BigDecimal capitalDispActual, 
				BigDecimal totalADistribuir, BigDecimal pctfuncionario, BigDecimal resultadoDistrib){
		this.tarjeta = tarjeta;
		this.nombre = nombre;
		this.capitalIntegAntes = capitalIntegAntes;
		this.capitalIntegActual = capitalIntegActual;
		this.capitalDispAntes = capitalDispAntes;
		this.capitalDispActual = capitalDispActual;
		this.totalADistribuir = totalADistribuir;
		this.pctfuncionario = pctfuncionario;
		this.resultadoDistrib = resultadoDistrib;
		
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

	public BigDecimal getResultadoDistrib() {
		return resultadoDistrib;
	}

	public void setResultadoDistrib(BigDecimal resultadoDistrib) {
		this.resultadoDistrib = resultadoDistrib;
	}

	public BigDecimal getPctfuncionario() {
		return pctfuncionario;
	}

	public void setPctfuncionario(BigDecimal pctfuncionario) {
		this.pctfuncionario = pctfuncionario;
	}

	public BigDecimal getTotalADistribuir() {
		return totalADistribuir;
	}

	public void setTotalADistribuir(BigDecimal totalADistribuir) {
		this.totalADistribuir = totalADistribuir;
	}
	
}
