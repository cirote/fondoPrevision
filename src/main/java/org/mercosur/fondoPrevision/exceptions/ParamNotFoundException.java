package org.mercosur.fondoPrevision.exceptions;

public class ParamNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6547661470685289957L;

	public ParamNotFoundException() {
		super("Parámetro no encontrado");
	}
	
	public ParamNotFoundException(String message) {
		super(message);
	}
}
