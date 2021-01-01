package org.mercosur.fondoPrevision.dto;

import org.mercosur.fondoPrevision.entities.Gplanta;

public class MovimientosForm {

	
	private Long idfuncionario;
	
	private Gplanta funcionario;

	private String mesdesde;

	private String meshasta;
	
	public MovimientosForm() {
		super();
	}

	public Long getIdfuncionario() {
		return idfuncionario;
	}

	public void setIdfuncionario(Long idfuncionario) {
		this.idfuncionario = idfuncionario;
	}

	public Gplanta getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Gplanta funcionario) {
		this.funcionario = funcionario;
	}

	public String getMesdesde() {
		return mesdesde;
	}

	public void setMesdesde(String mesdesde) {
		this.mesdesde = mesdesde;
	}

	public String getMeshasta() {
		return meshasta;
	}

	public void setMeshasta(String meshasta) {
		this.meshasta = meshasta;
	}
}
