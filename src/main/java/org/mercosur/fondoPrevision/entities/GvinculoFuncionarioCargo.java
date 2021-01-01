package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gvinculofuncargo")
public class GvinculoFuncionarioCargo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2081531004032169896L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long idgvinculofuncargo;
	
	@Column
	private Integer gcargos_id;
	
	@Column
	private Long gplanta_id;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private Date fecha_inicial;
	
	@Column
	private Date fecha_final;
	
	public GvinculoFuncionarioCargo() {
		super();
	}

	public Long getIdgvinculofuncargo() {
		return idgvinculofuncargo;
	}

	public void setIdgvinculofuncargo(Long idgvinculofuncargo) {
		this.idgvinculofuncargo = idgvinculofuncargo;
	}

	public Integer getGcargos_id() {
		return gcargos_id;
	}

	public void setGcargos_id(Integer gcargo_id) {
		this.gcargos_id = gcargo_id;
	}

	public Long getGplanta_id() {
		return gplanta_id;
	}

	public void setGplanta_id(Long gplanta_id) {
		this.gplanta_id = gplanta_id;
	}

	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Date getFecha_inicial() {
		return fecha_inicial;
	}

	public void setFecha_inicial(Date fecha_inicial) {
		this.fecha_inicial = fecha_inicial;
	}

	public Date getFecha_final() {
		return fecha_final;
	}

	public void setFecha_final(Date fecha_final) {
		this.fecha_final = fecha_final;
	}
	
}
