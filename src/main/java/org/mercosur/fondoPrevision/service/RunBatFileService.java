package org.mercosur.fondoPrevision.service;

public interface RunBatFileService {

	public String ejecutarBatOrSSH(String name);
	
	public String ejecutarBatOrSSH(String name, String parametro);

}
