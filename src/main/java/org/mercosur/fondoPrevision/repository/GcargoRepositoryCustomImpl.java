package org.mercosur.fondoPrevision.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mercosur.fondoPrevision.entities.Gcargo;
import org.springframework.stereotype.Repository;

@Repository
public class GcargoRepositoryCustomImpl implements GcargoRepositoryCustom{

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Gcargo> getAllByUnidad(String unidad) throws Exception {
		return entityManager.createQuery("select gc from Gcargo gc where gc.unidad =:unidad")
				.setParameter("unidad", unidad)
				.getResultList();
	}

}
