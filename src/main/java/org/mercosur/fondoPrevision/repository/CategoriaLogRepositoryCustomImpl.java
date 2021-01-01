package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.mercosur.fondoPrevision.entities.CategoriaLog;

@Repository
@Transactional
public class CategoriaLogRepositoryCustomImpl implements CategoriaLogRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<CategoriaLog> getSomeLikeDesc(String descripcion) throws Exception {
		return entityManager.createQuery("select cl from CategoriaLog cl where cl.descripcion like '%" + descripcion + "%'").getResultList();
	}

}
