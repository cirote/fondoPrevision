package org.mercosur.fondoPrevision.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.mercosur.fondoPrevision.entities.SaldosHistoria;

public interface SaldosHistoriaRepositoryCustom {

	public Optional<String> getMaxMesLiquidacion();

	public Iterable<SaldosHistoria> getSaldosHisByTarjeta(Integer tarjeta);
	
	public List<SaldosHistoria> getByTarjetaAndMesliquidacion(Integer tarjeta, String mesliquidacion);
	
	public SaldosHistoria getLastByTarjetaAndMesliquidacion(Integer tarjeta, String mesliquidacion);
	
	public SaldosHistoria getByTarjetaAndMesDistribucion(Integer tarjeta, String mesliquidacion);
	
	public SaldosHistoria getUltimoByTarjeta(Integer tarjeta) throws Exception;
	
	public void deleteByMesliquidacion(String mesliquidacion) throws Exception;
	
	public void deleteByTarjeta(Integer tarjeta) throws Exception;
	
	public BigDecimal numeralesFuncionario(String anioMes2,
			Integer tarjeta) throws Exception;
			
	public BigDecimal totalNumeralesPorTarjetasConDistribucion(String aniomes, String tarjetas) throws Exception;
		
	public BigDecimal totalNumeralesPorTajetasSinDistribucion(String aniomes, String tarjetas) throws Exception;
	
	public BigDecimal totalNumeralesConDistribucion(String aniomes) throws Exception;
	
	public BigDecimal totalNumeralesSinDistribucion(String aniomes) throws Exception;
	
	public List<Integer> getTarjetasEgresosByMesliquidacion(String mesliquidacion) throws Exception;
}
