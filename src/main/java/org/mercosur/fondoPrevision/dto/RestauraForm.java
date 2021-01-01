package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.util.List;

import org.mercosur.fondoPrevision.entities.Procedimiento;

public class RestauraForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1010074842475614491L;

	private Integer idprocedimientos;
	
	private String descripcion;
	
	private List<Procedimiento> lstProcs;
	
	private Procedimiento procElegido;
	
	public RestauraForm() {
		super();
	}

	public RestauraForm(String nombreProc) {
		this.descripcion = nombreProc ;
	}
	
	public Integer getIdprocedimientos() {
		return idprocedimientos;
	}

	public void setIdprocedimientos(Integer idprocedimientos) {
		this.idprocedimientos = idprocedimientos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Procedimiento> getLstProcs() {
		return lstProcs;
	}

	public void setLstProcs(List<Procedimiento> lstProcs) {
		this.lstProcs = lstProcs;
	}

	public Procedimiento getProcElegido() {
		return procElegido;
	}

	public void setProcElegido(Procedimiento procElegido) {
		this.procElegido = procElegido;
	}
	
	
}
