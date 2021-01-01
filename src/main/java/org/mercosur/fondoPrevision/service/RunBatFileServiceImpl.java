package org.mercosur.fondoPrevision.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mercosur.fondoPrevision.controller.ConexionSSH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunBatFileServiceImpl implements RunBatFileService {
	
	@Autowired
	ConexionSSH conexionSSH;
	
	@Autowired
	LogfondoService logfondoService;
	
	final static String OS = System.getProperty("os.name").toLowerCase();
	
	private Boolean isWindowsOS() {
		return (OS.indexOf("win") >= 0);
	}
	
	private Boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
	
	@Override
	public String ejecutarBatOrSSH(String name) {
		String result = null;
		if(this.isUnix()) {
			name = name + ".sh";
			System.out.println("RECONOCE el Sistema LINUX y pasa a ejecutarSH");
			result = ejecutarSH(name);
		}
		else if(isWindowsOS()) {
			String completename = "C:\\var\\backup\\"+name+".bat";
			result = ejecutarBat(completename);
		} 
		return result;
	}

	private String ejecutarSH(String nameSh) {
		 try {
			 System.out.println("NOMBRE PROCEDIMIENTO: "+ nameSh);
			 // conexionSSH.exeComando("adriana", "localhost", "adrianamf0653", "sudo sh /var/respaldos/fondo/" + nameSh);
			 
             Process proc = Runtime.getRuntime().exec("sh /var/respaldos/fondo/" + nameSh); 
             InputStream stdout = proc.getErrorStream();
             
             /* Se obtiene el resultado de finalizacion del proceso */
             int resultado = proc.waitFor();
             
             String line;
             BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stdout));
             while ((line = brCleanUp.readLine()) != null) {
                 System.out.println(line);
             }
             brCleanUp.close();
  
             if (resultado == 0) {
                 System.out.println("Respaldo exitoso");
                 logfondoService.agregarLog("Respaldos/Restauraciones", "Respaldo exitoso");
                 return "Ejecucion exitosa";
             } else {
                 logfondoService.agregarLog("Respaldos/Restauraciones", "Error al respaldar/restaurar " + nameSh);
                 System.out.println("Error al respaldar");
                 return "Ejecución con Error";
             }
         } catch (IOException | InterruptedException ex) {
             System.out.println(ex.getMessage());
             return ex.getMessage();
         }
		catch(Exception e) {
			return e.getMessage();
		} 	
		 //return "Ejecucion Exitosa";
	}
	
	private String ejecutarBat(String nameBat) {

		try {
	        String saveBat = "\""+nameBat + "\"";

	        Process runtimeProcess = Runtime.getRuntime().exec(saveBat);
	        int processComplete = runtimeProcess.waitFor();
	        System.out.println("Process exitValue: " + processComplete);

	        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
	        if (processComplete == 0) {
	        	try {
	        		logfondoService.agregarLog("Respaldos/Restauraciones", "Proceso externo - Ejecución exitosa: " + nameBat);
		        	return "Ejecucion exitosa";
	        	}
	        	catch (Exception e) {
	        		return e.getMessage();
	        	}
	        } else {
	        	try {
	        		logfondoService.agregarLog("Respaldos/Restauraciones", "Proceso externo - Ejecución fracasada: " + nameBat);
		            return "Fracaso la Ejecucion";
	        	}
	        	catch(Exception e) {
	        		return e.getMessage();
	        	}
	        }

	    } catch (Exception ex) {
	        return ((Throwable) ex).getMessage();
		}
	}
	
	
	@Override
	public String ejecutarBatOrSSH(String name, String parametro) {
		String result = null;
		if(this.isUnix()) {
			name = name + ".sh";
			result = ejecutarSH(name, parametro);
		}
		else if(isWindowsOS()) {
			String completename = "C:\\var\\backup\\"+name+".bat ";
			result = ejecutarBat(completename, parametro);
		} 
		return result;
	}

	private String ejecutarSH(String name, String parametro) {
		 try {
             Process proc = Runtime.getRuntime().exec("sh /var/respaldos/fondo/" + name + " " + parametro); 
             InputStream stdout = proc.getErrorStream();
             
             /* Se obtiene el resultado de finalizacion del proceso*/
             int resultado = proc.waitFor();
             
             String line;
             BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stdout));
             while ((line = brCleanUp.readLine()) != null) {
                 System.out.println(line);
             }
             brCleanUp.close();
  
             if (resultado == 0) {
                 System.out.println("Respaldo exitoso");
                 logfondoService.agregarLog("Respaldos/Restauraciones", "Respaldo exitoso");
                 return "Ejecucion exitosa";
             } else {
                 logfondoService.agregarLog("Respaldos/Restauraciones", "Error al respaldar/restaurar " + name+parametro);
                 System.out.println("Error al respaldar");
                 return "Error al respaldar/restaurar";
             }
         } catch (IOException | InterruptedException ex) {
             System.out.println(ex.getMessage());
 			return ex.getMessage();
         }
		catch(Exception e) {
			return e.getMessage();
		} 		
	}
	
	private String ejecutarBat(String nameBat, String parametro) {

		try {
	        String saveBat = "\""+nameBat + "\"";

	        Process runtimeProcess = Runtime.getRuntime().exec(new String[] {saveBat, parametro});
	        int processComplete = runtimeProcess.waitFor();
	        System.out.println("Process exitValue: " + processComplete);

	        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
	        if (processComplete == 0) {
	        	try {
	        		logfondoService.agregarLog("Respaldos/Restauraciones","Proceso externo - Ejecución exitosa: " + nameBat);
		        	return "Ejecucion exitosa";
	        	}
	        	catch (Exception e) {
	        		return e.getMessage();
	        	}
	        } else {
	        	try {
	        		logfondoService.agregarLog("Respaldos/Restauraciones", "Proceso externo - Ejecución fracasada: " + nameBat);
		            return "Fracaso la Ejecucion";
	        	}
	        	catch(Exception e) {
	        		return e.getMessage();
	        	}
	        }

	    } catch (Exception ex) {
	        return ((Throwable) ex).getMessage();
		}
	}


}
