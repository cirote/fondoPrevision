package org.mercosur.fondoPrevision.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Role;
import org.mercosur.fondoPrevision.entities.User;

public class CuentaNuevaForm {

	private BigDecimal basico;
	
	private BigDecimal complemento;
	
	private Gplanta funcionario;
	
	private User usuario;
	
	private Set<Role> roles = new HashSet<Role>();

	private Integer idcargo;
	
	private Integer idorganigrama;
	
	public CuentaNuevaForm() {
		
	}


	public Gplanta getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Gplanta funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getIdcargo() {
		return idcargo;
	}

	public void setIdcargo(Integer idcargo) {
		this.idcargo = idcargo;
	}


	public BigDecimal getComplemento() {
		return complemento;
	}


	public void setComplemento(BigDecimal complemento) {
		this.complemento = complemento;
	}


	public User getUsuario() {
		return usuario;
	}


	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}


	public BigDecimal getBasico() {
		return basico;
	}


	public void setBasico(BigDecimal basico) {
		this.basico = basico;
	}


	public Integer getIdorganigrama() {
		return idorganigrama;
	}


	public void setIdorganigrama(Integer idorganigrama) {
		this.idorganigrama = idorganigrama;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
