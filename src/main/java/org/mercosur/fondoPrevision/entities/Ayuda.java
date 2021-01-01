package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ayuda")
public class Ayuda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2252740855740007578L;

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idayuda", unique = true, nullable = false)
	private Long idayuda;
		
	@Column
	private String clave;
	
	@Column
	private String texto;

	public Long getIdayuda() {
		return idayuda;
	}

	public void setIdayuda(Long idayuda) {
		this.idayuda = idayuda;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public String toString() {
		return "Ayuda [idayuda=" + idayuda + ", clave=" + clave + ", texto=" + texto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
		result = prime * result + ((idayuda == null) ? 0 : idayuda.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Ayuda))
			return false;
		Ayuda other = (Ayuda) obj;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		if (idayuda == null) {
			if (other.idayuda != null)
				return false;
		} else if (!idayuda.equals(other.idayuda))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}
	
	
	
}
