package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.entities.Gcargo;

public class SalarioForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6165441612274598574L;

	private BigDecimal pctAumento;
	
	private Ayuda help;
	
	private List<Gcargo> lstCargos;
	
	private Boolean complementoMayorquecero;
	
	public SalarioForm() {
		super();
	}

	public SalarioForm(Boolean valorComplemento) {
		super();
		this.complementoMayorquecero = valorComplemento;
	}
	
	public BigDecimal getPctAumento() {
		return pctAumento;
	}

	public void setPctAumento(BigDecimal pctAumento) {
		this.pctAumento = pctAumento;
	}

	public Ayuda getHelp() {
		return help;
	}

	public void setHelp(Ayuda help) {
		this.help = help;
	}

	public List<Gcargo> getLstCargos() {
		return lstCargos;
	}

	public void setLstCargos(List<Gcargo> lstCargos) {
		this.lstCargos = lstCargos;
	}

	public Boolean getComplementoMayorquecero() {
		return complementoMayorquecero;
	}

	public void setComplementoMayorquecero(Boolean complementoMayorquecero) {
		this.complementoMayorquecero = complementoMayorquecero;
	}
	
	
}
