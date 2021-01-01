package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.DatosDistribucion;

public interface DatosDistribucionRepositoryCustom {

	DatosDistribucion getPorMesDistribucion(String mesDistrib) throws Exception;
	
	String getUltimoMesDistrib() throws Exception;
}
