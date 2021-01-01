package org.mercosur.fondoPrevision.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuncionesUtiles {

	public Boolean chequeoOkMesLiquidacion(String mesActual, String mesIngresado) {
		Integer ma = Integer.valueOf(mesActual);
		Integer ms = Integer.valueOf(mesIngresado);
		if(ma == ms || ma.compareTo(ms) > 0 || ms.compareTo(ma + 1) > 0) {
			return false;
		}
		return true;
	}
	
	public String ultimodia(String mes, String anio){
		Integer month = Integer.valueOf(mes);
		Integer year = Integer.valueOf(anio);
		int numdays = 0;
		
		switch(month){
		case 1: case 3: case 5:
		case 7: case 8: case 10:
		case 12:{
			numdays = 31;
			break;
		}
		case 4: case 6: case 9:
		case 11:{
			numdays = 30;
			break;
		}
		case 2:{
			if(((year % 4 == 0) && !(year % 100 == 0))
				|| (year % 400 == 0)){
				numdays = 29;
			}
			else{
				numdays = 28;
			}
			break;
		}
		default:{
			numdays = 0;
			break;
		}
		}
		return String.valueOf(numdays);
	}

	public String literalMesAnio(String mes, String anio){
		Integer imes = Integer.valueOf(mes);
		String smes = "";
		switch(imes){
		case 1:{
			smes = "Enero de ";
			break;
		}
		case 2:{
			smes = "Febrero de ";
			break;
		}
		case 3:{
			smes = "Marzo de ";
			break;
		}
		case 4:{
			smes= "Abril de ";
			break;
		}
		case 5:{
			smes = "Mayo de ";
			break;
		}
		case 6:{
			smes = "Junio de ";
			break;
		}
		case 7:{
			smes = "Julio de ";
			break;
		}
		case 8: {
			smes = "Agosto de ";
			break;
		}
		case 9:{
			smes = "Setiembre de ";
			break;
		}
		case 10:{
			smes = "Octubre de ";
			break;
		}
		case 11:{
			smes = "Noviembre de ";
			break;
		}
		case 12:{
			smes = "Diciembre de ";
			break;
		}
		}
		return (smes + anio);
	}

	public String ultimoDiadelMes(String mes, String anio){
		
		Integer vmes = Integer.valueOf(mes);
		Integer year = Integer.valueOf(anio);
		int numDays = 0;
		
		switch (vmes) {
        case 1: case 3: case 5:
        case 7: case 8: case 10:
        case 12:
            numDays = 31;
            break;
        case 4: case 6:
        case 9: case 11:
            numDays = 30;
            break;
        case 2:
            if (((year % 4 == 0) && 
                 !(year % 100 == 0))
                 || (year % 400 == 0))
                numDays = 29;
            else
                numDays = 28;
            break;
        default:
            System.out.println("Invalid month.");
            break;	
		}
		return String.valueOf(numDays);
	}
	
	public Boolean validacionFecha(String fecha){
		Pattern fechaPattern = Pattern.compile("\\d{2}/\\d{2}/(\\d{4})");
		
		try{
			Matcher fec = fechaPattern.matcher(fecha);
			if(fec.matches()){
				return true;
			}
			else{
				return false;
			}
		}
		catch(IllegalStateException iex){
			return false;
		}
	
	}

	
	public String dateToString(Date fecha){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sfecha = sdf.format(fecha);
		return sfecha;
	}

	public String formatearAnioMes(String mesAnio){
		Pattern fecha = Pattern.compile("\\d{2}/(\\d{4})");
		try{
			if(mesAnio.equals("")){
				return null;
			}
			Matcher fec = fecha.matcher(mesAnio);
			if(fec.matches()){
				String aniomes = mesAnio.substring(3).concat(mesAnio.substring(0, 2));
				return aniomes;
			}
			else{
				return null;
			}
		}
		catch(IllegalStateException iex){
			return null;
		}
	}
}
