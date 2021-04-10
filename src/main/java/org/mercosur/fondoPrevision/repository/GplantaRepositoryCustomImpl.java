package org.mercosur.fondoPrevision.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.mercosur.fondoPrevision.entities.Gplanta;

@Repository
@Transactional
public class GplantaRepositoryCustomImpl implements GplantaRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Gplanta> getFuncsNotInGroup(String tarjetas) throws Exception {

		List<Gplanta> lstGroup = new ArrayList<Gplanta>();
		if(tarjetas.equals("")){
			lstGroup = entityManager.createQuery("from Gplanta gp where gp.egreso is null order by gp.tarjeta").getResultList();
		}
		else{
			lstGroup = entityManager.createQuery("from Gplanta gp where " +
				"gp.tarjeta NOT IN (" + tarjetas + ") order by gp.tarjeta").getResultList();
		}
		Iterable<Gplanta> grupo = lstGroup;
		return grupo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gplanta> getFuncsInGroup(String tarjetasQL) throws Exception {
		List<Gplanta> lstGroup = new ArrayList<Gplanta>();
		if(!tarjetasQL.isEmpty()) {
			lstGroup = entityManager.createQuery("from Gplanta gp where gp.tarjeta IN(" + tarjetasQL +") order by gp.tarjeta")
					.getResultList();			
		}
		
		return lstGroup;
	}

	@Override
	public void updateUltimosIngresos() throws Exception {
		try {
			entityManager.createQuery("update Gplanta gp set gp.ultimoIngreso = false where gp.ultimoIngreso = true")
				.executeUpdate();
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getTarjetasIngresos(Date fecha) throws Exception {
		List<Integer> lstTarjetas = entityManager.createQuery("select gp.tarjeta from Gplanta gp where gp.ingreso > :fecha")
									.setParameter("fecha", fecha)
									.getResultList();
		
		return lstTarjetas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gplanta> getAllByUnidad(String unidad) throws Exception {
		return entityManager.createQuery("select gp from Gplanta gp where gp.unidad =:unidad")
				.setParameter("unidad", unidad)
				.getResultList();
	}

	@Override
	public Integer getLastTarjeta() throws Exception {
		return (Integer) entityManager.createQuery("select Max(f.tarjeta) from Gplanta f").getSingleResult();
	}

	@Override
	public Boolean findTarjeta(Integer tarjeta) throws Exception {
		@SuppressWarnings("unused")
		Gplanta f =  (Gplanta) entityManager.createQuery("select f from Gplanta f where f.tarjeta =:tarjeta")
				.setParameter("tarjeta", tarjeta)
				.getSingleResult();
		return true;
	}

	@Override
	public void deleteByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("delete from Gplanta f where f.tarjeta =:tarjeta")
		.setParameter("tarjeta", tarjeta)
		.executeUpdate();
	}

}
