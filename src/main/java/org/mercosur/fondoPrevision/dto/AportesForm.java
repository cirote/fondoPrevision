package org.mercosur.fondoPrevision.dto;

import java.math.BigDecimal;

import org.mercosur.fondoPrevision.entities.Ayuda;

public class AportesForm {

	private String mesliquidacion;

	private FuncionarioConSueldoMes funcionario;
	
	private String aporteSobre;	//contiene el valor del ENUM elegido por el usuario
	
	private String desde;
	
	private String hasta;
	
	private Integer tarjeta;
	
	private BigDecimal basico;
	
	private BigDecimal complemento;
	
	private Ayuda help = new Ayuda();
	
	public AportesForm() {
		
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}

	public Ayuda getHelp() {
		return help;
	}

	public void setHelp(Ayuda help) {
		this.help = help;
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

	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}

	public FuncionarioConSueldoMes getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioConSueldoMes funcionario) {
		this.funcionario = funcionario;
	}

	public String getAporteSobre() {
		return aporteSobre;
	}

	public void setAporteSobre(String aporteSobre) {
		this.aporteSobre = aporteSobre;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}
	
}
