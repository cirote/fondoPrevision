package org.mercosur.fondoPrevision.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.mercosur.fondoPrevision.entities.ParamPrestamo;
import org.mercosur.fondoPrevision.entities.Parametro;
import org.mercosur.fondoPrevision.exceptions.ParamNotFoundException;
import org.mercosur.fondoPrevision.repository.ParamPrestamoRepository;
import org.mercosur.fondoPrevision.repository.ParametroRepository;

@Service
public class ParamServiceImpl implements ParamService {

	@Autowired
	UserService userService;
	
	@Autowired
	LogfondoService logfondoService;
	
	@Autowired
	ParametroRepository paramRepository;
	
	@Autowired
	ParamPrestamoRepository paramPrestamoRepository;
	
	@Override
	public Iterable<Parametro> getAllParametros() {
		return paramRepository.findAll();
	}

	@Override
	public Iterable<ParamPrestamo> getAllParamPrestamo() {
		return paramPrestamoRepository.findAllOrderBymeses();
	}

	@Override
	public Parametro getParametroById(Long id) throws ParamNotFoundException {
		Parametro param = paramRepository.findById(id).orElseThrow(()-> new ParamNotFoundException());
		return param;
	}

	@Override
	public ParamPrestamo getParamPrestamoById(Integer id) throws ParamNotFoundException {
		ParamPrestamo paramprst = paramPrestamoRepository.findById(id).orElseThrow(()-> new ParamNotFoundException());
		return paramprst;
	}

	@Override
	public Parametro getParametroByMesliquidacionAndDescripcion(String mesliquidacion, String descripcion)
			throws ParamNotFoundException {
		Parametro param = paramRepository.findByMesliquidacionAndDescripcion(mesliquidacion, descripcion)
				.orElseThrow(()-> new ParamNotFoundException());

		return param;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public Parametro createParametro(Parametro param) throws Exception {
		param = paramRepository.save(param);
		logfondoService.agregarLog("Actualización Parámetros", "Se agregó un parámetro: " + param.getDescripcion());
		return param;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERVISOR')")
	public Parametro updateParametro(Parametro param) throws Exception {
		Parametro toParam = getParametroById(param.getIdfparametros());
		mapParam(param, toParam);
		logfondoService.agregarLog("Actualización Parámetros", "Se actualizó el parámetro: "+ toParam.getDescripcion());
		return paramRepository.save(toParam);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ParamPrestamo createParamPrestamo(ParamPrestamo param) throws Exception {
		param = paramPrestamoRepository.save(param);
		logfondoService.agregarLog("Actualización Parámetros", "Se agregó un parámetro: " + param.getDescripcion());
		return param;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
	public ParamPrestamo updateParamPrestamo(ParamPrestamo param) throws Exception {
		ParamPrestamo toParam = paramPrestamoRepository.getOne(param.getIdfparamprst());
		mapParamPrst(param, toParam);
		logfondoService.agregarLog("Actualización Parámetros", "Se actualizó el parámetro: "+ toParam.getDescripcion());
		return paramPrestamoRepository.save(toParam);
	}

	protected void mapParam(Parametro from, Parametro to) {
		to.setDescripcion(from.getDescripcion());
		to.setMesliquidacion(from.getMesliquidacion());
		to.setSimbolo(from.getSimbolo());
		to.setValor(from.getValor());
	}
	
	protected void mapParamPrst(ParamPrestamo from, ParamPrestamo to) {
		to.setDescripcion(from.getDescripcion());
		to.setMesliquidacion(from.getMesliquidacion());
		to.setMeses(from.getMeses());
		to.setTasa(from.getTasa());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteParametro(Long id) throws ParamNotFoundException, Exception {
		Parametro param = getParametroById(id);
		logfondoService.agregarLog("Actualización Parámetros", "Se eliminó el Parametro: " + param.getDescripcion() + param.getMesliquidacion());
		paramRepository.delete(param);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteParamPrestamo(Integer id) throws ParamNotFoundException, Exception {
		ParamPrestamo param = paramPrestamoRepository.getOne(id);
		paramPrestamoRepository.delete(param);
		logfondoService.agregarLog("Actualización Parámetros", "Se eliminó el Parametro: " + param.getDescripcion() + param.getMesliquidacion());
	}

	@Override
	public String updateMesLiquidacion(String mesLiq) throws Exception {
		String result = "success";
		paramRepository.pasajeAhistoricoParametros();
		paramRepository.actualizaMesLiquidacion(mesLiq);
		logfondoService.agregarLog("Actualización Parámetros", " Mes Liquidación " + mesLiq);
		
		paramPrestamoRepository.pasajeAhistoricoParametros();
		paramPrestamoRepository.actualizaMesLiquidacion(mesLiq);
		logfondoService.agregarLog("Actualización Parámetros", " Plazos y Tasas de Préstamos, Mes Liquidación: " + mesLiq);

		return result;
	}

	@Override
	public String getMesliquidacion() {
		return paramRepository.findByMesliquidacionDistinct();
	}

	@Override
	public Parametro getParametroByDescripcion(String desc) throws ParamNotFoundException {
		
		return paramRepository.getOneByDesc(desc);
	}

}
