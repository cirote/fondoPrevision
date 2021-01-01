package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.mercosur.fondoPrevision.entities.Parametro;
import org.mercosur.fondoPrevision.exceptions.ParamNotFoundException;

@Repository
@Transactional
public class ParametroRepositoryCustomImpl implements ParametroRepositoryCustom{

	@PersistenceContext 
	EntityManager entityManager;
	
	@Override
	public String pasajeAhistoricoParametros() throws Exception {
		entityManager.joinTransaction();
		entityManager.createQuery("insert into Parametrohist (mesliquidacion, descripcion, " +
				"valor, simbolo) select p.mesliquidacion, p.descripcion, p.valor, p.simbolo from " + 
				"Parametro p").executeUpdate();
		return "success";
	}

	@Override
	public String actualizaMesLiquidacion(String mesLiq) throws Exception {
		entityManager.joinTransaction();
		entityManager.createQuery("update Parametro p set p.mesliquidacion = :ml")
			.setParameter("ml", mesLiq).executeUpdate();
			
		return "success";
	}

	@Override
	@Transactional(readOnly=true)
	public String findByMesliquidacionDistinct() {
		
		return (String)entityManager.createQuery("select distinct p.mesliquidacion from Parametro p ").getResultList().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Parametro> getSomeByDesc(String descrip) throws Exception {
		return entityManager.createQuery("select p from Parametro p where p.descripcion like '%" + descrip + "%'").getResultList();
	}

	@Override
	public Parametro getOneByDesc(String descrip) throws ParamNotFoundException {
		return (Parametro) entityManager.createQuery("select p from Parametro p where p.descripcion like '%" + descrip + "%'").getSingleResult();
	}

}
