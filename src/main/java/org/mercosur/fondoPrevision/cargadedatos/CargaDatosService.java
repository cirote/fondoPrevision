package org.mercosur.fondoPrevision.cargadedatos;

import java.util.List;

import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.SaldoPrestamosAcum;
import org.mercosur.fondoPrevision.entities.Saldos;
import org.mercosur.fondoPrevision.entities.SueldoMes;

public interface CargaDatosService {

	public List<Saldos> cargaSaldosDesde(String nomarchivo)throws Exception;
	
	public List<SueldoMes> cargaSueldosDesde(String nomarchivo)throws Exception;
	
	public List<Movimientos> cargaMovimientos(String nomarchivo) throws Exception;
	
	public List<Prestamo> cargaPrestamos(String nomarchivo) throws Exception;
	
	public List<SaldoPrestamosAcum> actualizaSaldosPrst() throws Exception;
	
	public List<Gplanta> creaVinculoConCargo() throws Exception;
}
