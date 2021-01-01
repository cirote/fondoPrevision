package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.util.List;

import org.mercosur.fondoPrevision.dto.AportesSummary;
import org.mercosur.fondoPrevision.dto.FuncionarioConSueldoMes;
import org.mercosur.fondoPrevision.entities.Gplanta;

public interface CalculoAportesService {

	public Boolean controlDeEjecucion(String mesLiquidacion) throws Exception;
	
	public String calculoDeAportes(String mesLiquidacion) throws Exception;
	
	public Boolean controlDeEjecucionAguinaldo(String mesliquidacion) throws Exception;
	
	public String calculoDeAportesAguinaldo(String aniomes1, String aniomes2) throws Exception;
	
	public List<AportesSummary> obtenerResultadosAportes(String mesliquidacion) throws Exception;

	public List<AportesSummary> obtenerResultadosAportesAguinaldo(String mesliquidacion) throws Exception;
	
	public List<AportesSummary> obtenerResultadosMensualYAguinaldo(String mesliquidacion) throws Exception;
	
	public List<FuncionarioConSueldoMes> getAllFuncionarios() throws Exception;
	
	public List<FuncionarioConSueldoMes> getAllFuncionariosByUnidad(String unidad) throws Exception;
	
	public Boolean actualizacionBasicoyComplemento(Gplanta funcionario, String mesLiquidacion, BigDecimal basico, BigDecimal complemento) throws Exception;
}
