package org.mercosur.fondoPrevision.exceptions;

public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9171103584593375647L;
	
	public UserNotFoundException(){
		super("Usuario no encontrado");
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
