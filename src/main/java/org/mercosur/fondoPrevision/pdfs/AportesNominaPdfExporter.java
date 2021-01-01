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

import org.mercosur.fondoPrevision.dto.AportesSummary;
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

public class AportesNominaPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);
	public static final Font BOLD = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);

	private List<AportesSummary> lstSM;
	
	private List<AportesSummary> lstUTF;
	
	private String mesliquidacion;
	
	public AportesNominaPdfExporter(List<AportesSummary> lstSM, List<AportesSummary> lstUTF, String mesliquidacion) {
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
		
		cell.setPhrase(new Phrase("Nro.", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Nombre", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nominales", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Aporte Total", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("7% s/Nominal", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("14% s/Nominal + Aj.", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Continúa en la pag. siguiente... " , font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setColspan(6);
		table.addCell(cell);
		table.setHeaderRows(2);
		table.setFooterRows(1);
		table.setSkipLastFooter(true);

	}
	
	private void writeTableDataSM(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);
		BigDecimal totalNominales = BigDecimal.ZERO;
		BigDecimal totalAportes = BigDecimal.ZERO;
		BigDecimal totalapfun = BigDecimal.ZERO;
		BigDecimal totalapsec = BigDecimal.ZERO;
		
		for(AportesSummary cp : lstSM) {
			table.addCell(new Phrase(cp.getTarjeta().toString(), fontd));
			PdfPCell cellNom = new PdfPCell();
			cellNom.setPhrase(new Phrase(cp.getNombre(), fontd));
			table.addCell(cellNom);
			table.addCell(new Phrase(getAsString(cp.getTotalNominales()), fontd));
			table.addCell(new Phrase(getAsString(cp.getAporteTotal()), fontd));
			table.addCell(new Phrase(getAsString(cp.getAporteFun()), fontd));
			table.addCell(new Phrase(getAsString(cp.getAporteSec()), fontd));
			totalNominales = totalNominales.add(cp.getTotalNominales());
			totalAportes = totalAportes.add(cp.getAporteTotal());
			totalapfun = totalapfun.add(cp.getAporteFun());
			totalapsec = totalapsec.add(cp.getAporteSec());
		}
		
		table.addCell(new Phrase(Chunk.NEWLINE));
		table.addCell(new Phrase("TOTAL", BOLD));
		table.addCell(new Phrase(getAsString(totalNominales), fontd));
		table.addCell(new Phrase(getAsString(totalAportes), fontd));
		table.addCell(new Phrase(getAsString(totalapfun), fontd));
		table.addCell(new Phrase(getAsString(totalapsec), fontd));
		
	}


	private void writeTableDataUTF(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);
		BigDecimal totalNominales = BigDecimal.ZERO;
		BigDecimal totalAportes = BigDecimal.ZERO;
		BigDecimal totalapfun = BigDecimal.ZERO;
		BigDecimal totalapsec = BigDecimal.ZERO;
		for(AportesSummary cp : lstUTF) {
			table.addCell(new Phrase(cp.getTarjeta().toString(), fontd));
			PdfPCell cellNom = new PdfPCell();
			cellNom.setPhrase(new Phrase(cp.getNombre(), fontd));
			table.addCell(cellNom);
			table.addCell(new Phrase(getAsString(cp.getTotalNominales()), fontd));
			table.addCell(new Phrase(getAsString(cp.getAporteTotal()), fontd));
			table.addCell(new Phrase(getAsString(cp.getAporteFun()), fontd));
			table.addCell(new Phrase(getAsString(cp.getAporteSec()), fontd));
			totalNominales = totalNominales.add(cp.getTotalNominales());
			totalAportes = totalAportes.add(cp.getAporteTotal());
			totalapfun = totalapfun.add(cp.getAporteFun());
			totalapsec = totalapsec.add(cp.getAporteSec());
		}
		table.addCell(new Phrase(Chunk.NEWLINE));
		table.addCell(new Phrase("TOTAL", BOLD));
		table.addCell(new Phrase(getAsString(totalNominales), fontd));
		table.addCell(new Phrase(getAsString(totalAportes), fontd));
		table.addCell(new Phrase(getAsString(totalapfun), fontd));
		table.addCell(new Phrase(getAsString(totalapsec), fontd));

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
		
		String medio = "06";
		String completo = "12";
		
		Paragraph title = new Paragraph();
		if(this.mesliquidacion.substring(0,2).equals(medio) || this.mesliquidacion.substring(0,2).equals(completo)) {
			title = new Paragraph("MERCOSUR - LIQUIDACIÓN DE APORTES CORRESPONDIENTE A SUELDO Y AGUINALDO", BOLD_UNDERLINED);
		}
		else {
			title = new Paragraph("MERCOSUR - LIQUIDACIÓN DE APORTES CORRESPONDIENTE A HABERES", BOLD_UNDERLINED);
		}
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

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f});
		table.setSpacingBefore(10);
		
		title = new Paragraph("SECRETARIA DEL MERCOSUR", font);
		title.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(title);
		
		writeTableHeader(table);
		writeTableDataSM(table);
		document.add(table);
		
		document.newPage();
		
		table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f});
		table.setSpacingBefore(10);

		if(this.mesliquidacion.substring(0,2).equals(medio) || this.mesliquidacion.substring(0,2).equals(completo)) {
			title = new Paragraph("MERCOSUR - LIQUIDACIÓN DE APORTES CORRESPONDIENTE A SUELDO Y AGUINALDO", BOLD_UNDERLINED);
		}
		else {
			title = new Paragraph("MERCOSUR - LIQUIDACIÓN DE APORTES CORRESPONDIENTE A HABERES", BOLD_UNDERLINED);
		}

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
}
