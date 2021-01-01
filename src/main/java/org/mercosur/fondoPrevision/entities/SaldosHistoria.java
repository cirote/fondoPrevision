package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fsaldoshistoria")
public class SaldosHistoria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -910098509759190965L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="idFSaldosHistoria", nullable = false, unique=true)
	private Long idfsaldoshistoria;
	
	@Column
	private Long gplanta_id;
	
	@Column
	private Integer tarjeta;
	
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
	
	@Column
	private String motivo;
	
	
	public SaldosHistoria() {
		super();
	}


	public Long getIdfsaldoshistoria() {
		return idfsaldoshistoria;
	}


	public void setIdfsaldoshistoria(Long idfsaldoshistoria) {
		this.idfsaldoshistoria = idfsaldoshistoria;
	}


	public Long getGplanta_id() {
		return gplanta_id;
	}

	public void setGplanta_id(Long gplanta_id) {
		this.gplanta_id = gplanta_id;
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


	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
