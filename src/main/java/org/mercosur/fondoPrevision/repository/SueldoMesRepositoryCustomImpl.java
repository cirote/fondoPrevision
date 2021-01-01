package org.mercosur.fondoPrevision.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mercosur.fondoPrevision.entities.SueldoMes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SueldoMesRepositoryCustomImpl implements SueldoMesRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<SueldoMes> getAllByMesliquidacion(String mesliquidacion) throws Exception {
		return entityManager.createQuery("select sm from SueldoMes sm where sm.mesliquidacion =:mesl")
				.setParameter("mesl", mesliquidacion)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SueldoMes> getByPeriodoAndTarjeta(String aniomes1, String aniomes2, Integer tarjeta) throws Exception {
		return entityManager.createQuery("select sm from SueldoMes sm where sm.tarjeta =:tarjeta and sm.aniomes >= :aniomes1 " +
				" and sm.aniomes <=:aniomes2")
				.setParameter("tarjeta", tarjeta)
				.setParameter("aniomes1", aniomes1)
				.setParameter("aniomes2", aniomes2)
				.getResultList();
	}

	@Override
	public void deleteByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("delete from SueldoMes sm where sm.tarjeta =:tarjeta")
			.setParameter("tarjeta", tarjeta)
			.executeUpdate();
		
	}

}
