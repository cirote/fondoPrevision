package org.mercosur.fondoPrevision.service;

import java.util.List;

import org.mercosur.fondoPrevision.dto.CapitalesForDisplay;

public interface ConsultaCapitalesService {

	List<CapitalesForDisplay> getCapitalesByMesliquidacion(String mesliquidacion) throws Exception;
	
	List<CapitalesForDisplay> realizarAjuste(CapitalesForDisplay capfordisplay) throws Exception;
}
