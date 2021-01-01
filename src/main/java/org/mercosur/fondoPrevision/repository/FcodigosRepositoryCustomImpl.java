package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.mercosur.fondoPrevision.entities.Fcodigos;

@Repository
@Transactional
public class FcodigosRepositoryCustomImpl implements FcodigosRepositoryCustom{

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Fcodigos> getCodInList(String list) {
		
		return entityManager.createQuery("from Fcodigos fc where " +
				"fc.codigo IN (" + list + ")").getResultList();
	}

}
