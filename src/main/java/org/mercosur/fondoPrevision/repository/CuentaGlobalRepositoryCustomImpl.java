package org.mercosur.fondoPrevision.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.CuentaGlobal;

@Repository
public class CuentaGlobalRepositoryCustomImpl implements CuentaGlobalRepositoryCustom{

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal sumaMensual(String mesliquidacion) throws Exception {
		
		List<BigDecimal> lstimportes = entityManager.createQuery("select cg.importe from CuentaGlobal cg where cg.mesliquidacion =:mes")
				.setParameter("mes", mesliquidacion)
				.getResultList();
		BigDecimal suma = lstimportes.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		return suma;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CuentaGlobal> getAllByYear(String desde) throws Exception {
		return entityManager.createQuery("select cg from CuentaGlobal cg where cg.mesliquidacion >=:aniomes")
				.setParameter("aniomes", desde)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllMesliquidacionByYear(String desde) throws Exception {
		return entityManager.createQuery("select distinct cg.mesliquidacion from CuentaGlobal cg where cg.mesliquidacion >=:aniomes")
				.setParameter("aniomes", desde)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllMesliquidacion() throws Exception {
		return entityManager.createQuery("select distinct cg.mesliquidacion from CuentaGlobal cg").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CuentaGlobal> getDetalleMesLiquidacion(String mesliquidacion) throws Exception {
		return entityManager.createQuery("select cg from CuentaGlobal cg where cg.mesliquidacion =:mesliq")
				.setParameter("mesliq", mesliquidacion)
				.getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal sumaAnual(String anio) throws Exception {
		
		List<BigDecimal> lstimportes = entityManager.createQuery("select cg.importe from CuentaGlobal cg where substring(cg.mesliquidacion,1,4) =:anio")
				.setParameter("anio", anio)
				.getResultList();
		
		return lstimportes.stream().reduce(BigDecimal.ZERO,  BigDecimal::add);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal sumaPeriodo(String mesinicio, String mesfin) throws Exception {
		
		List<BigDecimal> lstimportes = entityManager.createQuery("select cg.importe from CuentaGlobal cg where cg.mesliquidacion >= :aniomesini " + 
				"and cg.mesliquidacion <= :aniomesfin")
				.setParameter("aniomesini", mesinicio)
				.setParameter("aniomesfin", mesfin)
				.getResultList();
		
		return lstimportes.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal sumaPorPeriodoyTarjetas(String mesinicio, String mesfin, String tarjetasQL) throws Exception {
		List<BigDecimal> lstimportes = entityManager.createQuery("select cg.importe from CuentaGlobal cg where cg.mesliquidacion >= :aniomesini " + 
				"and cg.mesliquidacion <= :aniomesfin and cg.tarjeta NOT IN (\" + tarjetasQL + \")")
				.setParameter("aniomesini", mesinicio)
				.setParameter("aniomesfin", mesfin)
				.getResultList();
		
		return lstimportes.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
