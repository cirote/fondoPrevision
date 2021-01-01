package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="gplanta")
public class Gplanta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -400510105388396309L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idGPlanta", unique = true, nullable = false)
	private Long idgplanta;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gcargos_id")	
	private Gcargo gcargo;
	
	@Column
	private String unidad;
	
	@Column
	private String sector;
	
	@Column
	@Email
	private String email;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
	private List<SueldoMes> lstsueldos = new ArrayList<SueldoMes>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
	private List<Prestamo> lstPrsts = new ArrayList<Prestamo>();
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "funcionario")
	private Saldos saldos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
	private List<Movimientos> lstMovimientos = new ArrayList<Movimientos>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
	private List<ResultadoDistribucion> lstResDistrib = new ArrayList<ResultadoDistribucion>();
	
	public List<SueldoMes> getLstsueldos() {
		return lstsueldos;
	}

	public void setLstsueldos(List<SueldoMes> lstsueldos) {
		this.lstsueldos = lstsueldos;
	}

	public Gplanta() {
		this.gcargo = new Gcargo();
	}
	
	public Gplanta(Gcargo cargo) {
		this.setGcargo(cargo);
	}
	
	public Long getIdgplanta() {
		return idgplanta;
	}

	public void setIdgplanta(Long idgplanta) {
		this.idgplanta = idgplanta;
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

	public Gcargo getGcargo() {
		return gcargo;
	}

	public void setGcargo(Gcargo gcargo) {
		this.gcargo = gcargo;
	}

	public Date getIngreso() {
		return ingreso;
	}

	public void setIngreso(Date ingreso) {
		this.ingreso = ingreso;
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

	public Date getEgreso() {
		return egreso;
	}

	public void setEgreso(Date egreso) {
		this.egreso = egreso;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Saldos getSaldos() {
		return saldos;
	}

	public void setSaldos(Saldos saldos) {
		this.saldos = saldos;
	}

	public List<Movimientos> getLstMovimientos() {
		return lstMovimientos;
	}

	public void setLstMovimientos(List<Movimientos> lstMovimientos) {
		this.lstMovimientos = lstMovimientos;
	}

	public List<Prestamo> getLstPrsts() {
		return lstPrsts;
	}

	public void setLstPrsts(List<Prestamo> lstPrsts) {
		this.lstPrsts = lstPrsts;
	}

	public Boolean getUltimoIngreso() {
		return ultimoIngreso;
	}

	public void setUltimoIngreso(Boolean ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
	}

	public List<ResultadoDistribucion> getLstResDistrib() {
		return lstResDistrib;
	}

	public void setLstResDistrib(List<ResultadoDistribucion> lstResDistrib) {
		this.lstResDistrib = lstResDistrib;
	}

	
}
