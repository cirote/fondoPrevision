package org.mercosur.fondoPrevision.exceptions;

public class FmovimientosException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2892544073787078067L;
	private String msg;

	public FmovimientosException(String msg){
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
