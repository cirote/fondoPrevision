package org.mercosur.fondoPrevision.service;

import org.mercosur.fondoPrevision.entities.TipoPrestamo;
import org.mercosur.fondoPrevision.exceptions.TipoPrestamoNotFoundException;

public interface TipoPrestamoService {

	TipoPrestamo getById(Integer id) throws TipoPrestamoNotFoundException;
}
