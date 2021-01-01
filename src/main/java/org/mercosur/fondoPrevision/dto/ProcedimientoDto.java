package org.mercosur.fondoPrevision.dto;

public class ProcedimientoDto {
	
	private String nombreproc;
	
	private Integer idproc;
	
	public ProcedimientoDto() {
		
	}
	
	public ProcedimientoDto(String nombre, Integer id) {
		this.nombreproc = nombre;
		this.idproc = id;
	}

	public String getNombreproc() {
		return nombreproc;
	}

	public void setNombreproc(String nombreproc) {
		this.nombreproc = nombreproc;
	}

	public Integer getIdproc() {
		return idproc;
	}

	public void setIdproc(Integer idproc) {
		this.idproc = idproc;
	}
}
