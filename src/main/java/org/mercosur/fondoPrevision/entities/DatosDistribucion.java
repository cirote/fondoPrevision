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
@Table(name="fdatosdistribintereses")
public class DatosDistribucion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4830307759450158747L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idfdatosdistribintereses")
	private Long idfdatosdistribintereses;
	
	@Column
	private String mesDistrib;
	
	@Column
	private BigDecimal ResultadoADistrib;
	
	@Column
	private BigDecimal TotBaseDistrib;
		
	public DatosDistribucion() {
		super();
	}


	public Long getIdfdatosdistribintereses() {
		return idfdatosdistribintereses;
	}


	public void setIdfdatosdistribintereses(Long idfdatosdistribintereses) {
		this.idfdatosdistribintereses = idfdatosdistribintereses;
	}


	public String getMesDistrib() {
		return mesDistrib;
	}


	public void setMesDistrib(String mesDistrib) {
		this.mesDistrib = mesDistrib;
	}


	public BigDecimal getResultadoADistrib() {
		return ResultadoADistrib;
	}


	public void setResultadoADistrib(BigDecimal resultadoADistrib) {
		ResultadoADistrib = resultadoADistrib;
	}


	public BigDecimal getTotBaseDistrib() {
		return TotBaseDistrib;
	}


	public void setTotBaseDistrib(BigDecimal totBaseDistrib) {
		TotBaseDistrib = totBaseDistrib;
	}


}
