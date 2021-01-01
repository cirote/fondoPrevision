package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gorganigrama")
public class Gorganigrama implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6925113552651093518L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idgorganigrama", nullable = false, unique = true)
	private Integer idgorganigrama;
	
	@Column
	private String unidad;
	
	@Column
	private String siglaSector;
	
	@Column
	private String nombreSector;
	
	public Gorganigrama() {
		super();
	}

	public Integer getIdgorganigrama() {
		return idgorganigrama;
	}

	public void setIdgorganigrama(Integer idgorganigrama) {
		this.idgorganigrama = idgorganigrama;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getSiglaSector() {
		return siglaSector;
	}

	public void setSiglaSector(String siglaSector) {
		this.siglaSector = siglaSector;
	}

	public String getNombreSector() {
		return nombreSector;
	}

	public void setNombreSector(String nombreSector) {
		this.nombreSector = nombreSector;
	}

}
