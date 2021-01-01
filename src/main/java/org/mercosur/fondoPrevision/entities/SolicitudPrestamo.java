package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="fsolicitudprestamos")
public class SolicitudPrestamo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6737551933124752651L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	@Column(name = "idFSolicitud", unique = true, nullable = false)
	private Long idfsolicitud;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="gplanta_id")
	private Gplanta funcionario;

	@Column
	private Integer tarjeta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ftipoPrestamo_id")
	private TipoPrestamo tipoPrestamo;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="paramprst_id")
	private ParamPrestamo paramPrestamo;

	@Column
	private Short codigoPrestamo;

	@Column
	private LocalDate fechaSolicitud;
	
	@Column
	private BigDecimal capitalPrestamo;
	
	@Column
	private BigDecimal interesPrestamo;
	
	public ParamPrestamo getParamPrestamo() {
		return paramPrestamo;
	}


	public void setParamPrestamo(ParamPrestamo paramPrestamo) {
		this.paramPrestamo = paramPrestamo;
	}


	@Column
	private BigDecimal cuota;
	
	@Column
	private Short cantCuotas;
	
	@Column
	private String cancelPrstNros;
	
	@Column
	private BigDecimal importeNeto;
	
	@Column
	private Boolean enviadaAfondo;
	
	@Column
	private Boolean procesada;
	
	@Column
	private String motivo;
	
	@Column
	private Boolean enviadaAComision;
	
	@Column
	private Boolean aprobada;
	
	@Column
	private Boolean aprobada2;
	
	@Column
	private Boolean rechazada;
	
	@Column
	private Boolean rechazada2;
	
	
	
	public SolicitudPrestamo() {
		super();
	}


	public Long getIdfsolicitud() {
		return idfsolicitud;
	}


	public void setIdfsolicitud(Long idfsolicitud) {
		this.idfsolicitud = idfsolicitud;
	}


	public Gplanta getFuncionario() {
		return funcionario;
	}


	public void setFuncionario(Gplanta funcionario) {
		this.funcionario = funcionario;
	}


	public Integer getTarjeta() {
		return tarjeta;
	}


	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}


	public TipoPrestamo getTipoPrestamo() {
		return tipoPrestamo;
	}


	public void setTipoPrestamo(TipoPrestamo tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}


	public Short getCodigoPrestamo() {
		return codigoPrestamo;
	}


	public void setCodigoPrestamo(Short codigoPrestamo) {
		this.codigoPrestamo = codigoPrestamo;
	}


	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}


	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}


	public BigDecimal getCapitalPrestamo() {
		return capitalPrestamo;
	}


	public void setCapitalPrestamo(BigDecimal capitalPrestamo) {
		this.capitalPrestamo = capitalPrestamo;
	}


	public BigDecimal getInteresPrestamo() {
		return interesPrestamo;
	}


	public void setInteresPrestamo(BigDecimal interesPrestamo) {
		this.interesPrestamo = interesPrestamo;
	}


	public BigDecimal getCuota() {
		return cuota;
	}


	public void setCuota(BigDecimal cuota) {
		this.cuota = cuota;
	}


	public Short getCantCuotas() {
		return cantCuotas;
	}


	public void setCantCuotas(Short cantCuotas) {
		this.cantCuotas = cantCuotas;
	}


	public String getCancelPrstNros() {
		return cancelPrstNros;
	}


	public void setCancelPrstNros(String cancelPrstNros) {
		this.cancelPrstNros = cancelPrstNros;
	}


	public BigDecimal getImporteNeto() {
		return importeNeto;
	}


	public void setImporteNeto(BigDecimal importeNeto) {
		this.importeNeto = importeNeto;
	}


	public Boolean getEnviadaAfondo() {
		return enviadaAfondo;
	}


	public void setEnviadaAfondo(Boolean enviadaAfondo) {
		this.enviadaAfondo = enviadaAfondo;
	}


	public Boolean getProcesada() {
		return procesada;
	}


	public void setProcesada(Boolean procesada) {
		this.procesada = procesada;
	}


	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


	public Boolean getEnviadaAComision() {
		return enviadaAComision;
	}


	public void setEnviadaAComision(Boolean enviadaAComision) {
		this.enviadaAComision = enviadaAComision;
	}


	public Boolean getAprobada() {
		return aprobada;
	}


	public void setAprobada(Boolean aprobada) {
		this.aprobada = aprobada;
	}


	public Boolean getRechazada() {
		return rechazada;
	}


	public void setRechazada(Boolean rechazada) {
		this.rechazada = rechazada;
	}


	public Boolean getAprobada2() {
		return aprobada2;
	}


	public void setAprobada2(Boolean aprobada2) {
		this.aprobada2 = aprobada2;
	}


	public Boolean getRechazada2() {
		return rechazada2;
	}


	public void setRechazada2(Boolean rechazada2) {
		this.rechazada2 = rechazada2;
	}
	
}
