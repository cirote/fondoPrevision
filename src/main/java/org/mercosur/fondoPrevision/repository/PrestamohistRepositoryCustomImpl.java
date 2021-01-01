package org.mercosur.fondoPrevision.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mercosur.fondoPrevision.entities.Prestamohist;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PrestamohistRepositoryCustomImpl implements PrestamohistRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Prestamohist> findPrstCancelados() throws Exception {
		BigDecimal saldo = BigDecimal.ZERO;
		return entityManager.createQuery("select ph from Prestamohist ph where ph.saldoPrestamo > :saldo")
				.setParameter("saldo", saldo)
				.getResultList();
	}


	@Override
	public void insertByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("insert into Prestamohist (cantCuotas, capitalPrestamo, codigoPrestamo, cuota, " +
				"cuotasPagas, fechaPrestamo, fechaSaldo, ftipoprestamo_id, gplanta_id, interesPrestamo, nroprestamo, " +
				"prestamoNuevo, saldoPrestamo, tarjeta) " +
				"select p.cantCuotas, p.capitalPrestamo, p.codigoPrestamo, p.cuota, p.cuotasPagas, p.fechaPrestamo, " +
				"p.fechaSaldo, p.tipoPrestamo.idftipoprestamo, p.funcionario.idgplanta, p.interesPrestamo, p.nroprestamo, " +
				"p.prestamoNuevo, p.saldoPrestamo, p.tarjeta from Prestamo p where p.tarjeta =:tarjeta")
			.setParameter("tarjeta", tarjeta)
			.executeUpdate();
	}

}
