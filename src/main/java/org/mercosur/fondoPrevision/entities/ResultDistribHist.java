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
@Table(name="fresultdistribhist")
public class ResultDistribHist implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6013930660165175669L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFResultDistribHist")
	private Long idresultDistrib;
	
	@Column
	private Long gplanta_id;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private Short fcodigos_id;
	
	@Column
	private Date fecha;
	
	@Column 
	private String mesInicial;
	
	@Column
	private String mesFinal;
	
	@Column
	private String mesLiquidacion;
	
	@Column
	private BigDecimal totalAdistribuir;
	
	@Column
	private BigDecimal numeralesFuncionario;
	
	@Column
	private BigDecimal numeralesTodos;
	
	@Column
	private BigDecimal pctFuncionario;
	
	@Column
	private BigDecimal distribucionFuncionario;
	
	public ResultDistribHist() {
		super();
	}

	public Long getIdresultDistrib() {
		return idresultDistrib;
	}

	public void setIdresultDistrib(Long idresultDistrib) {
		this.idresultDistrib = idresultDistrib;
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

	public Short getFcodigos_id() {
		return fcodigos_id;
	}

	public void setFcodigos_id(Short fcodigos_id) {
		this.fcodigos_id = fcodigos_id;
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

	public BigDecimal getTotalAdistribuir() {
		return totalAdistribuir;
	}

	public void setTotalAdistribuir(BigDecimal totalAdistribuir) {
		this.totalAdistribuir = totalAdistribuir;
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

	public void setDistribucionFuncionario(BigDecimal distribucionFuncionario) {
		this.distribucionFuncionario = distribucionFuncionario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((distribucionFuncionario == null) ? 0 : distribucionFuncionario.hashCode());
		result = prime * result + ((fcodigos_id == null) ? 0 : fcodigos_id.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((gplanta_id == null) ? 0 : gplanta_id.hashCode());
		result = prime * result + ((idresultDistrib == null) ? 0 : idresultDistrib.hashCode());
		result = prime * result + ((mesFinal == null) ? 0 : mesFinal.hashCode());
		result = prime * result + ((mesInicial == null) ? 0 : mesInicial.hashCode());
		result = prime * result + ((mesLiquidacion == null) ? 0 : mesLiquidacion.hashCode());
		result = prime * result + ((numeralesFuncionario == null) ? 0 : numeralesFuncionario.hashCode());
		result = prime * result + ((numeralesTodos == null) ? 0 : numeralesTodos.hashCode());
		result = prime * result + ((pctFuncionario == null) ? 0 : pctFuncionario.hashCode());
		result = prime * result + ((tarjeta == null) ? 0 : tarjeta.hashCode());
		result = prime * result + ((totalAdistribuir == null) ? 0 : totalAdistribuir.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ResultDistribHist))
			return false;
		ResultDistribHist other = (ResultDistribHist) obj;
		if (distribucionFuncionario == null) {
			if (other.distribucionFuncionario != null)
				return false;
		} else if (!distribucionFuncionario.equals(other.distribucionFuncionario))
			return false;
		if (fcodigos_id == null) {
			if (other.fcodigos_id != null)
				return false;
		} else if (!fcodigos_id.equals(other.fcodigos_id))
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
		if (idresultDistrib == null) {
			if (other.idresultDistrib != null)
				return false;
		} else if (!idresultDistrib.equals(other.idresultDistrib))
			return false;
		if (mesFinal == null) {
			if (other.mesFinal != null)
				return false;
		} else if (!mesFinal.equals(other.mesFinal))
			return false;
		if (mesInicial == null) {
			if (other.mesInicial != null)
				return false;
		} else if (!mesInicial.equals(other.mesInicial))
			return false;
		if (mesLiquidacion == null) {
			if (other.mesLiquidacion != null)
				return false;
		} else if (!mesLiquidacion.equals(other.mesLiquidacion))
			return false;
		if (numeralesFuncionario == null) {
			if (other.numeralesFuncionario != null)
				return false;
		} else if (!numeralesFuncionario.equals(other.numeralesFuncionario))
			return false;
		if (numeralesTodos == null) {
			if (other.numeralesTodos != null)
				return false;
		} else if (!numeralesTodos.equals(other.numeralesTodos))
			return false;
		if (pctFuncionario == null) {
			if (other.pctFuncionario != null)
				return false;
		} else if (!pctFuncionario.equals(other.pctFuncionario))
			return false;
		if (tarjeta == null) {
			if (other.tarjeta != null)
				return false;
		} else if (!tarjeta.equals(other.tarjeta))
			return false;
		if (totalAdistribuir == null) {
			if (other.totalAdistribuir != null)
				return false;
		} else if (!totalAdistribuir.equals(other.totalAdistribuir))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResultDistribHist [idresultDistrib=" + idresultDistrib + ", gplanta_id=" + gplanta_id + ", tarjeta="
				+ tarjeta + ", fcodigos_id=" + fcodigos_id + ", fecha=" + fecha + ", mesInicial=" + mesInicial
				+ ", mesFinal=" + mesFinal + ", mesLiquidacion=" + mesLiquidacion + ", totalAdistribuir="
				+ totalAdistribuir + ", numeralesFuncionario=" + numeralesFuncionario + ", numeralesTodos="
				+ numeralesTodos + ", pctFuncionario=" + pctFuncionario + ", distribucionFuncionario="
				+ distribucionFuncionario + "]";
	}

	
}
