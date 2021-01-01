package org.mercosur.fondoPrevision.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fdatoscierrecta")
public class DatosCierreCta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3460418502738040626L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idfdatoscierrecta")
	private Long iddatoscierre;

	@Column
	private Long gplanta_id;
	
	@Column
	private Integer tarjeta;
	
	@Column
	private String nombre;
	
	@Column
	private LocalDate fechaEgreso;
	
	@Column
	private BigDecimal capitalIntegrado;
	
	@Column
	private BigDecimal haberesUltimoMes;
	
	@Column 
	private BigDecimal aporteHaberes;
	
	@Column
	private BigDecimal importeAguinaldo;
	
	@Column
	private BigDecimal aporteAguinaldo;
	
	@Column
	private BigDecimal saldoPrestamos;
	
	@Column
	private BigDecimal interesesUtilidades;
	
	@Column
	private BigDecimal saldoCuenta;
	
	@Column
	private String prstDescontados;
	
	public DatosCierreCta() {
		super();
	}

	public Long getIddatoscierre() {
		return iddatoscierre;
	}

	public void setIddatoscierre(Long iddatoscierre) {
		this.iddatoscierre = iddatoscierre;
	}

	public Long getGplanta_id() {
		return gplanta_id;
	}

	public void setGplanta_id(Long gplanta_id) {
		this.gplanta_id = gplanta_id;
	}

	public Integer getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Integer tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(LocalDate fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public BigDecimal getCapitalIntegrado() {
		return capitalIntegrado;
	}

	public void setCapitalIntegrado(BigDecimal capitalIntegrado) {
		this.capitalIntegrado = capitalIntegrado;
	}

	public BigDecimal getHaberesUltimoMes() {
		return haberesUltimoMes;
	}

	public void setHaberesUltimoMes(BigDecimal haberesUltimoMes) {
		this.haberesUltimoMes = haberesUltimoMes;
	}

	public BigDecimal getAporteHaberes() {
		return aporteHaberes;
	}

	public void setAporteHaberes(BigDecimal aporteHaberes) {
		this.aporteHaberes = aporteHaberes;
	}

	public BigDecimal getImporteAguinaldo() {
		return importeAguinaldo;
	}

	public void setImporteAguinaldo(BigDecimal importeAguinaldo) {
		this.importeAguinaldo = importeAguinaldo;
	}

	public BigDecimal getAporteAguinaldo() {
		return aporteAguinaldo;
	}

	public void setAporteAguinaldo(BigDecimal aporteAguinaldo) {
		this.aporteAguinaldo = aporteAguinaldo;
	}

	public BigDecimal getSaldoPrestamos() {
		return saldoPrestamos;
	}

	public void setSaldoPrestamos(BigDecimal saldoPrestamos) {
		this.saldoPrestamos = saldoPrestamos;
	}

	public BigDecimal getInteresesUtilidades() {
		return interesesUtilidades;
	}

	public void setInteresesUtilidades(BigDecimal interesesUtilidades) {
		this.interesesUtilidades = interesesUtilidades;
	}

	public BigDecimal getSaldoCuenta() {
		return saldoCuenta;
	}

	public void setSaldoCuenta(BigDecimal saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}

	public String getPrstDescontados() {
		return prstDescontados;
	}

	public void setPrstDescontados(String prstDescontados) {
		this.prstDescontados = prstDescontados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aporteAguinaldo == null) ? 0 : aporteAguinaldo.hashCode());
		result = prime * result + ((aporteHaberes == null) ? 0 : aporteHaberes.hashCode());
		result = prime * result + ((capitalIntegrado == null) ? 0 : capitalIntegrado.hashCode());
		result = prime * result + ((fechaEgreso == null) ? 0 : fechaEgreso.hashCode());
		result = prime * result + ((gplanta_id == null) ? 0 : gplanta_id.hashCode());
		result = prime * result + ((haberesUltimoMes == null) ? 0 : haberesUltimoMes.hashCode());
		result = prime * result + ((iddatoscierre == null) ? 0 : iddatoscierre.hashCode());
		result = prime * result + ((importeAguinaldo == null) ? 0 : importeAguinaldo.hashCode());
		result = prime * result + ((interesesUtilidades == null) ? 0 : interesesUtilidades.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((prstDescontados == null) ? 0 : prstDescontados.hashCode());
		result = prime * result + ((saldoCuenta == null) ? 0 : saldoCuenta.hashCode());
		result = prime * result + ((saldoPrestamos == null) ? 0 : saldoPrestamos.hashCode());
		result = prime * result + ((tarjeta == null) ? 0 : tarjeta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DatosCierreCta))
			return false;
		DatosCierreCta other = (DatosCierreCta) obj;
		if (aporteAguinaldo == null) {
			if (other.aporteAguinaldo != null)
				return false;
		} else if (!aporteAguinaldo.equals(other.aporteAguinaldo))
			return false;
		if (aporteHaberes == null) {
			if (other.aporteHaberes != null)
				return false;
		} else if (!aporteHaberes.equals(other.aporteHaberes))
			return false;
		if (capitalIntegrado == null) {
			if (other.capitalIntegrado != null)
				return false;
		} else if (!capitalIntegrado.equals(other.capitalIntegrado))
			return false;
		if (fechaEgreso == null) {
			if (other.fechaEgreso != null)
				return false;
		} else if (!fechaEgreso.equals(other.fechaEgreso))
			return false;
		if (gplanta_id == null) {
			if (other.gplanta_id != null)
				return false;
		} else if (!gplanta_id.equals(other.gplanta_id))
			return false;
		if (haberesUltimoMes == null) {
			if (other.haberesUltimoMes != null)
				return false;
		} else if (!haberesUltimoMes.equals(other.haberesUltimoMes))
			return false;
		if (iddatoscierre == null) {
			if (other.iddatoscierre != null)
				return false;
		} else if (!iddatoscierre.equals(other.iddatoscierre))
			return false;
		if (importeAguinaldo == null) {
			if (other.importeAguinaldo != null)
				return false;
		} else if (!importeAguinaldo.equals(other.importeAguinaldo))
			return false;
		if (interesesUtilidades == null) {
			if (other.interesesUtilidades != null)
				return false;
		} else if (!interesesUtilidades.equals(other.interesesUtilidades))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (prstDescontados == null) {
			if (other.prstDescontados != null)
				return false;
		} else if (!prstDescontados.equals(other.prstDescontados))
			return false;
		if (saldoCuenta == null) {
			if (other.saldoCuenta != null)
				return false;
		} else if (!saldoCuenta.equals(other.saldoCuenta))
			return false;
		if (saldoPrestamos == null) {
			if (other.saldoPrestamos != null)
				return false;
		} else if (!saldoPrestamos.equals(other.saldoPrestamos))
			return false;
		if (tarjeta == null) {
			if (other.tarjeta != null)
				return false;
		} else if (!tarjeta.equals(other.tarjeta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DatosCierreCta [iddatoscierre=" + iddatoscierre + ", gplanta_id=" + gplanta_id + ", tarjeta=" + tarjeta
				+ ", nombre=" + nombre + ", fechaEgreso=" + fechaEgreso + ", capitalIntegrado=" + capitalIntegrado
				+ ", haberesUltimoMes=" + haberesUltimoMes + ", aporteHaberes=" + aporteHaberes + ", importeAguinaldo="
				+ importeAguinaldo + ", aporteAguinaldo=" + aporteAguinaldo + ", saldoPrestamos=" + saldoPrestamos
				+ ", interesesUtilidades=" + interesesUtilidades + ", saldoCuenta=" + saldoCuenta + ", prstDescontados="
				+ prstDescontados + "]";
	}
	
	
}
