package org.mercosur.fondoPrevision.service;

import org.mercosur.fondoPrevision.dto.CierreCtaForm;
import org.mercosur.fondoPrevision.dto.EstadoDeCtaCierre;

public interface CierreCuentaService {

	public EstadoDeCtaCierre cerrarCuenta(CierreCtaForm form) throws Exception;
	
	public Boolean consolidarCierredeCuenta(EstadoDeCtaCierre estadoCta) throws Exception;
	
}
