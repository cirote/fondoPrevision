package org.mercosur.fondoPrevision.service;

import java.util.List;

import org.mercosur.fondoPrevision.entities.SolicitudPrestamo;
import org.mercosur.fondoPrevision.repository.SolicitudPrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudPrestamoServiceImpl implements SolicitudPrestamoService {

	@Autowired
	SolicitudPrestamoRepository solicitudPrestamoRepository;
	
	@Autowired
	LogfondoService logFondoService;
	
	@Autowired
	UserService userService;
	
	@Override
	public List<SolicitudPrestamo> getNoProcesadas() throws Exception {
		return solicitudPrestamoRepository.getAllSinProcesar();
	}

	@Override
	public SolicitudPrestamo ingresarSolicitud(SolicitudPrestamo solicitud) throws Exception {
		solicitud = solicitudPrestamoRepository.save(solicitud);
		logFondoService.agregarLog("Solicitud Prestamo", "Ingreso de solicitud del funcionario: " + solicitud.getTarjeta().toString());
		return solicitud;
	}

	@Override
	public List<SolicitudPrestamo> getByTarjetaSinProcesar(Integer tarjeta) throws Exception {
		
		return solicitudPrestamoRepository.getByTarjetaSinProcesar(tarjeta);
	}

	@Override
	public void deleteSolicitud(Long id) throws Exception {
		SolicitudPrestamo solic = solicitudPrestamoRepository.getOne(id);
		solicitudPrestamoRepository.delete(solic);
		logFondoService.agregarLog("Solicitud Prestamo", "Se Eliminó solicitud del funcionario: " + solic.getTarjeta().toString());
		return;
	}

	@Override
	public List<SolicitudPrestamo> getByTarjetaSinEnviar(Integer tarjeta) throws Exception {
		return solicitudPrestamoRepository.getByTarjetaSinEnviar(tarjeta);
	}

	@Override
	public List<SolicitudPrestamo> getenviadasAComision() throws Exception {
		return solicitudPrestamoRepository.getAllSinSupervisar();
	}

	@Override
	public List<SolicitudPrestamo> getdevueltasDeComision() throws Exception {
		return solicitudPrestamoRepository.getAllDevueltasDeComision();
	}

	@Override
	public SolicitudPrestamo updateSolicitud(SolicitudPrestamo fromSol) throws Exception {	
		if(fromSol.getAprobada() && fromSol.getAprobada2()) {
			fromSol.setRechazada(false);
			fromSol.setRechazada2(false);
		}
		if(fromSol.getRechazada() && fromSol.getRechazada2()) {
			fromSol.setAprobada(false);
			fromSol.setAprobada2(false);
		}
		SolicitudPrestamo toSol = solicitudPrestamoRepository.findById(fromSol.getIdfsolicitud())
				.orElseThrow(()-> new Exception("No se encuentra la solicitud a actualizar!"));
		mapSolicitud(fromSol, toSol);
		logFondoService.agregarLog("Solicitud Prestamo", "Actualización de solicitud del func: " + toSol.getTarjeta());
		return solicitudPrestamoRepository.save(toSol);
	}

	protected void mapSolicitud(SolicitudPrestamo fromSol, SolicitudPrestamo toSol) {
		toSol.setCancelPrstNros(fromSol.getCancelPrstNros());
		toSol.setCantCuotas(fromSol.getCantCuotas());
		toSol.setCapitalPrestamo(fromSol.getCapitalPrestamo());
		toSol.setCodigoPrestamo(fromSol.getCodigoPrestamo());
		toSol.setCuota(fromSol.getCuota());
		toSol.setEnviadaAComision(fromSol.getEnviadaAComision());
		toSol.setEnviadaAfondo(fromSol.getEnviadaAfondo());
		toSol.setFechaSolicitud(fromSol.getFechaSolicitud());
		toSol.setFuncionario(fromSol.getFuncionario());
		toSol.setImporteNeto(fromSol.getImporteNeto());
		toSol.setInteresPrestamo(fromSol.getInteresPrestamo());
		toSol.setMotivo(fromSol.getMotivo());
		toSol.setParamPrestamo(fromSol.getParamPrestamo());
		toSol.setProcesada(fromSol.getProcesada());
		toSol.setAprobada(fromSol.getAprobada());
		toSol.setAprobada2(fromSol.getAprobada2());
		toSol.setRechazada(fromSol.getRechazada());
		toSol.setRechazada2(fromSol.getRechazada2());
		toSol.setTarjeta(fromSol.getTarjeta());
		toSol.setTipoPrestamo(fromSol.getTipoPrestamo());

	}
}
