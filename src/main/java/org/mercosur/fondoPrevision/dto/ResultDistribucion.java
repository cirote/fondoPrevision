package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResultDistribucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1250177228623922686L;

	private String anioMesDistrib;
	
	private BigDecimal totalNumeralesAnt;
	
	private BigDecimal totalNumeralesAct;
	
	private BigDecimal sumaADistribuir;
	
	public ResultDistribucion() {
		super();
	}

	public String getAnioMesDistrib() {
		return anioMesDistrib;
	}

	public void setAnioMesDistrib(String anioMesDistrib) {
		this.anioMesDistrib = anioMesDistrib;
	}

	public BigDecimal getTotalNumeralesAnt() {
		return totalNumeralesAnt;
	}

	public void setTotalNumeralesAnt(BigDecimal totalNumeralesAnt) {
		this.totalNumeralesAnt = totalNumeralesAnt;
	}

	public BigDecimal getTotalNumeralesAct() {
		return totalNumeralesAct;
	}

	public void setTotalNumeralesAct(BigDecimal totalNumeralesAct) {
		this.totalNumeralesAct = totalNumeralesAct;
	}

	public BigDecimal getSumaADistribuir() {
		return sumaADistribuir;
	}

	public void setSumaADistribuir(BigDecimal sumaADistribuir) {
		this.sumaADistribuir = sumaADistribuir;
	}
}
