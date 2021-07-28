package org.mercosur.fondoPrevision.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;



public class PrstNuevoForm {

	@NotNull
	private Long idfuncionario;
	private Integer idtipoprst;
	private Integer idparamPrst;
	private Long idfprestamos;
	private Integer nroprestamo;
	private String tipoprestamo;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate fechaprestamo;
	@NotNull(message="El capital solicitado no ha sido ingresado!!")
	private BigDecimal capitalPrst;
	@NotNull(message="Se debe ingresar la tasa de interes!")
	private BigDecimal tasa;
	private BigDecimal cuotaPrst;
	@NotNull(message="Se debe ingresar la Cantidad de Cuotas!")
	private Short cantCuotas;
	private Boolean prstNuevo;
	private BigDecimal saldoPrst;
	private Integer tarjeta;
	private Short cuotasPagas;
	private Long idsolicitud;
	private String prstAcancelar;
	private String errorMessage;
	private String successMessage;
	
	private Integer nroprst;
	
	public PrstNuevoForm() {
		super();

	}

	public PrstNuevoForm(Integer nroPrest) {
		this.nroprestamo = nroPrest;
	}
	public Long getIdfuncionario() {
		return idfuncionario;
	}

	public void setIdfuncionario(Long idfuncionario) {
		this.idfuncionario = idfuncionario;
	}

	public Integer getNroprestamo() {
		return nroprestamo;
	}

	public void setNroprestamo(Integer nroprestamo) {
		this.nroprestamo = nroprestamo;
	}

	public String getTipoprestamo() {
		return tipoprestamo;
	}

	public void setTipoprestamo(String tipoprestamo) {
		this.tipoprestamo = tipoprestamo;
	}

	public BigDecimal getCapitalPrst() {
		return capitalPrst;
	}

	public void setCapitalPrst(BigDecimal capitalPrst) {
		this.capitalPrst = capitalPrst;
	}

	public BigDecimal getTasa() {
		return tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	public BigDecimal getCuotaPrst() {
		return cuotaPrst;
	}

	public void setCuotaPrst(BigDecimal cuotaPrst) {
		this.cuotaPrst = cuotaPrst;
	}

	public Short getCantCuotas() {
		return cantCuotas;
	}

	public void setCantCuotas(Short cantCuotas) {
		this.cantCuotas = cantCuotas;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public Integer getIdtipoprst() {
		return idtipoprst;
	}

	public void setIdtipoprst(Integer idtipoprst) {
		this.idtipoprst = idtipoprst;
	}

	public Long getIdfprestamos() {
		return idfprestamos;
	}

	public void setIdfprestamos(Long idfprestamos) {
		this.idfprestamos = idfprestamos;
	}

	public Boolean getPrstNuevo() {
		return prstNuevo;
	}

	public void setPrstNuevo(Boolean prstNuevo) {
		this.prstNuevo = prstNuevo;
	}

	public BigDecimal getSaldoPrst() {
		return saldoPrst;
	}

	public void setSaldoPrst(BigDecimal saldoPrst) {
		this.saldoPrst = saldoPrst;
	}

	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Short getCuotasPagas() {
		return cuotasPagas;
	}

	public void setCuotasPagas(Short cuotasPagas) {
		this.cuotasPagas = cuotasPagas;
	}

	public Long getIdsolicitud() {
		return idsolicitud;
	}

	public void setIdsolicitud(Long idsolicitud) {
		this.idsolicitud = idsolicitud;
	}

	public String getPrstAcancelar() {
		return prstAcancelar;
	}

	public void setPrstAcancelar(String prstAcancelar) {
		this.prstAcancelar = prstAcancelar;
	}

	public Integer getNroprst() {
		return nroprst;
	}

	public void setNroprst(Integer nroprst) {
		this.nroprst = nroprst;
	}

	public LocalDate getFechaprestamo() {
		return fechaprestamo;
	}

	public void setFechaprestamo(LocalDate fechaprestamo) {
		this.fechaprestamo = fechaprestamo;
	}

	public Integer getIdparamPrst() {
		return idparamPrst;
	}

	public void setIdparamPrst(Integer idparamPrst) {
		this.idparamPrst = idparamPrst;
	}

}
