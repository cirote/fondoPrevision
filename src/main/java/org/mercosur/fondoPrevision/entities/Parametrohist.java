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
@Table(name="fparametroshist")
public class Parametrohist implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5468388221751669188L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idfparametroshist", unique = true, nullable = false)

	private Long idfparametroshist;
	
	@Column
	private String mesliquidacion;
	
	@Column
	private String descripcion;
	
	@Column
	private BigDecimal valor;
	
	@Column
	private String simbolo;

	public Long getIdfparametroshist() {
		return idfparametroshist;
	}

	public void setIdfparametroshist(Long idfparametroshist) {
		this.idfparametroshist = idfparametroshist;
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

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	@Override
	public String toString() {
		return "Parametrohist [idfparametroshist=" + idfparametroshist + ", mesliquidacion=" + mesliquidacion
				+ ", descripcion=" + descripcion + ", valor=" + valor + ", simbolo=" + simbolo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((idfparametroshist == null) ? 0 : idfparametroshist.hashCode());
		result = prime * result + ((mesliquidacion == null) ? 0 : mesliquidacion.hashCode());
		result = prime * result + ((simbolo == null) ? 0 : simbolo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Parametrohist))
			return false;
		Parametrohist other = (Parametrohist) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (idfparametroshist == null) {
			if (other.idfparametroshist != null)
				return false;
		} else if (!idfparametroshist.equals(other.idfparametroshist))
			return false;
		if (mesliquidacion == null) {
			if (other.mesliquidacion != null)
				return false;
		} else if (!mesliquidacion.equals(other.mesliquidacion))
			return false;
		if (simbolo == null) {
			if (other.simbolo != null)
				return false;
		} else if (!simbolo.equals(other.simbolo))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	
}
