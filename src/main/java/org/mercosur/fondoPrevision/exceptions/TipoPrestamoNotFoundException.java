package org.mercosur.fondoPrevision.exceptions;

public class TipoPrestamoNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2222596184485495603L;

	public TipoPrestamoNotFoundException() {
		super("Tipo Préstamo no encontrado");
	}
	
	public TipoPrestamoNotFoundException(String message) {
		super(message);
	}
}
