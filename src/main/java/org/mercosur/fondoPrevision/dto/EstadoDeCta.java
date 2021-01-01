package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Prestamo;

public class EstadoDeCta implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1715775663996015541L;

	
	private BigDecimal capIntegActual;
	
	private BigDecimal capDispOperable;
	
	private BigDecimal saldoPrstAcum;
	
	private BigDecimal saldoDisponible;
	
	private BigDecimal basico;
	
	private BigDecimal complemento;
	
	private BigDecimal totalNominal;
	
	private BigDecimal cuarentaPorCiento;
	
	private BigDecimal sumaDeCuotas;
	
	private List<Prestamo> lstPrst;
	
	private List<AportesSummary> lstAportes;
	
	private BigDecimal pctReserva;
	
	private Long idfuncionario;
	
	private Gplanta funcionario;
	
	public EstadoDeCta() {
		super();
	}

	public BigDecimal getCapIntegActual() {
		return capIntegActual;
	}

	public void setCapIntegActual(BigDecimal capIntegActual) {
		this.capIntegActual = capIntegActual;
	}

	public BigDecimal getCapDispOperable() {
		return capDispOperable;
	}

	public void setCapDispOperable(BigDecimal capDispOperable) {
		this.capDispOperable = capDispOperable;
	}

	public BigDecimal getSaldoPrstAcum() {
		return saldoPrstAcum;
	}

	public void setSaldoPrstAcum(BigDecimal saldoPrstAcum) {
		this.saldoPrstAcum = saldoPrstAcum;
	}

	public BigDecimal getSaldoDisponible() {
		return saldoDisponible;
	}

	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	public BigDecimal getBasico() {
		return basico;
	}

	public void setBasico(BigDecimal basico) {
		this.basico = basico;
	}

	public BigDecimal getCuarentaPorCiento() {
		return cuarentaPorCiento;
	}

	public void setCuarentaPorCiento(BigDecimal cuarentaPorCiento) {
		this.cuarentaPorCiento = cuarentaPorCiento;
	}

	public BigDecimal getSumaDeCuotas() {
		return sumaDeCuotas;
	}

	public void setSumaDeCuotas(BigDecimal sumaDeCuotas) {
		this.sumaDeCuotas = sumaDeCuotas;
	}

	public List<Prestamo> getLstPrst() {
		return lstPrst;
	}

	public void setLstPrst(List<Prestamo> lstPrst) {
		this.lstPrst = lstPrst;
	}

	public BigDecimal getPctReserva() {
		return pctReserva;
	}

	public void setPctReserva(BigDecimal pctReserva) {
		this.pctReserva = pctReserva;
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

	public BigDecimal getComplemento() {
		return complemento;
	}

	public void setComplemento(BigDecimal complemento) {
		this.complemento = complemento;
	}

	public BigDecimal getTotalNominal() {
		return totalNominal;
	}

	public void setTotalNominal(BigDecimal totalNominal) {
		this.totalNominal = totalNominal;
	}

	public void setFuncionario(Gplanta funcionario) {
		this.funcionario = funcionario;
	}

	public List<AportesSummary> getLstAportes() {
		return lstAportes;
	}

	public void setLstAportes(List<AportesSummary> lstAportes) {
		this.lstAportes = lstAportes;
	}
}
