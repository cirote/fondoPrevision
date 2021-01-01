package org.mercosur.fondoPrevision.pdfs;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.CuotasPagas;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class DescCuotasPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);
	public static final Font BOLD = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);

	private List<CuotasPagas> lstSM;
	
	private List<CuotasPagas> lstUTF;
	
	private String mesliquidacion;
	
	public DescCuotasPdfExporter(List<CuotasPagas> lstSM, List<CuotasPagas> lstUTF, String mesliquidacion) {
		this.lstSM = lstSM;
		this.lstUTF = lstUTF;
		this.mesliquidacion = mesliquidacion;
	}

	private void writeTableHeader(PdfPTable table) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);
		
		cell.setPhrase(new Phrase("Nombre", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nro. Prst.", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Fecha Prst.", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Cuota", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("U$S", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Continúa en la pag. siguiente... " , font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setColspan(5);
		table.addCell(cell);
		table.setHeaderRows(2);
		table.setFooterRows(1);
		table.setSkipLastFooter(true);

	}
	
	private void writeTableDataSM(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);
		BigDecimal total = BigDecimal.ZERO;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		for(CuotasPagas cp : lstSM) {
			PdfPCell cellNom = new PdfPCell();
			cellNom.setPhrase(new Phrase(cp.getNombre(), fontd));
			table.addCell(cellNom);
			table.addCell(new Phrase(cp.getNroPrestamo().toString(), fontd));
			table.addCell(new Phrase(formatter.format(cp.getFechaPrestamo()), fontd));
			table.addCell(new Phrase(cp.getCuotasPagas().toString() + '/' + cp.getCantCuotas().toString(), fontd));
			table.addCell(new Phrase(getAsString(cp.getCuotaTotal()), fontd));
			total = total.add(cp.getCuotaTotal());
		}
		
		table.addCell(new Phrase(Chunk.NEWLINE));
		table.addCell(new Phrase(Chunk.NEWLINE));
		table.addCell(new Phrase("TOTAL", BOLD));
		table.addCell(new Phrase(Chunk.NEWLINE));
		table.addCell(new Phrase(getAsString(total), fontd));
		
	}


	private void writeTableDataUTF(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);
		BigDecimal total = BigDecimal.ZERO;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		for(CuotasPagas cp : lstUTF) {
			PdfPCell cellNom = new PdfPCell();
			cellNom.setPhrase(new Phrase(cp.getNombre(), fontd));
			table.addCell(cellNom);
			table.addCell(new Phrase(cp.getNroPrestamo().toString(), fontd));
			table.addCell(new Phrase(formatter.format(cp.getFechaPrestamo()), fontd));
			table.addCell(new Phrase(cp.getCuotasPagas().toString() + '/' + cp.getCantCuotas().toString(), fontd));
			table.addCell(new Phrase(getAsString(cp.getCuotaTotal()), fontd));
			total = total.add(cp.getCuotaTotal());
		}
		table.addCell(new Phrase(Chunk.NEWLINE));
		table.addCell(new Phrase(Chunk.NEWLINE));
		table.addCell(new Phrase("TOTAL", BOLD));
		table.addCell(new Phrase(Chunk.NEWLINE));
		table.addCell(new Phrase(getAsString(total), fontd));

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

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document =  new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.DARK_GRAY);
		font.setSize(12);
		
		Paragraph title = new Paragraph();
		title = new Paragraph("MERCOSUR - DESCUENTO CUOTAS NOMINA", BOLD_UNDERLINED);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);
		document.add(Chunk.NEWLINE);
		
		title = new Paragraph();
		title.setAlignment(Paragraph.ALIGN_CENTER);

		title = new Paragraph("MES: " +  this.mesliquidacion, font);
		title.setAlignment(Paragraph.ALIGN_CENTER);			
		
		document.add(title);
				
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		
		Paragraph fecha = new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(currentDate));
		fecha.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(fecha);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {2.0f, 1.0f, 1.0f, 1.0f, 1.0f});
		table.setSpacingBefore(10);
		
		title = new Paragraph("SECRETARIA DEL MERCOSUR", font);
		title.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(title);
		
		writeTableHeader(table);
		writeTableDataSM(table);
		document.add(table);
		
		document.newPage();
		
		table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {2.0f, 1.0f, 1.0f, 1.0f, 1.0f});
		table.setSpacingBefore(10);
		
		title = new Paragraph("MERCOSUR - DESCUENTO CUOTAS NOMINA", BOLD_UNDERLINED);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);
		document.add(Chunk.NEWLINE);
		
		title = new Paragraph();
		title.setAlignment(Paragraph.ALIGN_CENTER);

		title = new Paragraph("MES: " +  this.mesliquidacion, font);
		title.setAlignment(Paragraph.ALIGN_CENTER);			
		
		document.add(title);

		title = new Paragraph("UNIDAD TÉCNICA FOSEM", font);
		title.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(title);

		writeTableHeader(table);
		writeTableDataUTF(table);
		document.add(table);
		
		document.close();

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
}
