package org.mercosur.fondoPrevision.excel;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.mercosur.fondoPrevision.dto.ResultadoDistribSummary;

public class DistribExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<ResultadoDistribSummary> lstDistrib;
	private BigDecimal totalNumeralesAnt;
	private BigDecimal totalNumeralesAct;
	private BigDecimal sumaAdistrib;
	private String mesliquidacion;
	private int rowCount;

	public DistribExcelExporter(List<ResultadoDistribSummary> lstDistrib, BigDecimal totalNumeralesAnt, BigDecimal totalNumeralesAct, String mesliquidacion, BigDecimal sumaAdistrib) {
		this.setLstDistrib(lstDistrib);
		this.setTotalNumeralesAnt(totalNumeralesAnt);
		this.setTotalNumeralesAct(totalNumeralesAct);
		this.mesliquidacion = mesliquidacion;
		this.setSumaAdistrib(sumaAdistrib);
		
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Distribucion");
		this.rowCount = 4;

	}

	private void writeTitleRow() {
		Row row = sheet.createRow(1);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		Cell cell = row.createCell(2);
		cell.setCellValue("MERCOSUR - RESULTADO DISTRIBUCION UTILIDADES CORRESPONDIENTE AL MES:  " + this.mesliquidacion);
		cell.setCellStyle(style);
		
	}

	private void writeDatosAgregados() {
		rowCount += 3;
		
		Row row = sheet.createRow(rowCount);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		Cell cell = row.createCell(1);
		cell.setCellValue("Capital Integrado Anterior Total: ");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue(this.totalNumeralesAnt.doubleValue());
		
		rowCount += 1;
		row = sheet.createRow(rowCount);
		
		cell = row.createCell(1);
		cell.setCellValue("Capital Integrado Actual Total: ");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue(this.totalNumeralesAct.doubleValue());

		
		cell = row.createCell(4);
		cell.setCellValue("Suma a Distribuir: ");
		cell.setCellStyle(style);
		
		cell = row.createCell(5);
		cell.setCellValue(this.sumaAdistrib.doubleValue());

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
		cell.setCellValue("Nro");
		cell.setCellStyle(style);
		
		cell = row.createCell(1);
		cell.setCellValue("Nombre");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue("Disponible Anterior");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("Disponible Actual");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("Cap. Integrado Anterior");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("Cap. Integrado Actual");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("Pct. Funcionario");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("Result. Distrib.");
		cell.setCellStyle(style);

		rowCount += 1;
	}
	
	private void writeDataRows() {
		int rowIni = rowCount + 1;
		
		CellStyle center = workbook.createCellStyle();
		center.setAlignment(HorizontalAlignment.CENTER);
		
		for(ResultadoDistribSummary rd : lstDistrib) {
			Row row = sheet.createRow(rowCount++);
			
			Cell cell = row.createCell(0);
			cell.setCellValue(rd.getTarjeta());
			
			cell = row.createCell(1);
			cell.setCellValue(rd.getNombre());
			sheet.autoSizeColumn(1);
			
			cell = row.createCell(2);
			cell.setCellValue(rd.getCapitalDispAntes().doubleValue());
			
			cell = row.createCell(3);
			cell.setCellValue(rd.getCapitalDispActual().doubleValue());
			sheet.autoSizeColumn(3);
			

			cell = row.createCell(4);
			cell.setCellValue(rd.getCapitalIntegAntes().doubleValue());
			sheet.autoSizeColumn(4);

			cell = row.createCell(5);
			cell.setCellValue(rd.getCapitalIntegActual().doubleValue());
			sheet.autoSizeColumn(5);

			cell = row.createCell(6);
			cell.setCellValue(rd.getPctfuncionario().doubleValue());
			sheet.autoSizeColumn(6);
		
			cell = row.createCell(7);
			cell.setCellValue(rd.getResultadoDistrib().doubleValue());
			sheet.autoSizeColumn(7);

		}
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.RIGHT);
		
		Row row = sheet.createRow(rowCount);
		Cell cell = row.createCell(1);
		cell.setCellValue("TOTALES");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellFormula(String.format("SUM(C%d:C%d)", rowIni,  rowCount));

		cell = row.createCell(3);
		cell.setCellFormula(String.format("SUM(D%d:D%d)", rowIni,  rowCount));

		cell = row.createCell(4);
		cell.setCellFormula(String.format("SUM(E%d:E%d)", rowIni,  rowCount));

		cell = row.createCell(5);
		cell.setCellFormula(String.format("SUM(F%d:F%d)", rowIni,  rowCount));

		cell = row.createCell(7);
		cell.setCellFormula(String.format("SUM(H%d:H%d)", rowIni,  rowCount));

	}
	
	
	public void export(HttpServletResponse response) throws IOException{
		writeTitleRow();
		writeDatosAgregados();
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

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public List<ResultadoDistribSummary> getLstDistrib() {
		return lstDistrib;
	}

	public void setLstDistrib(List<ResultadoDistribSummary> lstDistrib) {
		this.lstDistrib = lstDistrib;
	}

	public BigDecimal getTotalNumeralesAnt() {
		return totalNumeralesAnt;
	}

	public void setTotalNumeralesAnt(BigDecimal totalNumeralesAnt) {
		this.totalNumeralesAnt = totalNumeralesAnt;
	}

	public BigDecimal getTotalNumeralesAct() {
		return totalNumeralesAct;
	}

	public void setTotalNumeralesAct(BigDecimal totalNumeralesAct) {
		this.totalNumeralesAct = totalNumeralesAct;
	}

	public BigDecimal getSumaAdistrib() {
		return sumaAdistrib;
	}

	public void setSumaAdistrib(BigDecimal sumaAdistrib) {
		this.sumaAdistrib = sumaAdistrib;
	}
}
