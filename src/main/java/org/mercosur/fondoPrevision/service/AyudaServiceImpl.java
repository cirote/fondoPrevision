package org.mercosur.fondoPrevision.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mercosur.fondoPrevision.entities.Ayuda;
import org.mercosur.fondoPrevision.repository.AyudaRepository;

@Service
public class AyudaServiceImpl implements AyudaService{

	@Autowired
	AyudaRepository ayudaRepository;
	
	@Override
	public Ayuda getByClave(String clave) throws Exception {
		Ayuda help = ayudaRepository.findByClave(clave).orElseThrow(() -> new Exception ("Tema de ayuda no encontrado - " + this.getClass().getName()));
		return help;
	}

}
