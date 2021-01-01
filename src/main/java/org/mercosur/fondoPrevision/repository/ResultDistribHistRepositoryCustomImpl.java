package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ResultDistribHistRepositoryCustomImpl implements ResultDistribHistRepositoryCustom{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void insertByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("insert into ResultDistribHist (gplanta_id, tarjeta, fcodigos_id, fecha, mesLiquidacion, " + 
				"mesInicial, mesFinal, numeralesFuncionario, numeralesTodos, totalAdistribuir, pctFuncionario, " + 
				"distribucionFuncionario) select rd.funcionario.idgplanta, rd.tarjeta, rd.fcodigos.idfcodigos, " +
				"rd.fecha, rd.mesLiquidacion, rd.mesInicial, rd.mesFinal, rd.numeralesFuncionario, rd.numeralesTodos, " +
				"rd.totalADistribuir, rd.pctFuncionario, rd.distribucionFuncionario from ResultadoDistribucion rd " +
				"where tarjeta =:tarjeta")
			.setParameter("tarjeta", tarjeta)
			.executeUpdate();
		
	}
	
}

