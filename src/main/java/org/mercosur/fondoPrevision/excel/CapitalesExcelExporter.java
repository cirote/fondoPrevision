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
import org.mercosur.fondoPrevision.dto.CapitalesForDisplay;

public class CapitalesExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<CapitalesForDisplay> lstCapitales;
	private String mesliquidacion;
	private Boolean conDistribucion;
	
	public CapitalesExcelExporter(List<CapitalesForDisplay> lst, String mesliquidacion, Boolean conDistribucion) {
		this.setLstCapitales(lst);
		this.setMesliquidacion(mesliquidacion);
		this.conDistribucion = conDistribucion;
		
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
		cell.setCellValue("CAPITALES DISPONIBLES Y NUMERALES " + mesliquidacion);
		cell.setCellStyle(style);
		
	}
	
	private void writeHeaderRow() {
		Row row = sheet.createRow(1);
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		int col = 0;
		
		Cell cell = row.createCell(col);
		cell.setCellValue("Nro.");
		cell.setCellStyle(style);
		col += 1;
		
		cell = row.createCell(col);
		cell.setCellValue("Nombre");
		cell.setCellStyle(style);
		col += 1;
		
		cell = row.createCell(col++);
		cell.setCellValue("Disp. Anterior");
		cell.setCellStyle(style);
		
		if(conDistribucion) {
			cell = row.createCell(col++);
			cell.setCellValue("Result. Distrib.");
			cell.setCellStyle(style);
		}
		
		cell = row.createCell(col++);
		cell.setCellValue("Amortiza.");
		cell.setCellStyle(style);

		cell = row.createCell(col++);
		cell.setCellValue("Cancelaciones");
		cell.setCellStyle(style);

		cell = row.createCell(col++);
		cell.setCellValue("Prsts. Nuevos");
		cell.setCellStyle(style);

		cell = row.createCell(col++);
		cell.setCellValue("Total Mov.");
		cell.setCellStyle(style);

		cell = row.createCell(col++);
		cell.setCellValue("Total Mov. Nominal");
		cell.setCellStyle(style);

		cell = row.createCell(col++);
		cell.setCellValue("Disp. Actual");
		cell.setCellStyle(style);

		cell = row.createCell(col++);
		cell.setCellValue("Cap. Integrado");
		cell.setCellStyle(style);

	}
	
	private void writeDataRows() {
		int rowCount = 2;
		for(CapitalesForDisplay ap : lstCapitales) {
			Row row = sheet.createRow(rowCount++);
			int col = 0;
			
			Cell cell = row.createCell(col++);
			cell.setCellValue(ap.getNrofuncionario());
			
			cell = row.createCell(col);
			cell.setCellValue(ap.getNombre());
			sheet.autoSizeColumn(col++);

			cell = row.createCell(col);
			cell.setCellValue(ap.getCapitalDispAnterior().doubleValue());
			sheet.autoSizeColumn(col++);

			if(conDistribucion) {
				cell = row.createCell(col);
				cell.setCellValue(ap.getImporteDistribucion().doubleValue());
				sheet.autoSizeColumn(col++);				
			}

			cell = row.createCell(col);
			cell.setCellValue(ap.getAmortizacion().doubleValue());
			sheet.autoSizeColumn(col++);

			cell = row.createCell(col);
			cell.setCellValue(ap.getCancelaciones().doubleValue());
			sheet.autoSizeColumn(col++);

			cell = row.createCell(col);
			cell.setCellValue(ap.getPrstnuevos().doubleValue());
			sheet.autoSizeColumn(col++);

			cell = row.createCell(col);
			cell.setCellValue(ap.getTotalMovPrst().doubleValue());
			sheet.autoSizeColumn(col++);

			cell = row.createCell(col);
			cell.setCellValue(ap.getTotalMovAportes().doubleValue());
			sheet.autoSizeColumn(col++);
			
			cell = row.createCell(col);
			cell.setCellValue(ap.getCapitalDispActual().doubleValue());
			sheet.autoSizeColumn(col++);

			cell = row.createCell(col);
			cell.setCellValue(ap.getCapitalIntegActual().doubleValue());
			sheet.autoSizeColumn(col++);

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


	public String getMesliquidacion() {
		return mesliquidacion;
	}


	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}


	public List<CapitalesForDisplay> getLstCapitales() {
		return lstCapitales;
	}


	public void setLstCapitales(List<CapitalesForDisplay> lstCapitales) {
		this.lstCapitales = lstCapitales;
	}
	
}
