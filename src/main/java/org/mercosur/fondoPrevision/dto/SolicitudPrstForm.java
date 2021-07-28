package org.mercosur.fondoPrevision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.mercosur.fondoPrevision.entities.ParamPrestamo;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.User;

public class SolicitudPrstForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8100481924687930479L;
	
	private User user;
	
	private Long idfuncionario;
	
	private Prestamo prestamo;
	
	private Integer idTipoPrestamo;
	
	private Long idfsolicitud;
	
	private Integer idparamPrst;
	
	private List<ParamPrestamo> lstpprst = new ArrayList<ParamPrestamo>();
	
	private BigDecimal capitalNeto;
	
	private BigDecimal totalCuotas;
	
	private String cancelPrst;
	
	private BigDecimal cuarentaPorCiento;
	
	private String obsComision;
	
	private String accion;
	
	private BigDecimal capital;
	
	private BigDecimal tasa;
	
	private BigDecimal cuota;
	
	private Integer plazo;
		
	public SolicitudPrstForm() {
		super();
	}


	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public Integer getIdTipoPrestamo() {
		return idTipoPrestamo;
	}

	public void setIdTipoPrestamo(Integer idTipoPrestamo) {
		this.idTipoPrestamo = idTipoPrestamo;
	}

	public Long getIdfuncionario() {
		return idfuncionario;
	}

	public void setIdfuncionario(Long idfuncionario) {
		this.idfuncionario = idfuncionario;
	}


	public String getCancelPrst() {
		return cancelPrst;
	}


	public void setCancelPrst(String cancelPrst) {
		this.cancelPrst = cancelPrst;
	}


	public Long getIdfsolicitud() {
		return idfsolicitud;
	}


	public void setIdfsolicitud(Long idsolicitud) {
		this.idfsolicitud = idsolicitud;
	}


	public BigDecimal getCapitalNeto() {
		return capitalNeto;
	}


	public void setCapitalNeto(BigDecimal capitalNeto) {
		this.capitalNeto = capitalNeto;
	}


	public BigDecimal getTotalCuotas() {
		return totalCuotas;
	}


	public void setTotalCuotas(BigDecimal totalCuotas) {
		this.totalCuotas = totalCuotas;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public BigDecimal getCuarentaPorCiento() {
		return cuarentaPorCiento;
	}


	public void setCuarentaPorCiento(BigDecimal cuarentaPorCiento) {
		this.cuarentaPorCiento = cuarentaPorCiento;
	}


	public String getObsComision() {
		return obsComision;
	}


	public void setObsComision(String obsComision) {
		this.obsComision = obsComision;
	}

	public Integer getIdparamPrst() {
		return idparamPrst;
	}


	public void setIdparamPrst(Integer idparamPrst) {
		this.idparamPrst = idparamPrst;
	}


	public List<ParamPrestamo> getLstpprst() {
		return lstpprst;
	}


	public void setLstpprst(List<ParamPrestamo> lstpprst) {
		this.lstpprst = lstpprst;
	}


	public String getAccion() {
		return accion;
	}


	public void setAccion(String accion) {
		this.accion = accion;
	}


	public BigDecimal getCapital() {
		return capital;
	}


	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}


	public BigDecimal getTasa() {
		return tasa;
	}


	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}


	public BigDecimal getCuota() {
		return cuota;
	}


	public void setCuota(BigDecimal cuota) {
		this.cuota = cuota;
	}


	public Integer getPlazo() {
		return plazo;
	}


	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}
	
}
