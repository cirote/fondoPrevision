package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SaldoPrestamosAcumRepositoryCustomImpl implements SaldoPrestamosAcumRepositoryCustom{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void deleteByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("delete from SaldoPrestamosAcum sp where sp.tarjeta =:tarjeta")
		.setParameter("tarjeta", tarjeta)
		.executeUpdate();
		
	}

}
