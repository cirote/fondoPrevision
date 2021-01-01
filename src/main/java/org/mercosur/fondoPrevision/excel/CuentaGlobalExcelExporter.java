package org.mercosur.fondoPrevision.excel;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mercosur.fondoPrevision.dto.CuentaGlobalSummary;
import org.mercosur.fondoPrevision.entities.CuentaGlobal;
import org.mercosur.fondoPrevision.repository.CuentaGlobalRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CuentaGlobalExcelExporter {

	@Autowired
	CuentaGlobalRepository cuentaGlobalRepository;

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private int rowCount;

	private List<CuentaGlobal> lstResumen;
	
	private BigDecimal sumaTotal;
	
	private String mesliquidacion;
	
	private Map<String, List<CuentaGlobalSummary>> ctaGlobalMap;
	
	public CuentaGlobalExcelExporter(List<CuentaGlobal> lstResumen, BigDecimal sumaTotal, String mesliquidacion, 
			Map<String, List<CuentaGlobalSummary>> ctaGlobalMap) {
		super();
		this.lstResumen = lstResumen;
		this.sumaTotal = sumaTotal;
		this.mesliquidacion = mesliquidacion;
		this.setCtaGlobalMap(ctaGlobalMap);
		
		workbook = new XSSFWorkbook();

	}

	private void writeTitleSheet() {
		Row row = sheet.createRow(1);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		Cell cell = row.createCell(2);
		cell.setCellValue("MERCOSUR - CUENTA GLOBAL ");
		cell.setCellStyle(style);
				
		rowCount +=2;
		
	}
	

	private void writeSubTableHeader() {
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(12);
		style.setFont(font);
		
		Row row = sheet.createRow(rowCount);
		Cell cell = row.createCell(0);
		cell.setCellValue("Nro. Prest.");
		cell.setCellStyle(style);
		
		cell = row.createCell(1);
		cell.setCellValue("Titular");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue("Nro. Cuota");
		cell.setCellStyle(style);
		
		cell = row.createCell(3);
		cell.setCellValue("Importe");
		cell.setCellStyle(style);

		rowCount += 1;
	}

	private void writeSubTableData(List<CuentaGlobalSummary> lstdetalle) {
		
		for(CuentaGlobalSummary cg : lstdetalle) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(cg.getNroPrestamo());
			
			cell = row.createCell(1);
			cell.setCellValue(cg.getTitular());
			sheet.autoSizeColumn(1);
			
			cell = row.createCell(2);
			cell.setCellValue(cg.getNroCuota().shortValue());
			
			cell = row.createCell(3);
			cell.setCellValue(cg.getImporte().doubleValue());
		}
	}
	
	
	private String getAsString(BigDecimal nro){
		if(nro == null){
			return null;
		}
		BigDecimal remainder = ((BigDecimal) nro).remainder(BigDecimal.ONE);
		String digitocero = remainder.toString();
		digitocero = digitocero.substring(digitocero.length() - 1);
		
		if(remainder.abs().compareTo(BigDecimal.ZERO) > 0){
			if(digitocero.equals("0")){
				return  NumberFormat.getInstance(Locale.getDefault()).format(nro) + "0";					
			}
			return  NumberFormat.getInstance(Locale.getDefault()).format(nro);
		}
		else{
			return NumberFormat.getInstance(Locale.getDefault()).format(nro) + ",00";
		}

	}

	public void export(HttpServletResponse response) throws IOException{

		for(CuentaGlobal cgr : this.lstResumen) {
			this.mesliquidacion = cgr.getMesliquidacion();
			sheet = workbook.createSheet("CG-"+cgr.getMesliquidacion());
			this.setRowCount(4);

			writeTitleSheet();
			
			Row row = sheet.createRow(rowCount);
			
			CellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			font.setFontHeight(12);
			style.setFont(font);

			Cell cell = row.createCell(1);
			cell.setCellValue("Mes Distribución: ");
			cell.setCellStyle(style);
			cell = row.createCell(2);
			cell.setCellValue(cgr.getMesliquidacion());
			
			cell = row.createCell(4);
			cell.setCellValue("Total del Mes: ");
			cell.setCellStyle(style);
			cell = row.createCell(6);
			cell.setCellValue(cgr.getImporte().doubleValue());
			
			rowCount += 2;
			writeSubTableHeader();
			
			String key = cgr.getMesliquidacion() + " - " + getAsString(cgr.getImporte());
			List<CuentaGlobalSummary> lstDetalles = ctaGlobalMap.get(key);
			writeSubTableData(lstDetalles);
			
		}
		

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}


	
	public XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public List<CuentaGlobal> getLstResumen() {
		return lstResumen;
	}

	public void setLstResumen(List<CuentaGlobal> lstResumen) {
		this.lstResumen = lstResumen;
	}

	public BigDecimal getSumaTotal() {
		return sumaTotal;
	}

	public void setSumaTotal(BigDecimal sumaTotal) {
		this.sumaTotal = sumaTotal;
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}

	public Map<String, List<CuentaGlobalSummary>> getCtaGlobalMap() {
		return ctaGlobalMap;
	}

	public void setCtaGlobalMap(Map<String, List<CuentaGlobalSummary>> ctaGlobalMap) {
		this.ctaGlobalMap = ctaGlobalMap;
	}

	public XSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	
}
