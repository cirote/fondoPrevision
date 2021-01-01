package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ftipomovimiento")
public class TipoMovimiento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3550393514349845677L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="idFTipoMovimiento", nullable = false, unique=true)
	private Integer idftipomovimiento;
	
	@Column
	private Short codigoMovimiento;
	
	@Column
	private String descripcion;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoMovimiento")
	private List<Movimientos> lstMovimientos = new ArrayList<Movimientos>();
	
	public TipoMovimiento() {
		super();
	}


	public Integer getIdftipomovimiento() {
		return idftipomovimiento;
	}


	public void setIdftipomovimiento(Integer idftipomovimiento) {
		this.idftipomovimiento = idftipomovimiento;
	}



	public Short getCodigoMovimiento() {
		return codigoMovimiento;
	}


	public void setCodigoMovimiento(Short codigoMovimiento) {
		this.codigoMovimiento = codigoMovimiento;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
