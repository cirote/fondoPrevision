package org.mercosur.fondoPrevision.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


public class FuncionesUtiles {

	
	public static String getSiteURL(HttpServletRequest request) {
		String siteUrl = request.getRequestURL().toString();
		return siteUrl.replace(request.getServletPath(), "");
	}
	
	public static Boolean chequeoOkMesLiquidacion(String mesActual, String mesIngresado) {
		Integer ma = Integer.valueOf(mesActual);
		Integer ms = Integer.valueOf(mesIngresado);
		if(ma == ms || ma.compareTo(ms) > 0 || ms.compareTo(ma + 1) > 0) {
			return false;
		}
		return true;
	}
	
	public static String mesLiquidacionSiguiente(String mesLiquidacion) {
		if(mesLiquidacion.substring(4).equals("12")) {
			String anio = String.valueOf((Integer.valueOf(mesLiquidacion.substring(0, 4)) + 1));
			return anio.concat("01");
		}
		else {
			Integer nmes = Integer.valueOf(mesLiquidacion.substring(4)) + 1;
			if(nmes < 10) {
				return mesLiquidacion.substring(0, 4).concat("0").concat(nmes.toString());
			}
			else {
				return mesLiquidacion.substring(0, 4).concat(nmes.toString());
			}
		}
	}
	
	public static String literalMesAnio(String mes, String anio){
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

	public static String ultimoDiadelMes(String mes, String anio){
		
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
            System.out.println("Mes inv&aacute;lido.");
            break;	
		}
		return String.valueOf(numDays);
	}
	
	public static Boolean validacionFecha(String fecha){
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

	public static Date convertirFecha(String dia, String mes, String anio){
		String fechatope = dia + "-" + mes + "-" + anio;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try{
			Date dfecha = sdf.parse(fechatope);
			return dfecha;
		}
		catch(Exception ex){
			return null;
		}
	}
	
	public static LocalDate strfechaTolocaldate(String dia, String mes, String anio) {
		String fecha = dia + "-" + mes + "-" + anio;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		 LocalDate localDate = LocalDate.parse(fecha, formatter);
		 return localDate;
	}
	
	public static String dateToString(Date fecha){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sfecha = sdf.format(fecha);
		return sfecha;
	}

	public static String formatearAnioMes(String mesAnio){
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
	
	public static List<String> mesesConForma(List<String> meses){
		List<String> lstmeses = new ArrayList<String>();
		String mm;
		for(String mes:meses) {
			mm = mes.substring(4) + "/" + mes.substring(0, 4);
			lstmeses.add(mm);
		}		
		return lstmeses;
	}

}
