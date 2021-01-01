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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fsaldos")
public class Saldos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -910098509759190965L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="idFSaldos", nullable = false, unique=true)
	private Long idfsaldos;
	
	@Column
	private Integer tarjeta;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gplanta_id")
	private Gplanta funcionario;
	
	@Column
	private Date fecha;
	
	@Column
	private BigDecimal capitalIntegAntes;
	
	@Column
	private BigDecimal capitalIntegActual;
	
	@Column
	private BigDecimal capitalDispAntes;
	
	@Column
	private BigDecimal capitalDispActual;
	
	@Column
	private BigDecimal numerales;
	
	@Column
	private String mesliquidacion;
	
	
	public Saldos() {
		super();
	}

	public Long getIdfsaldos() {
		return idfsaldos;
	}


	public void setIdfsaldos(Long idfsaldos) {
		this.idfsaldos = idfsaldos;
	}

	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Gplanta getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Gplanta funcionario) {
		this.funcionario = funcionario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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


	public BigDecimal getNumerales() {
		return numerales;
	}

	public void setNumerales(BigDecimal numerales) {
		this.numerales = numerales;
	}


	public String getMesliquidacion() {
		return mesliquidacion;
	}


	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}
}
