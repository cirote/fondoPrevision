package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mercosur.fondoPrevision.entities.Movimientos;
import org.mercosur.fondoPrevision.entities.MovimientosHist;
import org.mercosur.fondoPrevision.exceptions.FmovimientosException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MovimientosHistRepositoryCustomImpl implements MovimientosHistRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void insertAllByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("insert into MovimientosHist (codigoMovimiento, fechaMovimiento, " +
				"idFTipoMovimiento, idGPlanta, importeCapSec, importeIntFunc, importeMov, " +
				"mesliquidacion, nroCuota, nroPrestamo, observaciones, saldoActual, saldoAnterior, tarjeta) " +
				"select fm.codigoMovimiento, fm.fechaMovimiento, fm.tipoMovimiento.idftipomovimiento, fm.funcionario.idgplanta, " +
				"fm.importeCapSec, fm.importeIntFunc, fm.importeMov, fm.mesliquidacion, fm.nroCuota, " +
				"fm.nroPrestamo, fm.observaciones, fm.saldoActual, fm.saldoAnterior, fm.tarjeta " +
				"from Movimientos fm where fm.tarjeta =:tarjeta")
				.setParameter("tarjeta", tarjeta).executeUpdate();
	}

	@Override
	public void insertAllFromMovs() throws Exception {
		entityManager.createQuery("insert into MovimientosHist (codigoMovimiento, fechaMovimiento, " +
				"idFTipoMovimiento, idGplanta, importeCapSec, importeIntFunc, importeMov, " +
				"mesliquidacion, nroCuota, nroPrestamo, observaciones, saldoActual, saldoAnterior, tarjeta) " +
				"select fm.codigoMovimiento, fm.fechaMovimiento, fm.tipoMovimiento.idftipomovimiento, fm.funcionario.idgplanta, " +
				"fm.importeCapSec, fm.importeIntFunc, fm.importeMov, fm.mesliquidacion, fm.nroCuota, " +
				"fm.nroPrestamo, fm.observaciones, fm.saldoActual, fm.saldoAnterior, fm.tarjeta " +
				"from Movimientos fm").executeUpdate();
	}

	@Override
	public MovimientosHist getLastByFuncAndTipo(Integer tarjeta, Integer idtipo) throws Exception {
		try{
			Long id = (Long)entityManager.createQuery("select Max(fmov.idmovimientoshist) from MovimientosHist fmov " + 
					"where fmov.tarjeta =:tar and fmov.idFTipoMovimiento =:idtipo and fmov.observaciones not like '%Cierre de Cuenta%'")
					.setParameter("tar", tarjeta)
					.setParameter("idtipo", idtipo)
					.getSingleResult();
			
			return  (MovimientosHist) entityManager.createQuery("from MovimientosHist fm " +
					"where fm.idmovimientoshist =:id")
					.setParameter("id", id)
					.getSingleResult();
		}
		catch(Exception ex){
			throw new FmovimientosException("No fue posible obtener el último movimiento del funcionario");
		}
	}

}
