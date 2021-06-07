package org.mercosur.fondoPrevision.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.exceptions.FmovimientosException;

@Repository
@Transactional
public class MovimientosRepositoryCustomImpl implements MovimientosRepositoryCustom{
	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Movimientos> getByCodigoMovAndMes(Short codmov, String mesliquidacion) throws Exception {
		return entityManager.createQuery("select m from Movimientos m where m.codigoMovimiento =:tipo and " +
				"m.mesliquidacion =:mes order by m.funcionario.tarjeta")
				.setParameter("tipo", codmov)
				.setParameter("mes", mesliquidacion)
				.getResultList();
	}

	@Override
	public Movimientos getLastByFunc(Integer tarjeta) throws Exception {
		try{
			Long id = (Long)entityManager.createQuery("select Max(fmov.idfmovimientos) from Movimientos fmov " + 
					"where fmov.tarjeta =:tar")
					.setParameter("tar", tarjeta)
					.getSingleResult();
			
			return  (Movimientos) entityManager.createQuery("from Movimientos fm " +
					"where fm.idfmovimientos =:id")
					.setParameter("id", id)
					.getSingleResult();
		}
		catch(Exception ex){
			throw new FmovimientosException("No fue posible obtener el último movimiento del funcionario");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimientos> getByCodigosAndMes(String lstcod, String mesliquidacion) throws Exception {
		return entityManager.createQuery("select m from Movimientos m where m.codigoMovimiento IN (" + lstcod +
				") and m.mesliquidacion =:mes order by m.tarjeta")
				.setParameter("mes", mesliquidacion)
				.getResultList();
	}

	@Override
	public Movimientos getLastByFuncAndTipo(Integer tarjeta, Integer idtipo) throws Exception {
		try{
			Long id = (Long)entityManager.createQuery("select Max(fmov.idfmovimientos) from Movimientos fmov " + 
					"where fmov.tarjeta =:tar and fmov.tipoMovimiento.idftipomovimiento =:idtipo")
					.setParameter("tar", tarjeta)
					.setParameter("idtipo", idtipo)
					.getSingleResult();
			
			return  (Movimientos) entityManager.createQuery("from Movimientos fm " +
					"where fm.idfmovimientos =:id")
					.setParameter("id", id)
					.getSingleResult();
		}
		catch(Exception ex){
			throw new FmovimientosException("No fue posible obtener el último movimiento del funcionario");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimientos> getByFuncAndPeriodo(Long idfuncionario, String mesdesde, String meshasta) throws Exception {
		List<Movimientos> lstRet = entityManager.createQuery("select m from Movimientos m where m.funcionario.idgplanta =:id and m.mesliquidacion >= :mesdesde " + 
		" and m.mesliquidacion <= :meshasta order by m.fechaMovimiento")
			.setParameter("id", idfuncionario)
			.setParameter("mesdesde", mesdesde)
			.setParameter("meshasta", meshasta)
			.getResultList();
		return lstRet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMesesLiquidacion() {
		List<String> lst = entityManager.createQuery("select distinct m.mesliquidacion from Movimientos m").getResultList();
		return lst;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMesesLiquidacionDesc() {
		List<String> lst = entityManager.createQuery("select distinct m.mesliquidacion from Movimientos m order by m.mesliquidacion desc").getResultList();
		return lst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimientos> getSumasByCodigosAndMes(String lstcod, String mesliquidacion) throws Exception {
		List<Movimientos> lstMovs = entityManager.createQuery("select new org.mercosur.fondoPrevision.entities.Movimientos(m.tarjeta, " + 
				"sum(m.importeCapSec), sum(m.importeIntFunc), sum(importeMov)) from Movimientos m where " + 
				"m.codigoMovimiento IN (" + lstcod + ") and m.mesliquidacion =:mes group by m.tarjeta")
				.setParameter("mes", mesliquidacion)
				.getResultList();
		return lstMovs;
	}

	@Override
	public Long getCountPorAnioMes(String mesliquidacion, Integer idTipoMov) throws Exception {
		
		return (Long) entityManager.createQuery("select COUNT(*) from Movimientos m where m.mesliquidacion =:mes and " + 
		"m.tipoMovimiento.idftipomovimiento =:idmov").setParameter("mes", mesliquidacion).setParameter("idmov", idTipoMov).getSingleResult();
	}

	@Override
	public void deleteAllByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("delete from Movimientos m where m.tarjeta =:tarjeta")
		.setParameter("tarjeta", tarjeta).executeUpdate();
		
	}

	@Override
	public void deleteAll() throws Exception {
		entityManager.createQuery("delete from Movimientos m").executeUpdate();
		
	}

	@Override
	public Movimientos getLastByFuncAndMesliquidacion(Integer tarjeta, String mesliquidacion) throws Exception {
		try{
			Long id = (Long)entityManager.createQuery("select Max(fmov.idfmovimientos) from Movimientos fmov " + 
					"where fmov.tarjeta =:tar and fmov.mesliquidacion =:mesliquidacion")
					.setParameter("tar", tarjeta)
					.setParameter("mesliquidacion", mesliquidacion)
					.getSingleResult();
			
			return  (Movimientos) entityManager.createQuery("from Movimientos fm " +
					"where fm.idfmovimientos =:id")
					.setParameter("id", id)
					.getSingleResult();
		}
		catch(Exception ex){
			throw new FmovimientosException("No fue posible obtener el último movimiento del funcionario");
		}
	}
}
