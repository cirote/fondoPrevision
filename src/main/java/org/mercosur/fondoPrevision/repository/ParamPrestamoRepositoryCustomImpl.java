package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ParamPrestamoRepositoryCustomImpl implements ParamPrestamoRepositoryCustom{

	@PersistenceContext
	@Autowired
	EntityManager entityManager;
	
	@Override
	public String pasajeAhistoricoParametros() throws Exception {
		entityManager.joinTransaction();
		entityManager.createQuery("insert into ParamPrestamoHist (mesliquidacion, descripcion, " +
				"meses, tasa) select p.mesliquidacion, p.descripcion, p.meses, p.tasa from " + 
				"ParamPrestamo p").executeUpdate();
		return "success";
	}

	@Override
	public String actualizaMesLiquidacion(String mesLiq) throws Exception {
		entityManager.joinTransaction();
		entityManager.createQuery("update ParamPrestamo p set p.mesliquidacion = :ml")
			.setParameter("ml", mesLiq).executeUpdate();
			
		return "success";
	}

}
