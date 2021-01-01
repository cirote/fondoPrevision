package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaldosPrestamosSummary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3484510248531024606L;
	private Integer tarjeta;
	private BigDecimal saldoPrestamo;
    
	public SaldosPrestamosSummary(){
		
	}
	
	public SaldosPrestamosSummary(Integer tarjeta, BigDecimal saldoPrestamo){
		this.tarjeta = tarjeta;
		this.saldoPrestamo = saldoPrestamo;
	}
	
	public Integer getTarjeta() {
		return tarjeta;
	}
	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}
	public BigDecimal getSaldoPrestamo() {
		return saldoPrestamo;
	}
	public void setSaldoPrestamo(BigDecimal saldoPrestamo) {
		this.saldoPrestamo = saldoPrestamo;
	}

}
