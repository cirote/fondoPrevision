package org.mercosur.fondoPrevision.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
