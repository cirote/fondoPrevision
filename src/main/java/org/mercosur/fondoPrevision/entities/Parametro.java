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
@Table(name="fparametros")
public class Parametro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2544705741335007777L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idfparametros", unique = true, nullable = false)
	private Long idfparametros;
	
	@Column
	private String mesliquidacion;
	
	@Column
	private String descripcion;
	
	@Column
	private BigDecimal valor;
	
	@Column
	private String simbolo;
		
	public Long getIdfparametros() {
		return idfparametros;
	}

	public void setIdfparametros(Long idfparametros) {
		this.idfparametros = idfparametros;
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
		return "Parametro [idfparametros=" + idfparametros + ", mesliquidacion=" + mesliquidacion + ", descripcion="
				+ descripcion + ", valor=" + valor + ", simbolo=" + simbolo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((idfparametros == null) ? 0 : idfparametros.hashCode());
		result = prime * result + ((mesliquidacion == null) ? 0 : mesliquidacion.hashCode());
		result = prime * result + ((simbolo == null) ? 0 : simbolo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parametro other = (Parametro) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (idfparametros == null) {
			if (other.idfparametros != null)
				return false;
		} else if (!idfparametros.equals(other.idfparametros))
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
