package org.mercosur.fondoPrevision.service;

import java.util.Date;

import org.mercosur.fondoPrevision.entities.Logfondo;

public interface LogfondoService {

	public Iterable<Logfondo> getAllLogsByFechahora(Date fechahora) throws Exception;
	
	public Iterable<Logfondo> getAllLogfondo();
	
	public Iterable<Logfondo> getAllLogfondoByUsernameAndFechahora(String username, Date fechahora) throws Exception;
	
	public Logfondo createLogfondo(Logfondo log) throws Exception;
	
	public Iterable<Logfondo> getAllLogfondByCategoria(String categoria) throws Exception;
	
	public void agregarLog(String proc) throws Exception;
	
	public void agregarLog(String proc, String mensaje) throws Exception;
}
