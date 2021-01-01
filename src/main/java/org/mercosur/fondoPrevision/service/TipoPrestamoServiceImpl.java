package org.mercosur.fondoPrevision.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mercosur.fondoPrevision.entities.TipoPrestamo;
import org.mercosur.fondoPrevision.exceptions.TipoPrestamoNotFoundException;
import org.mercosur.fondoPrevision.repository.TipoPrestamoRepository;

@Service
public class TipoPrestamoServiceImpl implements TipoPrestamoService {

	@Autowired
	TipoPrestamoRepository tipoPrestamoRepository;
	
	@Override
	public TipoPrestamo getById(Integer id) throws TipoPrestamoNotFoundException {
		
		return tipoPrestamoRepository.getOne(id);
	}

}
