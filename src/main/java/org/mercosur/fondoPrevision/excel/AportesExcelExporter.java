package org.mercosur.fondoPrevision.excel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mercosur.fondoPrevision.dto.AportesSummary;

public class AportesExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<AportesSummary> lstAportes;
	private String mesliquidacion;
	
	public AportesExcelExporter(List<AportesSummary> lst, String mesliquidacion) {
		this.lstAportes = lst;
		this.setMesliquidacion(mesliquidacion);
		
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("aportes");
	}
	
	private void writeTitleRow() {
		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		Cell cell = row.createCell(0);
		cell.setCellValue("APORTES INTEGRADOS EN EL MES " + mesliquidacion);
		cell.setCellStyle(style);
		
	}

	private void writeHeaderRow() {
		Row row = sheet.createRow(1);
		
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
		cell.setCellValue("Fecha");
		cell.setCellStyle(style);
		
		cell = row.createCell(3);
		cell.setCellValue("Aporte Func.");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("Aporte Sec.");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("Aporte Total");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("Disponible Ant.");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("Disponible Act.");
		cell.setCellStyle(style);

		cell = row.createCell(8);
		cell.setCellValue("Integrado Ant.");
		cell.setCellStyle(style);

		cell = row.createCell(9);
		cell.setCellValue("Integrado Act.");
		cell.setCellStyle(style);

	}
	

	private void writeDataRows() {
		int rowCount = 2;
		for(AportesSummary ap : lstAportes) {
			Row row = sheet.createRow(rowCount++);
			
			Cell cell = row.createCell(0);
			cell.setCellValue(ap.getTarjeta());
			sheet.autoSizeColumn(0);
			
			cell = row.createCell(1);
			cell.setCellValue(ap.getNombre());
			sheet.autoSizeColumn(1);

			cell = row.createCell(2);
			cell.setCellValue(ap.getFecha());
			sheet.autoSizeColumn(2);

			cell = row.createCell(3);
			cell.setCellValue(ap.getAporteFun().doubleValue());
			sheet.autoSizeColumn(3);

			cell = row.createCell(4);
			cell.setCellValue(ap.getAporteSec().doubleValue());
			sheet.autoSizeColumn(4);

			cell = row.createCell(5);
			cell.setCellValue(ap.getAporteTotal().doubleValue());
			sheet.autoSizeColumn(5);

			cell = row.createCell(6);
			cell.setCellValue(ap.getCapDispAntes().doubleValue());
			sheet.autoSizeColumn(6);


			cell = row.createCell(7);
			cell.setCellValue(ap.getCapDispActual().doubleValue());
			sheet.autoSizeColumn(7);
			
			cell = row.createCell(8);
			cell.setCellValue(ap.getCapIntegAntes().doubleValue());
			sheet.autoSizeColumn(8);

			cell = row.createCell(9);
			cell.setCellValue(ap.getCapIntegActual().doubleValue());
			sheet.autoSizeColumn(9);

		}

	}
	
	public void export(HttpServletResponse response) throws IOException {

		writeTitleRow();
		writeHeaderRow();
		writeDataRows();
		
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
	public List<AportesSummary> getLstAportes() {
		return lstAportes;
	}
	public void setLstAportes(List<AportesSummary> lstAportes) {
		this.lstAportes = lstAportes;
	}


	public String getMesliquidacion() {
		return mesliquidacion;
	}


	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}
	
}
