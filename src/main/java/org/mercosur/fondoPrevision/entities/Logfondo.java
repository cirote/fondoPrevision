package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "logfondo")
public class Logfondo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -546852171894247271L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idlogfondo", unique = true, nullable = false)
	private Long idlogfondo;
		
	@ManyToOne
    @JoinColumn(name="fcategoriaslog_id", referencedColumnName = "idfcategoriaslog", nullable=false)
	private CategoriaLog categoriaLog;
	
	@Column
	private String procedimiento;
	
	@Column
	private Date fechahora;
	
	@Column
	private String username;

	public Logfondo() {
		super();
	}
	
	public Logfondo(CategoriaLog categoria) {
		this.categoriaLog = categoria;
	}
	
	public Long getIdlogfondo() {
		return idlogfondo;
	}

	public void setIdlogfondo(Long idlogfondo) {
		this.idlogfondo = idlogfondo;
	}

	public CategoriaLog getCategoriaLog() {
		return categoriaLog;
	}

	public void setCategoriaLog(CategoriaLog categoriaLog) {
		this.categoriaLog = categoriaLog;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public Date getFechahora() {
		return fechahora;
	}

	public void setFechahora(Date fechahora) {
		this.fechahora = fechahora;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Logfondo [idlogfondo=" + idlogfondo + ", procedimiento=" + procedimiento + ", fechahora=" + fechahora
				+ ", username=" + username + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechahora == null) ? 0 : fechahora.hashCode());
		result = prime * result + ((idlogfondo == null) ? 0 : idlogfondo.hashCode());
		result = prime * result + ((procedimiento == null) ? 0 : procedimiento.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Logfondo other = (Logfondo) obj;
		if (fechahora == null) {
			if (other.fechahora != null)
				return false;
		} else if (!fechahora.equals(other.fechahora))
			return false;
		if (idlogfondo == null) {
			if (other.idlogfondo != null)
				return false;
		} else if (!idlogfondo.equals(other.idlogfondo))
			return false;
		if (procedimiento == null) {
			if (other.procedimiento != null)
				return false;
		} else if (!procedimiento.equals(other.procedimiento))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
}
