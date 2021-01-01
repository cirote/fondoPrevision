package org.mercosur.fondoPrevision.service;

import org.mercosur.fondoPrevision.dto.EstadoDeCta;

public interface EstadoDeCtaService {

	public EstadoDeCta getEstadoDeCtabyFuncionario(Long idfuncionario) throws Exception;

}
