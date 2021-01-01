package org.mercosur.fondoPrevision.exceptions;

public class ValidacionAniomesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3462445619946859744L;

	public ValidacionAniomesException() {
		super("Error en el AnioMes ingresado");
	}
	
	public ValidacionAniomesException(String message) {
		super(message);
	}
}
