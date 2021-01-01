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
@Table(name="fparamprst")
public class ParamPrestamo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4979360149385797585L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idfparamprst", unique = true, nullable = false)
	private Integer idfparamprst;
		
	@Column
	private String mesliquidacion;
	
	@Column
	private String descripcion;
	
	@Column
	private Integer meses;
	
	@Column
	private BigDecimal tasa;
	
	
	public ParamPrestamo() {
		super();
	}



	public Integer getIdfparamprst() {
		return idfparamprst;
	}



	public void setIdfparamprst(Integer idfparamprst) {
		this.idfparamprst = idfparamprst;
	}



	public String getMesliquidacion() {
		return mesliquidacion;
	}


	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
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
