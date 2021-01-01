package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fprocedimientos")
public class Procedimiento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9112008224252352056L;

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idfprocedimientos", unique = true, nullable = false)
	private Integer idfprocedimientos;
	
	@Column
	private String descripcion;
	
	public Procedimiento() {
		super();
	}

	public Integer getIdfprocedimientos() {
		return idfprocedimientos;
	}

	public void setIdfprocedimientos(Integer idfprocedimientos) {
		this.idfprocedimientos = idfprocedimientos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
