package org.mercosur.fondoPrevision.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MesLiquidacionForm {

	@NotNull
	private Long id;
	
	@NotBlank(message="el mes liquidacion no puede estar en blanco")
	private String mesliquidacion;

	public MesLiquidacionForm() {
		
	}
	
	public MesLiquidacionForm(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}

	@Override
	public String toString() {
		return "UpdateMesLiquidacion [mesliquidacion=" + mesliquidacion + "]";
	}

}
