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

public class AportesPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);

	private List<AportesSummary> lstAportes;
	
	private String mesLiquidacion;
	
	private Boolean aguinaldo;
	
	public AportesPdfExporter(List<AportesSummary> lstaportes, String mesLiquidacion, Boolean aguinaldo) {
		super();
		this.lstAportes = lstaportes;
		this.mesLiquidacion = mesLiquidacion;
		this.aguinaldo = aguinaldo;
	}

	private void writeTableHeader(PdfPTable table) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("Nro", font));		
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nombre", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Aporte Func", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Aporte Sec.", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Aporte Total", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Cap. Integ. Antes", font));
		table.addCell(cell);			

		cell.setPhrase(new Phrase("Cap. Integ. Actual", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Cap. Disp. Antes", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Cap. Disp. Actual", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Continúa en la pag. siguiente... " , font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setColspan(9);
		table.addCell(cell);
		table.setHeaderRows(2);
		table.setFooterRows(1);
		table.setSkipLastFooter(true);

	}
	
	private void writeTableData(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);

		for(AportesSummary apt : lstAportes) {
			PdfPCell cellNom = new PdfPCell();
			cellNom.setPhrase(new Phrase(apt.getNombre(), fontd));

			table.addCell(String.valueOf(apt.getTarjeta()));
			table.addCell(cellNom);
			table.addCell(new Phrase(getAsString(apt.getAporteFun()), fontd));
			table.addCell(new Phrase(getAsString(apt.getAporteSec()), fontd));
			table.addCell(new Phrase(getAsString(apt.getAporteTotal()), fontd));
			table.addCell(new Phrase(getAsString(apt.getCapIntegAntes()), fontd));
			table.addCell(new Phrase(getAsString(apt.getCapIntegActual()), fontd));
			table.addCell(new Phrase(getAsString(apt.getCapDispAntes()), fontd));
			table.addCell(new Phrase(getAsString(apt.getCapDispActual()), fontd));

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
		
		title = new Paragraph();
		title.setAlignment(Paragraph.ALIGN_CENTER);

		if(aguinaldo) {
			title = new Paragraph("Aportes Integrados sobre Aguinaldo en el mes: " + this.mesLiquidacion.substring(4) + "/" +
					this.mesLiquidacion.substring(0, 4), font);
			title.setAlignment(Paragraph.ALIGN_CENTER);

		}
		else {
			title = new Paragraph("Aportes Integrados para el mes: " + this.mesLiquidacion.substring(4) + "/" +
					this.mesLiquidacion.substring(0, 4), font);
			title.setAlignment(Paragraph.ALIGN_CENTER);							
		}
		
		
		document.add(title);
		
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		
		Paragraph fecha = new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(currentDate));
		fecha.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(fecha);

		PdfPTable table = new PdfPTable(9);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f});
		table.setSpacingBefore(10);
		
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();

		
	}
	
	public List<AportesSummary> getLstAportes() {
		return lstAportes;
	}

	public void setLstAportes(List<AportesSummary> lstAportes) {
		this.lstAportes = lstAportes;
	}

	public String getMesLiquidacion() {
		return mesLiquidacion;
	}

	public void setMesLiquidacion(String mesLiquidacion) {
		this.mesLiquidacion = mesLiquidacion;
	}

	public Boolean getAguinaldo() {
		return aguinaldo;
	}

	public void setAguinaldo(Boolean aguinaldo) {
		this.aguinaldo = aguinaldo;
	}
	
}
