package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.mercosur.fondoPrevision.entities.CuentaGlobal;

public class CuentaGlobalForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6668393557723152157L;

	private List<CuentaGlobal> lstResumen;
	
	private List<CuentaGlobalSummary> lstDetalle;
	
	private String mesLiquidacion;

	private BigDecimal sumaTotal;
	
	public CuentaGlobalForm() {
		super();
	}
	
	public CuentaGlobalForm(String mesliquidacion, List<CuentaGlobal> lstResumen, BigDecimal sumaTotal) {
		this.mesLiquidacion = mesliquidacion;
		this.lstResumen = lstResumen;
		this.sumaTotal = sumaTotal;
	}

	public List<CuentaGlobal> getLstResumen() {
		return lstResumen;
	}

	public void setLstResumen(List<CuentaGlobal> lstResumen) {
		this.lstResumen = lstResumen;
	}

	public List<CuentaGlobalSummary> getLstDetalle() {
		return lstDetalle;
	}

	public void setLstDetalle(List<CuentaGlobalSummary> lstDetalle) {
		this.lstDetalle = lstDetalle;
	}

	public String getMesLiquidacion() {
		return mesLiquidacion;
	}

	public void setMesLiquidacion(String mesLiquidacion) {
		this.mesLiquidacion = mesLiquidacion;
	}

	public BigDecimal getSumaTotal() {
		return sumaTotal;
	}

	public void setSumaTotal(BigDecimal sumaTotal) {
		this.sumaTotal = sumaTotal;
	}

}
