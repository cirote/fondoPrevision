package org.mercosur.fondoPrevision.repository;

import java.util.List;

import org.mercosur.fondoPrevision.entities.Movimientos;

public interface MovimientosRepositoryCustom {

	public Iterable<Movimientos> getByCodigoMovAndMes(Short codmov, String mesliquidacion) throws Exception;
	public List<Movimientos> getByCodigosAndMes(String lstcod, String mesliquidacion) throws Exception;
	public List<Movimientos> getSumasByCodigosAndMes(String lstcod, String mesliquidacion) throws Exception;
	public Movimientos getLastByFunc(Integer tarjeta) throws Exception;
	public Movimientos getLastByFuncAndMesliquidacion(Integer tarjeta, String mesliquidacion) throws Exception;
	public Movimientos getLastByFuncAndTipo(Integer tarjeta, Integer idtipo) throws Exception;
	public List<Movimientos> getByFuncAndPeriodo(Long idfuncionario, String mesdesde, String meshasta) throws Exception;
	public List<String> getMesesLiquidacion();
	public List<String> getMesesLiquidacionDesc();
	public Long getCountPorAnioMes(String mesliquidacion, Integer idTipoMov) throws Exception;
	public void deleteAllByTarjeta(Integer tarjeta) throws Exception;
	public void deleteAll() throws Exception;
}
