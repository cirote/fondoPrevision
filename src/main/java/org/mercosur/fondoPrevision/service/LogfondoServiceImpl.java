package org.mercosur.fondoPrevision.service;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mercosur.fondoPrevision.entities.CategoriaLog;
import org.mercosur.fondoPrevision.entities.Logfondo;
import org.mercosur.fondoPrevision.entities.User;
import org.mercosur.fondoPrevision.repository.CategoriaLogRepository;
import org.mercosur.fondoPrevision.repository.LogfondoRepository;

@Service
public class LogfondoServiceImpl implements LogfondoService{

	@Autowired
	LogfondoRepository logfondoRepository;
	
	@Autowired
	CategoriaLogRepository categoriaLogRepository;
	
	@Autowired
	UserService userService;
	
	@Override
	public Iterable<Logfondo> getAllLogsByFechahora(Date fechahora) throws Exception {
		
		return logfondoRepository.getAllLogfondoByFechahora(fechahora);
	}

	@Override
	public Iterable<Logfondo> getAllLogfondo() {
		return logfondoRepository.findAll();
	}

	@Override
	public Logfondo createLogfondo(Logfondo log) throws Exception {
		Logfondo logactual = logfondoRepository.save(log);
		return logactual;
	}

	@Override
	public Iterable<Logfondo> getAllLogfondoByUsernameAndFechahora(String username, Date fechahora) throws Exception {
		
		return logfondoRepository.getAllLogfondoByUsernameAndFechahora(username, fechahora);
	}

	@Override
	public Iterable<Logfondo> getAllLogfondByCategoria(String categoria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void agregarLog(String proc) throws Exception {
		
		Iterable<CategoriaLog> lstCat = categoriaLogRepository.findAll();
		
		CategoriaLog clog = new CategoriaLog();
		for (CategoriaLog categoriaLog : lstCat) {
			if(categoriaLog.getDescripcion().matches("(?i).*" + proc + ".*")) {
				clog.setIdfcategoriaslog(categoriaLog.getIdfcategoriaslog());
				clog.setDescripcion(categoriaLog.getDescripcion());
			}
		}
		
		Logfondo log = new Logfondo();
		User user = userService.getLoggedUser();
		Date currentDate;
		Calendar calendar = Calendar.getInstance();
		currentDate = calendar.getTime();
		log.setFechahora(currentDate);
		log.setProcedimiento(proc);
		log.setUsername(user.getUsername());
		log.setCategoriaLog(clog);
		log = logfondoRepository.save(log);
		return;		
	}

	@Override
	public void agregarLog(String proc, String mensaje) throws Exception {
		Iterable<CategoriaLog> lstCat = categoriaLogRepository.findAll();
		
		CategoriaLog clog = new CategoriaLog();
		for (CategoriaLog categoriaLog : lstCat) {
			if(categoriaLog.getDescripcion().matches("(?i).*" + proc + ".*")) {
				clog.setIdfcategoriaslog(categoriaLog.getIdfcategoriaslog());
				clog.setDescripcion(categoriaLog.getDescripcion());
			}
		}
		
		Logfondo log = new Logfondo();
		User user = userService.getLoggedUser();
		Date currentDate;
		Calendar calendar = Calendar.getInstance();
		currentDate = calendar.getTime();
		log.setFechahora(currentDate);
		log.setProcedimiento(proc + " - " + mensaje);
		log.setUsername(user.getUsername());
		log.setCategoriaLog(clog);
		log = logfondoRepository.save(log);
		return;		
	
	}

	@Override
	public void agregarLog(String proc, String mensaje, String username) throws Exception {
		Iterable<CategoriaLog> lstCat = categoriaLogRepository.findAll();
		
		CategoriaLog clog = new CategoriaLog();
		for (CategoriaLog categoriaLog : lstCat) {
			if(categoriaLog.getDescripcion().matches("(?i).*" + proc + ".*")) {
				clog.setIdfcategoriaslog(categoriaLog.getIdfcategoriaslog());
				clog.setDescripcion(categoriaLog.getDescripcion());
			}
		}
		
		Logfondo log = new Logfondo();
		Date currentDate;
		Calendar calendar = Calendar.getInstance();
		currentDate = calendar.getTime();
		log.setFechahora(currentDate);
		log.setProcedimiento(proc + " - " + mensaje);
		log.setUsername(username);
		log.setCategoriaLog(clog);
		log = logfondoRepository.save(log);
		return;		
	}

}
