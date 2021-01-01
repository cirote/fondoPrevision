package org.mercosur.fondoPrevision.pdfs;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.ResultadoDistribSummary;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class DistribucionPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);
	public static final Font BOLD9 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
	public static final Font NORMAL9 = FontFactory.getFont(FontFactory.HELVETICA, 9);
	public static final Font BOLD = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
	public static final Font NORMAL10 = FontFactory.getFont(FontFactory.HELVETICA, 10);

	private List<ResultadoDistribSummary> lstDist;
	private BigDecimal totalNumeralesAnt;
	private BigDecimal totalNumeralesAct;
	private String mesDistrib;
	private BigDecimal sumaAdistrib;
	
	public DistribucionPdfExporter(List<ResultadoDistribSummary> lstDist, BigDecimal totalNumeralesAnt, 
			BigDecimal totalNumeralesAct, String mesDistrib, BigDecimal sumaAdistrib) {
		this.lstDist = lstDist;
		this.mesDistrib = mesDistrib;
		this.sumaAdistrib = sumaAdistrib;
		this.totalNumeralesAct = totalNumeralesAct;
		this.totalNumeralesAnt = totalNumeralesAnt;
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
	
	private void writeDocHeader(PdfPTable tenca) {
		
		PdfPCell cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);

		Paragraph p = new Paragraph();
		p.add(new Phrase("Capital Integrado Total Anterior:  ", BOLD));
		p.add(new Phrase(getAsString(this.totalNumeralesAnt), NORMAL10));
		
		cell.addElement(p);
		tenca.addCell(cell);
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		
		p = new Paragraph();
		p.add(new Phrase("Capital Integrado Total Actual:  ", BOLD));
		p.add(new Phrase(getAsString(this.totalNumeralesAct), NORMAL10));
		
		cell.addElement(p);
		tenca.addCell(cell);
				
	}

	private void writeTableHeader(PdfPTable table) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		font.setSize(10);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("Nro.", font));		
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nombre", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Disp. Anterior", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Disp. Actual", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Integrado Anterior", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Integrado Actual", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Pct. Funcionario", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Resultado Dist.", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Continúa en la pag. siguiente... " , font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setColspan(8);
		table.addCell(cell);
		table.setHeaderRows(2);
		table.setFooterRows(1);
		table.setSkipLastFooter(true);
	}
	
	private void writeTableData(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

		for(ResultadoDistribSummary rds : lstDist) {
			PdfPCell cellNom = new PdfPCell();
			cellNom.setPhrase(new Phrase(rds.getNombre(), NORMAL10));

			table.addCell(String.valueOf(rds.getTarjeta()));
			table.addCell(cellNom);
			table.addCell(new Phrase(getAsString(rds.getCapitalDispAntes()), NORMAL10));
			table.addCell(new Phrase(getAsString(rds.getCapitalDispActual()), NORMAL10));
			table.addCell(new Phrase(getAsString(rds.getCapitalIntegAntes()), NORMAL10));
			table.addCell(new Phrase(getAsString(rds.getCapitalIntegActual()), NORMAL10));
			table.addCell(new Phrase(getAsString(rds.getPctfuncionario()), NORMAL10));
			table.addCell(new Phrase(getAsString(rds.getResultadoDistrib()), NORMAL10));
		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document =  new Document(PageSize.A4.rotate());
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.DARK_GRAY);
		font.setSize(12);
		
		Paragraph title = new Paragraph();
		title = new Paragraph("FONDO DE PREVISIÓN DE LOS FUNCIONARIOS DE LA SM", BOLD_UNDERLINED);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		document.add(Chunk.NEWLINE);

		title = new Paragraph("Distribución de Utilidades del mes: " + this.mesDistrib.substring(4) + "/" +
				this.mesDistrib.substring(0, 4), font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		
		Paragraph fecha = new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(currentDate));
		fecha.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(fecha);

		PdfPTable tenca = new PdfPTable(2);
		tenca.setWidthPercentage(100.0f);
		tenca.setWidths(new float[] {50.0f, 50.0f});
		tenca.setSpacingBefore(10);
		writeDocHeader(tenca);
		document.add(tenca);
		
		Paragraph p = new Paragraph();
		p.add(new Phrase("Total a Distribuir:  ", BOLD));
		p.add(new Phrase(getAsString(this.sumaAdistrib), NORMAL10));
		p.setAlignment(Paragraph.ALIGN_LEFT);
		p.setSpacingBefore(10);
		document.add(p);
		
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f});
		table.setSpacingBefore(15);
		
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();

	}


	public List<ResultadoDistribSummary> getLstDist() {
		return lstDist;
	}


	public void setLstDist(List<ResultadoDistribSummary> lstDist) {
		this.lstDist = lstDist;
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


	public String getMesDistrib() {
		return mesDistrib;
	}


	public void setMesDistrib(String mesDistrib) {
		this.mesDistrib = mesDistrib;
	}


	public BigDecimal getSumaAdistrib() {
		return sumaAdistrib;
	}


	public void setSumaAdistrib(BigDecimal sumaAdistrib) {
		this.sumaAdistrib = sumaAdistrib;
	}

}
