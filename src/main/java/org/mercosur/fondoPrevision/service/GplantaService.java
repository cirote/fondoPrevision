package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.mercosur.fondoPrevision.dto.FuncionarioConSueldoMes;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.exceptions.FuncionarioNoEncontradoException;

public interface GplantaService {

	@SuppressWarnings("rawtypes")
	public Iterable getAllPlanta();
	
	public Gplanta getFuncionarioByTarjeta(Integer tarjeta) throws FuncionarioNoEncontradoException;
	
	public FuncionarioConSueldoMes getFuncionarioConSueldoMes(Integer tarjeta) throws FuncionarioNoEncontradoException;
	
	public Boolean checkFuncionarioByTarjeta(Integer tarjeta) throws FuncionarioNoEncontradoException;
	
	public Long getIdGplantaByTarjeta(Integer tarjeta) throws FuncionarioNoEncontradoException;
	
	public Gplanta createCuenta(Gplanta funcionario, Integer idgrado, Integer idgorganigrama, BigDecimal sueldoMes, BigDecimal complemento) throws Exception;
	
	public String crearCuentaDeFuncionarioDePlanta(Gplanta funcionario) throws Exception;
	
	public Gplanta actualizarCargo(Long idfuncionario, Integer idcargonuevo, String fechaAcceso, 
			String firstBasico, String firstComplemento) throws Exception;
	
	public void actualizarUltimosIngresos() throws Exception;
	
	public List<Integer> getTarjetasIngresos(Date fecha) throws Exception;
	
	public List<Gplanta> getAllByUnidad(String unidad) throws Exception;
	
	public Integer getLastTarjeta() throws Exception;
	
}
