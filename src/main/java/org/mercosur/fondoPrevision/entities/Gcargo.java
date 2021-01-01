package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="gcargos")
public class Gcargo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2430883246745708775L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idgcargos", nullable=false, unique=true)
	private Integer idgcargo;
	
	@Column
	private String unidad;
	
	@Column
	private String descripCargo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gcargo")
	private List<Gplanta> funcionarios = new ArrayList<Gplanta>();

	@Column
	private BigDecimal basico;
	
	@Column
	private BigDecimal complemento;

	
	public Gcargo() {
		super();
	}
	
	public Integer getIdgcargo() {
		return idgcargo;
	}

	public void setIdgcargo(Integer idgcargos) {
		this.idgcargo = idgcargos;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	
	public String getDescripCargo() {
		return descripCargo;
	}

	public void setDescripCargo(String descripCargo) {
		this.descripCargo = descripCargo;
	}

	public List<Gplanta> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Gplanta> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public BigDecimal getBasico() {
		return basico;
	}

	public void setBasico(BigDecimal basico) {
		this.basico = basico;
	}

	public BigDecimal getComplemento() {
		return complemento;
	}

	public void setComplemento(BigDecimal complemento) {
		this.complemento = complemento;
	}
	
	
	
}
