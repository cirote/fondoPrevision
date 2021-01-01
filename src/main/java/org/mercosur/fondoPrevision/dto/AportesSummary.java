package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AportesSummary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 831211497193067387L;
	private Integer tarjeta;
	private String nombre;
	private Date fecha;
	private BigDecimal capIntegAntes;
	private BigDecimal capIntegActual;
	private BigDecimal capDispAntes;
	private BigDecimal capDispActual;
	private BigDecimal totalNominales;
	private String concepto;
	private BigDecimal aporteTotal;
	private BigDecimal aporteSec;
	private BigDecimal aporteFun;
	
	public AportesSummary(){
		
	}

	public AportesSummary(Integer tarjeta, String nombre, Date fecha, BigDecimal capIntegAntes, 
						BigDecimal capIntegActual, BigDecimal capDispAntes, BigDecimal capDispActual,
						BigDecimal aporteTotal, BigDecimal aporteSec, BigDecimal aporteFun){
		this.tarjeta = tarjeta;
		this.fecha = fecha;
		this.nombre = nombre;
		this.capDispActual = capDispActual;
		this.capDispAntes = capDispAntes;
		this.capIntegActual = capIntegActual;
		this.capIntegAntes = capIntegAntes;
		this.aporteTotal = aporteTotal;
		this.aporteSec = aporteSec;
		this.aporteFun = aporteFun;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getCapIntegAntes() {
		return capIntegAntes;
	}

	public void setCapIntegAntes(BigDecimal capIntegAntes) {
		this.capIntegAntes = capIntegAntes;
	}

	public BigDecimal getCapIntegActual() {
		return capIntegActual;
	}

	public void setCapIntegActual(BigDecimal capIntegActual) {
		this.capIntegActual = capIntegActual;
	}

	public BigDecimal getCapDispAntes() {
		return capDispAntes;
	}

	public void setCapDispAntes(BigDecimal capDispAntes) {
		this.capDispAntes = capDispAntes;
	}

	public BigDecimal getCapDispActual() {
		return capDispActual;
	}

	public void setCapDispActual(BigDecimal capDispActual) {
		this.capDispActual = capDispActual;
	}

	public BigDecimal getAporteTotal() {
		return aporteTotal;
	}

	public void setAporteTotal(BigDecimal aporteTotal) {
		this.aporteTotal = aporteTotal;
	}

	public BigDecimal getAporteSec() {
		return aporteSec;
	}

	public void setAporteSec(BigDecimal aporteSec) {
		this.aporteSec = aporteSec;
	}

	public BigDecimal getAporteFun() {
		return aporteFun;
	}

	public void setAporteFun(BigDecimal aporteFun) {
		this.aporteFun = aporteFun;
	}

	public BigDecimal getTotalNominales() {
		return totalNominales;
	}

	public void setTotalNominales(BigDecimal totalNominales) {
		this.totalNominales = totalNominales;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
}
