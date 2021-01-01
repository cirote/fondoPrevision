package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="fresultadosdistribucion")
public class ResultadoDistribucion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6424946463037745822L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="idFResultadosDistribucion")
	private Long idfresultadoDistribucion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gplanta_id")
	private Gplanta funcionario;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private Date fecha;
	
	@Column
	private String mesInicial;
	
	@Column
	private String mesFinal;
	
	@Column
	private String mesLiquidacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fcodigos_id")
	private Fcodigos fcodigos;
	
	@Column
	private BigDecimal totalADistribuir;
	
	@Column
	private BigDecimal numeralesFuncionario;
	
	@Column
	private BigDecimal numeralesTodos;
	
	@Column
	private BigDecimal pctFuncionario;
	
	@Column
	private BigDecimal distribucionFuncionario;
	
	
	public ResultadoDistribucion() {
		super();
	}


	public Long getIdfresultadoDistribucion() {
		return idfresultadoDistribucion;
	}


	public void setIdfresultadoDistribucion(Long idfresultadoDistribucion) {
		this.idfresultadoDistribucion = idfresultadoDistribucion;
	}


	public Gplanta getFuncionario() {
		return funcionario;
	}


	public void setFuncionario(Gplanta funcionario) {
		this.funcionario = funcionario;
	}


	public Integer getTarjeta() {
		return tarjeta;
	}


	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getMesInicial() {
		return mesInicial;
	}


	public void setMesInicial(String mesInicial) {
		this.mesInicial = mesInicial;
	}


	public String getMesFinal() {
		return mesFinal;
	}


	public void setMesFinal(String mesFinal) {
		this.mesFinal = mesFinal;
	}


	public String getMesLiquidacion() {
		return mesLiquidacion;
	}


	public void setMesLiquidacion(String mesLiquidacion) {
		this.mesLiquidacion = mesLiquidacion;
	}


	public Fcodigos getFcodigos() {
		return fcodigos;
	}


	public void setFcodigos(Fcodigos fcodigos) {
		this.fcodigos = fcodigos;
	}


	public BigDecimal getTotalADistribuir() {
		return totalADistribuir;
	}


	public void setTotalADistribuir(BigDecimal totalADistrib) {
		this.totalADistribuir = totalADistrib;
	}


	public BigDecimal getNumeralesFuncionario() {
		return numeralesFuncionario;
	}


	public void setNumeralesFuncionario(BigDecimal numeralesFuncionario) {
		this.numeralesFuncionario = numeralesFuncionario;
	}


	public BigDecimal getNumeralesTodos() {
		return numeralesTodos;
	}


	public void setNumeralesTodos(BigDecimal numeralesTodos) {
		this.numeralesTodos = numeralesTodos;
	}


	public BigDecimal getPctFuncionario() {
		return pctFuncionario;
	}


	public void setPctFuncionario(BigDecimal pctFuncionario) {
		this.pctFuncionario = pctFuncionario;
	}


	public BigDecimal getDistribucionFuncionario() {
		return distribucionFuncionario;
	}


	public void setDistribucionFuncionario(BigDecimal distribFuncionario) {
		this.distribucionFuncionario = distribFuncionario;
	}
}
