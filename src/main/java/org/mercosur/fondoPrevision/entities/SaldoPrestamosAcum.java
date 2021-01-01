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
@Table(name="fsaldosprstacum")
public class SaldoPrestamosAcum implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3440056288295700370L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="idFSaldosPrstAcum", nullable = false, unique=true)
	private Long idfsaldoPrstAcum;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gplanta_id")
	private Gplanta funcionario;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private BigDecimal saldoPrestAcumulado;
	
	@Column
	private Date fecha;
	public SaldoPrestamosAcum()
	{
		super();
	}
	public Long getIdfsaldoPrstAcum() {
		return idfsaldoPrstAcum;
	}
	public void setIdfsaldoPrstAcum(Long idfsaldoPrstAcum) {
		this.idfsaldoPrstAcum = idfsaldoPrstAcum;
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
	public BigDecimal getSaldoPrestAcumulado() {
		return saldoPrestAcumulado;
	}
	public void setSaldoPrestAcumulado(BigDecimal saldoPrestAcumulado) {
		this.saldoPrestAcumulado = saldoPrestAcumulado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
