package org.mercosur.fondoPrevision.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mercosur.fondoPrevision.entities.GvinculoFuncionarioCargo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GvinculoFuncionarioCargoRepositoryCustomImpl implements GvinculoFuncionarioCargoRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GvinculoFuncionarioCargo> getAllByTarjeta(Integer tarjeta) throws Exception {
		return entityManager.createQuery("select vfc from GvinculoFuncionarioCargo vfc where vfc.tarjeta =:tarjeta")
				.setParameter("tarjeta", tarjeta)
				.getResultList();
	}

	@Override
	public GvinculoFuncionarioCargo getLastByTarjeta(Integer tarjeta) throws Exception {
		
		return (GvinculoFuncionarioCargo) entityManager.createQuery("select vfc from GvinculoFuncionarioCargo vfc where vfc.tarjeta =:tarjeta and " +
				"vfc.fecha_final is null")
				.setParameter("tarjeta", tarjeta)
				.getSingleResult();
	}

	@Override
	public void deleteByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("delete from GvinculoFuncionarioCargo vfc where vfc.tarjeta =:tarjeta").setParameter("tarjeta", tarjeta)
			.executeUpdate();
	}

}
