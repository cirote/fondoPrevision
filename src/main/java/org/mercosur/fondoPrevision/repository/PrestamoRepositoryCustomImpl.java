package org.mercosur.fondoPrevision.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.mercosur.fondoPrevision.entities.Prestamo;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class PrestamoRepositoryCustomImpl implements PrestamoRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public Optional<Integer> getUltimoNroPrst() {
		try {
			Optional<Object> ultimo = Optional.ofNullable(entityManager.createQuery("select Max(p.nroprestamo) from Prestamo p").getSingleResult());
			return Optional.ofNullable(Integer.valueOf(ultimo.get().toString()));	
		}
		catch(NoSuchElementException nse) {
			Optional<Integer>ultimo = Optional.empty();
			return ultimo;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Prestamo> findByFuncionario(Long idfuncionario) {
		return entityManager.createQuery("select p from Prestamo p where p.funcionario.idgplanta =:id")
				.setParameter("id", idfuncionario)
				.getResultList();
	}

	@Override
	public Prestamo getUltimoByFuncionario(Long idfuncionario) throws NoResultException {
		Long idPrst = (Long) entityManager.createQuery("select Max(p.idfprestamos) from Prestamo p where p.funcionario.idgplanta = :id")
				.setParameter("id", idfuncionario)
				.getSingleResult();

		return (Prestamo) entityManager.createQuery("select p from Prestamo p where idfprestamos =:idp")
				.setParameter("idp", idPrst)
				.getSingleResult();
	}

	public void deleteAmortizados() throws Exception {
		entityManager.joinTransaction();
		entityManager.createQuery("delete From Prestamo fp where fp.cantCuotas = fp.cuotasPagas")
		.executeUpdate();
		return;
	}

	@Override
	public void deleteByTarjeta(Integer tarjeta) throws Exception {
		entityManager.createQuery("delete from Prestamo p where p.tarjeta =:tarjeta")
		.setParameter("tarjeta", tarjeta)
		.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Long> findIdGplantaDistinct() throws Exception {
		return entityManager.createQuery("select distinct p.funcionario.idgplanta from Prestamo p").getResultList();
	}

}
