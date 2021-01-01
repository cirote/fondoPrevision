package org.mercosur.fondoPrevision.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.mercosur.fondoPrevision.entities.Gcargo;
import org.mercosur.fondoPrevision.repository.GcargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class GcargoServiceImpl implements GcargoService {

	@Autowired
	GcargoRepository gcargoRepository;
	
	@Override
	public List<Gcargo> getAllGcargos() {
		return gcargoRepository.findAll(); 
	}

	@Override
	public void calculoDeAumento(BigDecimal pct) throws Exception {
		List<Gcargo> lst = gcargoRepository.findAll();
		for(Gcargo gg:lst) {
			BigDecimal complemento = gg.getBasico().multiply(pct).divide(new BigDecimal("100"));
			gg.setComplemento(complemento);
			gg = gcargoRepository.save(gg);
		}
		return ;
	}

	@Override
	public void sumarBasicoAndComplemento() throws Exception {
		List<Gcargo> lst = gcargoRepository.findAll();
		for(Gcargo gg:lst) {
			BigDecimal basico = gg.getBasico().add(gg.getComplemento()).setScale(2, RoundingMode.HALF_UP);
			gg.setBasico(basico);
			gg.setComplemento(BigDecimal.ZERO);
			gg = gcargoRepository.save(gg);
		}
		return ;
	}

	@Override
	public List<Gcargo> getAllByUnidad(String unidad) throws Exception {
		
		return gcargoRepository.getAllByUnidad(unidad);
	}

}
