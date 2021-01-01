package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SueldoMesHistRepositoryCustomImpl implements SueldoMesHistRepositoryCustom{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void insertByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("insert into SueldoMesHist (gplanta_id, tarjeta, aniomes, fecha, sueldomes, complemento, " +
				"aguinaldoBasico, aguinaldoComplemento, motivo) select sm.funcionario.idgplanta, sm.tarjeta, sm.aniomes, " +
				"sm.fecha, sm.sueldomes, sm.complemento, sm.aguinaldoBasico, sm.aguinaldoComplemento, sm.motivo from SueldoMes sm " +
				"where sm.tarjeta =:tarjeta")
		.setParameter("tarjeta", tarjeta)
		.executeUpdate();
	}

}
