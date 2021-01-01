package org.mercosur.fondoPrevision.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.mercosur.fondoPrevision.entities.Gcargo;
import org.mercosur.fondoPrevision.entities.Gplanta;

public class CargoNuevoForm {
	@NotBlank(message="El básico no puede estar vacío")
	@Pattern(regexp="\\d+(\\.\\d+)?", message="Básico inválido....")
	private String basico;
	
	@Pattern(regexp="\\d+(\\.\\d+)?", message="Complemento inválido....")
	private String complemento;
	
	private Gplanta funcionario;
	
	private Long idgplanta;
	
	private Integer idgcargo;
	
	@NotBlank(message="Se debe ingresar la fecha de acceso al cargo")
	@Pattern(regexp="\\d{2}/\\d{2}/(\\d{4})?", message="Fecha invalida...")
	private String fechaDeAcceso;
	
	private Gcargo cargoActual;
	
	private Integer idcargoactual;
	
	public CargoNuevoForm() {	}

	public String getBasico() {
		return basico;
	}

	public void setBasico(String basico) {
		this.basico = basico;
	}

	public Gplanta getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Gplanta funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getIdgcargo() {
		return idgcargo;
	}

	public void setIdgcargo(Integer idcargo) {
		this.idgcargo = idcargo;
	}

	public Gcargo getCargoActual() {
		return cargoActual;
	}

	public void setCargoActual(Gcargo cargoActual) {
		this.cargoActual = cargoActual;
	}

	public Long getIdgplanta() {
		return idgplanta;
	}

	public void setIdgplanta(Long idfuncionario) {
		this.idgplanta = idfuncionario;
	}

	public Integer getIdcargoactual() {
		return idcargoactual;
	}

	public void setIdcargoactual(Integer idcargoactual) {
		this.idcargoactual = idcargoactual;
	}

	public String getFechaDeAcceso() {
		return fechaDeAcceso;
	}

	public void setFechaDeAcceso(String fechaDeAcceso) {
		this.fechaDeAcceso = fechaDeAcceso;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


}
