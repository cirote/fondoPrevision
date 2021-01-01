package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="fcategoriaslog")
public class CategoriaLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2188921543527290502L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idfcategoriaslog", unique = true, nullable = false)
	private Integer idfcategoriaslog;
	
	
	@Column
	private String descripcion;

	@OneToMany(mappedBy="categoriaLog")
	private List<Logfondo> logs;

	public CategoriaLog() {
		super();
	}
	
	public CategoriaLog(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Integer getIdfcategoriaslog() {
		return idfcategoriaslog;
	}


	public void setIdfcategoriaslog(Integer idfcategoriaslog) {
		this.idfcategoriaslog = idfcategoriaslog;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public List<Logfondo> getLogs() {
		return logs;
	}


	public void setLogs(List<Logfondo> logs) {
		this.logs = logs;
	}


	@Override
	public String toString() {
		return "CategoriaLog [idfcategoriaslog=" + idfcategoriaslog + ", descripcion=" + descripcion + "]";
	}
	
}
