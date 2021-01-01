package org.mercosur.fondoPrevision.service;

import java.util.List;

import org.mercosur.fondoPrevision.entities.SolicitudPrestamo;

public interface SolicitudPrestamoService {

	public List<SolicitudPrestamo> getNoProcesadas() throws Exception;
	
	public List<SolicitudPrestamo> getenviadasAComision() throws Exception;
	
	public List<SolicitudPrestamo> getdevueltasDeComision() throws Exception;
	
	public List<SolicitudPrestamo> getByTarjetaSinProcesar(Integer tarjeta) throws Exception;
	
	public List<SolicitudPrestamo> getByTarjetaSinEnviar(Integer tarjeta) throws Exception;
	
	public SolicitudPrestamo ingresarSolicitud(SolicitudPrestamo solicitud) throws Exception;
	
	public SolicitudPrestamo updateSolicitud(SolicitudPrestamo fromSol) throws Exception;
	
	public void deleteSolicitud(Long id) throws Exception;
}
