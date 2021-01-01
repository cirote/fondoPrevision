package org.mercosur.fondoPrevision.controller;

import java.io.Serializable;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.mercosur.fondoPrevision.service.LogfondoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Component("conexionSSH")
public class ConexionSSH implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9086997487310427587L;
	
	@Autowired
	LogfondoService logfondoService;
	
	private static final String ENTER_KEY = "/n";

	public void exeComando(String user, String host, String pasw, String comando)
		throws Exception{
		
		JSch jsch = new JSch();
		Session session = null;
		ChannelExec channel = null;
		try{
			session = jsch.getSession(user, host, 22);
			session.setPassword(pasw);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			
			logfondoService.agregarLog("Respaldos/Restauraciones", "Sesion Activa: 	PASO POR CONNECT " + user + " host: " + host);
			System.out.println("Sesión activa: PASO POR CONNECT");
			
			channel = (ChannelExec)session.openChannel("exec");
			BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
			
			channel.setCommand(comando);
			channel.connect();
			
			StringBuilder builder = new StringBuilder();
			String msg = null;
			while((msg = in.readLine()) != null){
				builder.append(msg);
				builder.append(ENTER_KEY);
			}
			System.out.println("MENSAJE DE BUFFEREDREADER " + user + pasw + comando + builder.toString());
			logfondoService.agregarLog("Respaldos/Restauraciones", "MENSAJE DE BUFFEREDREADER:  " + user + " PASSW: " + pasw + " command: " + comando + " MSG: " + builder.toString());

		}
		catch(Exception ex){
			System.out.println("EXCEPCION!!!..." + ex.getMessage()); 
			logfondoService.agregarLog("Respaldos/Restauraciones", "Exception: " + ex.getMessage());
			throw new Exception(ex);
		}
		finally{
			if(channel.isConnected())
				channel.disconnect();
			if(session.isConnected())
				session.disconnect();
		}
	}
	
}
