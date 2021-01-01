package org.mercosur.fondoPrevision.excel;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
import org.mercosur.fondoPrevision.dto.CuotasPagas;

public class DescCuotasExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<CuotasPagas> lstSM;
	private List<CuotasPagas> lstUTF;
	private String mesliquidacion;
	private int rowCount;

	public DescCuotasExcelExporter(List<CuotasPagas> lstSM, List<CuotasPagas> lstUTF, String mesliquidacion) {
		this.lstSM = lstSM;
		this.lstUTF = lstUTF;
		this.mesliquidacion = mesliquidacion;
		
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("desc.Cuotas");
		this.rowCount = 4;

	}

	private void writeTitleRowSM() {
		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		Cell cell = row.createCell(2);
		cell.setCellValue("MERCOSUR - DESCUENTO CUOTAS NÓMINA ");
		cell.setCellStyle(style);
		
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
		cell.setCellValue("MERCOSUR - DESCUENTO CUOTAS NÓMINA - UTF");
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
		cell.setCellValue("Nombre");
		cell.setCellStyle(style);
		
		cell = row.createCell(1);
		cell.setCellValue("Nro. Prst.");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue("Fecha Prst.");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("Cuota");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("U$S");
		cell.setCellStyle(style);

		rowCount += 1;
	}
	
	private void writeDataRowsSM() {
		int rowIni = rowCount + 1;
		
		CellStyle center = workbook.createCellStyle();
		center.setAlignment(HorizontalAlignment.CENTER);
		
		for(CuotasPagas cp : lstSM) {
			Row row = sheet.createRow(rowCount++);
			
			Cell cell = row.createCell(0);
			cell.setCellValue(cp.getNombre());
			sheet.autoSizeColumn(0);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			
			cell = row.createCell(1);
			cell.setCellValue(cp.getNroPrestamo());
			sheet.autoSizeColumn(1);
			
			cell = row.createCell(2);
			cell.setCellValue(formatter.format(cp.getFechaPrestamo()));

			
			cell = row.createCell(3);
			cell.setCellValue(cp.getCuotasPagas().toString()+'/'+cp.getCantCuotas().toString());
			cell.setCellStyle(center);
			sheet.autoSizeColumn(3);
			
			cell = row.createCell(4);
			cell.setCellValue(cp.getCuotaTotal().doubleValue());
			sheet.autoSizeColumn(4);
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
		
		cell = row.createCell(4);
		cell.setCellFormula(String.format("SUM(E%d:E%d)", rowIni,  rowCount));

	}
	
	private void writeDataRowsUTF() {
		int rowIni = rowCount + 1;
		
		CellStyle center = workbook.createCellStyle();
		center.setAlignment(HorizontalAlignment.CENTER);

		for(CuotasPagas cp : lstUTF) {
			Row row = sheet.createRow(rowCount++);
			
			Cell cell = row.createCell(0);
			cell.setCellValue(cp.getNombre());
			sheet.autoSizeColumn(0);

			cell = row.createCell(1);
			cell.setCellValue(cp.getNroPrestamo());
			sheet.autoSizeColumn(1);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			cell = row.createCell(2);
			cell.setCellValue(formatter.format(cp.getFechaPrestamo()));
			
			cell = row.createCell(3);
			cell.setCellValue(cp.getCuotasPagas().toString()+'/'+cp.getCantCuotas().toString());
			cell.setCellStyle(center);
			sheet.autoSizeColumn(3);
			
			cell = row.createCell(4);
			cell.setCellValue(cp.getCuotaTotal().doubleValue());
			sheet.autoSizeColumn(4);
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
		
		cell = row.createCell(4);
		cell.setCellFormula(String.format("SUM(E%d:E%d)", rowIni,  rowCount));

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

	public List<CuotasPagas> getLstSM() {
		return lstSM;
	}

	public void setLstSM(List<CuotasPagas> lstSM) {
		this.lstSM = lstSM;
	}

	public List<CuotasPagas> getLstUTF() {
		return lstUTF;
	}

	public void setLstUTF(List<CuotasPagas> lstUTF) {
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
