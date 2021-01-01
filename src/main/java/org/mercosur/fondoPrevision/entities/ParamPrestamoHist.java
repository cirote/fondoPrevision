package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fparamprsthist")
public class ParamPrestamoHist implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5011525771686092592L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private Integer idfparamprsthist;
	
	@Column
	private String mesliquidacion;
	
	@Column
	private String descripcion;
	
	@Column
	private Integer meses;
	
	@Column
	private BigDecimal tasa;
	
	public ParamPrestamoHist() {
		super();
	}


	public Integer getIdfparamprsthist() {
		return idfparamprsthist;
	}


	public void setIdfparamprsthist(Integer idfparamprsthist) {
		this.idfparamprsthist = idfparamprsthist;
	}


	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}


	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getMeses() {
		return meses;
	}

	public void setMeses(Integer meses) {
		this.meses = meses;
	}

	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}
}
