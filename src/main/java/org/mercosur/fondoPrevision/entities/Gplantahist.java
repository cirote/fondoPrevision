package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="gplantahist")
public class Gplantahist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 997288039993682372L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idGPlantahist")
	private Long idgplantahist;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private String nombre;

	@Column
	private String documento;
	
	@Column
	private String nacionalidad;
	
	@Column
	private String sexo;
	
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date ingreso;
	
	private Boolean ultimoIngreso;
	
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date egreso;

	@Column
	private Integer gcargos_id;
	
	@Column
	private String unidad;
	
	@Column
	private String sector;
	
	@Column
	@Email
	private String email;

	public Gplantahist() {
		super();
	}


	public Long getIdgplantahist() {
		return idgplantahist;
	}


	public void setIdgplantahist(Long idgplantahist) {
		this.idgplantahist = idgplantahist;
	}


	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getIngreso() {
		return ingreso;
	}

	public void setIngreso(Date ingreso) {
		this.ingreso = ingreso;
	}

	public Boolean getUltimoIngreso() {
		return ultimoIngreso;
	}

	public void setUltimoIngreso(Boolean ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
	}

	public Date getEgreso() {
		return egreso;
	}

	public void setEgreso(Date egreso) {
		this.egreso = egreso;
	}

	public Integer getGcargos_id() {
		return gcargos_id;
	}

	public void setGcargos_id(Integer gcargos_id) {
		this.gcargos_id = gcargos_id;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
