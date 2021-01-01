package org.mercosur.fondoPrevision.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.mercosur.fondoPrevision.entities.ResultadoDistribucion;

@Repository
@Transactional
public class ResultadoDistribucionRepositoryCustomImpl implements ResultadoDistribucionRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultadoDistribucion> getByMesDistrib(String aniomes) {
		List<ResultadoDistribucion> lst = entityManager.createQuery("from ResultadoDistribucion frd " +
				"where frd.mesLiquidacion =:mes")
				.setParameter("mes", aniomes)
				.getResultList();
		
		return lst;
	}

	@SuppressWarnings("unchecked")
	public BigDecimal getTotNumerales(String anioMes) {
		List<BigDecimal> valores = entityManager.createQuery("select frd.numeralesFuncionario " +
				"from ResultadoDistribucion frd where frd.mesFinal =:mes")
				.setParameter("mes", anioMes)
				.getResultList();
		return valores.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@SuppressWarnings("unchecked")
	public BigDecimal getTotIntereses(String anioMes) {
		List<BigDecimal> importes = entityManager.createQuery("select frd.distribucionFuncionario " +
				"from ResultadoDistribucion frd where frd.mesFinal =:mes")
				.setParameter("mes", anioMes)
				.getResultList();
						
		return importes.stream().reduce(BigDecimal.ZERO,  BigDecimal::add);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMesesDistribucion() {
		
		return entityManager.createQuery("select distinct rd.mesLiquidacion from ResultadoDistribucion rd")
				.getResultList();
	}

	@Override
	public BigDecimal getSumaADistrib(String anioMes) throws Exception {
		BigDecimal total =  (BigDecimal) entityManager.createQuery("select distinct rd.totalADistribuir from ResultadoDistribucion rd " +
				"where rd.mesLiquidacion =:mes")
				.setParameter("mes", anioMes)
				.getSingleResult();
		return total;
	}

	@Override
	public void deleteByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("delete from ResultadoDistribucion rd where rd.tarjeta =:tarjeta")
			.setParameter("tarjeta", tarjeta)
			.executeUpdate();
		
	}


}
