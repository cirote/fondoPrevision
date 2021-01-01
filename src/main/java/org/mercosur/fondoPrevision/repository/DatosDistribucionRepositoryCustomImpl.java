package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.DatosDistribucion;

@Repository
public class DatosDistribucionRepositoryCustomImpl implements DatosDistribucionRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public DatosDistribucion getPorMesDistribucion(String mesDistrib) throws Exception {
		return (DatosDistribucion) entityManager.createQuery("select dd from DatosDistribucion dd where dd.mesDistrib =:mesD")
				.setParameter("mesD", mesDistrib)
				.getSingleResult();
	}

	@Override
	public String getUltimoMesDistrib() throws Exception {
		return (String) entityManager.createQuery("select Max(dd.mesDistrib) from DatosDistribucion dd").getSingleResult();
	}

}
