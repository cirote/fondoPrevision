package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.util.List;

import org.mercosur.fondoPrevision.dto.ResultadoDistribSummary;
import org.mercosur.fondoPrevision.entities.DatosDistribucion;

public interface DistribucionUtilidadesService {

	public DatosDistribucion registrarDatos(String mesdistrib) throws Exception;
	
	public DatosDistribucion salvarDatosDistribucion(String mesFinejercicio, BigDecimal sumaDistrib,
			BigDecimal sumaNumerales) throws Exception;
	
	public DatosDistribucion getDatosDistribucion(String mesDistribucion) throws Exception;
	
	public List<ResultadoDistribSummary> distribuirUtilidades(
			String tarjetasQL, String anioMes1, String anioMes2,
			DatosDistribucion datos) throws Exception;	
	
	public Boolean distribucionRealizada(String mesDistrib) throws Exception;

	public BigDecimal getSumaNumeralesPeriodoyTarjetas(String mes2, String tarjetas) throws Exception;
	
	public BigDecimal getSumaNumeralesSinDistribucionPorTarjetas(String aniomes, String tarjetas) throws Exception;
	
	public BigDecimal getSumaNumeralesConDistribucionPorTarjetas(String aniomes, String tarjetas) throws Exception;
	
	public BigDecimal getSumaNumeralesSinDistribucion(String aniomes) throws Exception;
	
	public BigDecimal getSumaNumeralesConDistribucion(String aniomes) throws Exception;
	
	
	public List<String> getMesesDistribucion() throws Exception;
	
	public List<ResultadoDistribSummary> obtenerResultados(String mesDistribucion);
	
	public BigDecimal getSumaADistribuir(String mesDistrib) throws Exception;
}
