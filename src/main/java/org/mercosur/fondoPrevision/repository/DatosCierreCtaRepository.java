package org.mercosur.fondoPrevision.repository;

import org.mercosur.fondoPrevision.entities.DatosCierreCta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosCierreCtaRepository extends JpaRepository<DatosCierreCta, Long> {

	@Query("select dc from DatosCierreCta dc where dc.gplanta_id =:gplanta_id")
	public DatosCierreCta getDatosByGplantaid(Long gplanta_id) throws Exception;
}
