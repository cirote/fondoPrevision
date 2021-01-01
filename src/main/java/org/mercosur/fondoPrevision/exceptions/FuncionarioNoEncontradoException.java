package org.mercosur.fondoPrevision.exceptions;

public class FuncionarioNoEncontradoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3200345548704279377L;

	public FuncionarioNoEncontradoException() {
		super("No se encontró al funcionario en Planta");
	}
	
	public FuncionarioNoEncontradoException(String message) {
		super(message);
	}
}
