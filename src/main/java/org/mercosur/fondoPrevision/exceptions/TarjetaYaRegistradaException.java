package org.mercosur.fondoPrevision.exceptions;

public class TarjetaYaRegistradaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7779402910059780741L;

	public TarjetaYaRegistradaException() {
		super("La tarjeta ingresada ya está asignada a otra persona.");
	}
	
	public TarjetaYaRegistradaException(String message) {
		super(message);
	}
}
