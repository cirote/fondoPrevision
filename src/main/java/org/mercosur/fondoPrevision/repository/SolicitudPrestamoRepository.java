package org.mercosur.fondoPrevision.repository;

import java.util.List;
import java.util.Optional;

import org.mercosur.fondoPrevision.entities.SolicitudPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudPrestamoRepository extends JpaRepository<SolicitudPrestamo, Long>, SolicitudPrestamoRepositoryCustom{


	public Optional<SolicitudPrestamo> findById(Long id);

	@Query("select sp from SolicitudPrestamo sp where sp.enviadaAfondo = true and sp.procesada = false and sp.enviadaAComision = false")
	public List<SolicitudPrestamo> getAllSinProcesar();
	
	@Query("select s from SolicitudPrestamo s where s.enviadaAComision = true and ((s.aprobada = false and s.rechazada = false) or (s.aprobada2 = false and s.rechazada2 = false))")
	public List<SolicitudPrestamo> getAllSinSupervisar();
	
	@Query("select s from SolicitudPrestamo s where s.procesada = false and s.enviadaAComision= true and (s.aprobada = true and s.aprobada2 = true) or (s.rechazada = true and s.rechazada2 = true)")
	public List<SolicitudPrestamo> getAllDevueltasDeComision();
	
	@Query("select sp from SolicitudPrestamo sp where sp.tarjeta =:tarjeta and sp.enviadaAfondo = true "
				+" and sp.procesada = false")
	public List<SolicitudPrestamo> getByTarjetaSinProcesar(Integer tarjeta);
	
	@Query("select sp from SolicitudPrestamo sp where sp.tarjeta =:tarjeta and sp.enviadaAfondo = false")
	public List<SolicitudPrestamo> getByTarjetaSinEnviar(Integer tarjeta);
	
	@Query("select sp from SolicitudPrestamo sp where sp.funcionario.idgplanta =:id")
	public List<SolicitudPrestamo> getAllByGplantaId(Long id);
}
