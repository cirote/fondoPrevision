package org.mercosur.fondoPrevision.exceptions;

public class PrestamoNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2222596184485495603L;

	public PrestamoNotFoundException() {
		super("Préstamo no encontrado");
	}
	
	public PrestamoNotFoundException(String message) {
		super(message);
	}
}
