package org.mercosur.fondoPrevision.repository;

import java.util.Date;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.CategoriaLog;
import org.mercosur.fondoPrevision.entities.Logfondo;

@Repository
public interface LogfondoRepository extends JpaRepository<Logfondo, Long> {

	public Iterable<Logfondo> getAllLogfondoByFechahora(Date fechahora);
	
	public Iterable<Logfondo> getAllLogfondoByUsernameAndFechahora(String username, Date fechahora);
	
	public Iterable<Logfondo> getAllLogfondoByCategoriaLog(CategoriaLog categoria);
	
	@Query("Select lf FROM Logfondo lf")
	public Iterable<Logfondo> findAllLogfondo(Sort sort);

}
