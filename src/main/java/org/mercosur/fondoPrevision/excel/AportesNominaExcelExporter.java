package org.mercosur.fondoPrevision.excel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mercosur.fondoPrevision.dto.AportesSummary;

public class AportesNominaExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<AportesSummary> lstSM;
	private List<AportesSummary> lstUTF;
	private String mesliquidacion;
	private int rowCount;

	public AportesNominaExcelExporter(List<AportesSummary> lstSM, List<AportesSummary> lstUTF, String mesliquidacion) {
		this.lstSM = lstSM;
		this.lstUTF = lstUTF;
		this.mesliquidacion = mesliquidacion;
		
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("aportesNomina");
		this.rowCount = 4;

	}

	private void writeTitleRowSM() {
		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		String medio = "6";
		String completo ="12";

		Cell cell = row.createCell(2);
		
		if(this.mesliquidacion.substring(0,2).equals(medio) || this.mesliquidacion.substring(0,2).equals(completo)) {
			cell.setCellValue("MERCOSUR - LIQUIDACIÓN DE APORTES CORRESPONDIENTE A SUELDO Y AGUINALDO ");
			cell.setCellStyle(style);
		}
		else {
			cell.setCellValue("MERCOSUR - LIQUIDACIÓN DE APORTES CORRESPONDIENTE A HABERES ");
			cell.setCellStyle(style);
		}
	
		row = sheet.createRow(1);
		
		cell = row.createCell(0);
		cell.setCellValue("MES:  " + this.mesliquidacion);
		cell.setCellStyle(style);
		
		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellValue("SECRETARÍA DEL MERCOSUR");
		cell.setCellStyle(style);
	}

	private void writeTitleRowUTF() {
		rowCount += 3;
		
		Row row = sheet.createRow(rowCount);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		Cell cell = row.createCell(2);
		cell.setCellValue("MERCOSUR - LIQUIDACIÓN DE APORTES NÓMINA - UTF");
		cell.setCellStyle(style);
		
		rowCount += 1;
		row = sheet.createRow(rowCount);
		
		cell = row.createCell(0);
		cell.setCellValue("MES:  " + this.mesliquidacion);
		cell.setCellStyle(style);
		rowCount += 2;
	}

	private void writeHeaderRow() {
		
		Row row = sheet.createRow(rowCount);
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("Nro.");
		cell.setCellStyle(style);
		
		cell = row.createCell(1);
		cell.setCellValue("Nombre");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue("Nominales");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("Aporte Total");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("7% s/Nominal");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("14% s/Nominal + Aj.");
		cell.setCellStyle(style);

		rowCount += 1;
	}
	
	private void writeDataRowsSM() {
		int rowIni = rowCount + 1;
		
		CellStyle center = workbook.createCellStyle();
		center.setAlignment(HorizontalAlignment.CENTER);
		
		for(AportesSummary cp : lstSM) {
			Row row = sheet.createRow(rowCount++);
	
			Cell cell = row.createCell(0);
			cell.setCellValue(cp.getTarjeta());
			sheet.autoSizeColumn(1);

			cell = row.createCell(1);
			cell.setCellValue(cp.getNombre());
			sheet.autoSizeColumn(0);
						
			cell = row.createCell(2);
			cell.setCellValue(cp.getTotalNominales().doubleValue());

			
			cell = row.createCell(3);
			cell.setCellValue(cp.getAporteTotal().doubleValue());
			sheet.autoSizeColumn(3);
			
			cell = row.createCell(4);
			cell.setCellValue(cp.getAporteFun().doubleValue());
			sheet.autoSizeColumn(4);
			
			cell = row.createCell(5);
			cell.setCellValue(cp.getAporteSec().doubleValue());
			sheet.autoSizeColumn(5);
		}
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.RIGHT);
		
		Row row = sheet.createRow(rowCount);
		Cell cell = row.createCell(1);
		cell.setCellValue("TOTAL");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellFormula(String.format("SUM(C%d:C%d)", rowIni,  rowCount));
		cell = row.createCell(3);
		cell.setCellFormula(String.format("SUM(D%d:D%d)", rowIni,  rowCount));
		cell = row.createCell(4);
		cell.setCellFormula(String.format("SUM(E%d:E%d)", rowIni,  rowCount));
		cell = row.createCell(5);
		cell.setCellFormula(String.format("SUM(F%d:F%d)", rowIni,  rowCount));

	}
	
	private void writeDataRowsUTF() {
		int rowIni = rowCount + 1;
		
		CellStyle center = workbook.createCellStyle();
		center.setAlignment(HorizontalAlignment.CENTER);

		for(AportesSummary cp : lstUTF) {
			Row row = sheet.createRow(rowCount++);
			
			Cell cell = row.createCell(0);
			cell.setCellValue(cp.getTarjeta());
			sheet.autoSizeColumn(0);

			cell = row.createCell(1);
			cell.setCellValue(cp.getNombre());
			sheet.autoSizeColumn(1);
			
			cell = row.createCell(2);
			cell.setCellValue(cp.getTotalNominales().doubleValue());
			
			cell = row.createCell(3);
			cell.setCellValue(cp.getAporteTotal().doubleValue());
			sheet.autoSizeColumn(3);
			
			cell = row.createCell(4);
			cell.setCellValue(cp.getAporteFun().doubleValue());
			sheet.autoSizeColumn(4);

			cell = row.createCell(5);
			cell.setCellValue(cp.getAporteSec().doubleValue());
			sheet.autoSizeColumn(5);

		}

		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.RIGHT);

		Row row = sheet.createRow(rowCount);
		Cell cell = row.createCell(1);
		cell.setCellValue("TOTAL");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellFormula(String.format("SUM(C%d:C%d)", rowIni,  rowCount));
		cell = row.createCell(3);
		cell.setCellFormula(String.format("SUM(D%d:D%d)", rowIni,  rowCount));
		cell = row.createCell(4);
		cell.setCellFormula(String.format("SUM(E%d:E%d)", rowIni,  rowCount));
		cell = row.createCell(5);
		cell.setCellFormula(String.format("SUM(F%d:F%d)", rowIni,  rowCount));

	}
	
	public void export(HttpServletResponse response) throws IOException{
		writeTitleRowSM();
		writeHeaderRow();
		writeDataRowsSM();
		
		writeTitleRowUTF();
		writeHeaderRow();
		writeDataRowsUTF();
		
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

	public XSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}

	public List<AportesSummary> getLstSM() {
		return lstSM;
	}

	public void setLstSM(List<AportesSummary> lstSM) {
		this.lstSM = lstSM;
	}

	public List<AportesSummary> getLstUTF() {
		return lstUTF;
	}

	public void setLstUTF(List<AportesSummary> lstUTF) {
		this.lstUTF = lstUTF;
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
