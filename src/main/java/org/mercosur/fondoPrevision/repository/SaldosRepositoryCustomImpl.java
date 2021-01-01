package org.mercosur.fondoPrevision.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.mercosur.fondoPrevision.entities.Saldos;

@Repository
@Transactional
public class SaldosRepositoryCustomImpl implements SaldosRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Saldos> getSaldosConsolidables() throws Exception {
		BigDecimal valor = BigDecimal.ZERO;
		return entityManager.createQuery("select s from Saldos s where s.capitalIntegAntes > :valor")
				.setParameter("valor", valor).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Saldos> getByMesLiquidacion(String mesliq) throws Exception {
		
		return entityManager.createQuery("select s from Saldos s where s.mesliquidacion =:mes")
				.setParameter("mes", mesliq)
				.getResultList();
	}

	@Override
	public String getMesLiquidacion() {
		return (String) entityManager.createQuery("select distinct s.mesliquidacion from Saldos s").getSingleResult();
	}

	@Override
	public void deleteByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("delete from Saldos s where s.tarjeta =:tarjeta").setParameter("tarjeta", tarjeta).executeUpdate();
	}

}
