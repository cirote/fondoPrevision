package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.util.List;

import org.mercosur.fondoPrevision.entities.Gcargo;

public interface GcargoService {

	public List<Gcargo> getAllGcargos();
	public void calculoDeAumento(BigDecimal pct) throws Exception;
	
	public void sumarBasicoAndComplemento() throws Exception;

	public List<Gcargo> getAllByUnidad(String unidad) throws Exception;
}
