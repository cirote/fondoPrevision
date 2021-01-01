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
@Table(name="fsueldomeshist")
public class SueldoMesHist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2936529463471376630L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFSueldoMesHist")
	private Long idsueldoMes;
	
	@Column
	private Long gplanta_id;
	
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
	
	public SueldoMesHist() {
		super();
	}

	public Long getIdsueldoMes() {
		return idsueldoMes;
	}

	public void setIdsueldoMes(Long idsueldoMes) {
		this.idsueldoMes = idsueldoMes;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aguinaldoBasico == null) ? 0 : aguinaldoBasico.hashCode());
		result = prime * result + ((aguinaldoComplemento == null) ? 0 : aguinaldoComplemento.hashCode());
		result = prime * result + ((aniomes == null) ? 0 : aniomes.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((gplanta_id == null) ? 0 : gplanta_id.hashCode());
		result = prime * result + ((idsueldoMes == null) ? 0 : idsueldoMes.hashCode());
		result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
		result = prime * result + ((sueldomes == null) ? 0 : sueldomes.hashCode());
		result = prime * result + ((tarjeta == null) ? 0 : tarjeta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SueldoMesHist))
			return false;
		SueldoMesHist other = (SueldoMesHist) obj;
		if (aguinaldoBasico == null) {
			if (other.aguinaldoBasico != null)
				return false;
		} else if (!aguinaldoBasico.equals(other.aguinaldoBasico))
			return false;
		if (aguinaldoComplemento == null) {
			if (other.aguinaldoComplemento != null)
				return false;
		} else if (!aguinaldoComplemento.equals(other.aguinaldoComplemento))
			return false;
		if (aniomes == null) {
			if (other.aniomes != null)
				return false;
		} else if (!aniomes.equals(other.aniomes))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (gplanta_id == null) {
			if (other.gplanta_id != null)
				return false;
		} else if (!gplanta_id.equals(other.gplanta_id))
			return false;
		if (idsueldoMes == null) {
			if (other.idsueldoMes != null)
				return false;
		} else if (!idsueldoMes.equals(other.idsueldoMes))
			return false;
		if (motivo == null) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		if (sueldomes == null) {
			if (other.sueldomes != null)
				return false;
		} else if (!sueldomes.equals(other.sueldomes))
			return false;
		if (tarjeta == null) {
			if (other.tarjeta != null)
				return false;
		} else if (!tarjeta.equals(other.tarjeta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SueldoMesHist [idsueldoMes=" + idsueldoMes + ", gplanta_id=" + gplanta_id + ", tarjeta=" + tarjeta
				+ ", aniomes=" + aniomes + ", fecha=" + fecha + ", sueldomes=" + sueldomes + ", complemento="
				+ complemento + ", aguinaldoBasico=" + aguinaldoBasico + ", aguinaldoComplemento="
				+ aguinaldoComplemento + ", motivo=" + motivo + "]";
	}

	
}
