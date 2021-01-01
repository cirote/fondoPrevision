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
@Table(name="fsueldomes")
public class SueldoMes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3220641778483267636L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="idFSueldoMes", nullable = false, unique=true)
	private Long idfsueldomes;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gplanta_id")
	private Gplanta funcionario;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private String aniomes;
	
	@Column
	private Date fecha;
	
	@Column
	private BigDecimal sueldomes;
	
	@Column 
	private BigDecimal complemento;
	
	@Column
	private BigDecimal aguinaldoBasico;
	
	@Column
	private BigDecimal aguinaldoComplemento;
	
	@Column
	private String motivo;
	
	public SueldoMes() {
		super();
	}

	public Long getIdfsueldomes() {
		return idfsueldomes;
	}

	public void setIdfsueldomes(Long idfsueldomes) {
		this.idfsueldomes = idfsueldomes;
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

	public String getAniomes() {
		return aniomes;
	}

	public void setAniomes(String aniomes) {
		this.aniomes = aniomes;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getSueldomes() {
		return sueldomes;
	}

	public void setSueldomes(BigDecimal sueldomes) {
		this.sueldomes = sueldomes;
	}

	public BigDecimal getComplemento() {
		return complemento;
	}

	public void setComplemento(BigDecimal complemento) {
		this.complemento = complemento;
	}

	public BigDecimal getAguinaldoBasico() {
		return aguinaldoBasico;
	}

	public void setAguinaldoBasico(BigDecimal aguinaldoBasico) {
		this.aguinaldoBasico = aguinaldoBasico;
	}

	public BigDecimal getAguinaldoComplemento() {
		return aguinaldoComplemento;
	}

	public void setAguinaldoComplemento(BigDecimal aguinaldoComplemento) {
		this.aguinaldoComplemento = aguinaldoComplemento;
	}


	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
