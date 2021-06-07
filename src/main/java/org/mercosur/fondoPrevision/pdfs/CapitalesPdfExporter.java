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

import org.mercosur.fondoPrevision.dto.CapitalesForDisplay;

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

public class CapitalesPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);

	private List<CapitalesForDisplay> lstCapitales;
	private String mesLiquidacion;
	private Boolean conDistribucion;
	
	public CapitalesPdfExporter(List<CapitalesForDisplay> lstCapitales, String mesliquidacion, Boolean conDistribucion) {
		super();
		this.lstCapitales = lstCapitales;
		this.mesLiquidacion = mesliquidacion;
		this.conDistribucion = conDistribucion;
	}

	private void writeTableHeader(PdfPTable table) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("N°", font));		
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nombre", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Cap. Disp. Anterior", font));
		table.addCell(cell);
		
		if(conDistribucion) {
			cell.setPhrase(new Phrase("Result.Distribucion", font));
			table.addCell(cell);
		}
		
		cell.setPhrase(new Phrase("Amortización", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Cancelaciones", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Prsts. Nuevos", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Total Mov.", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Aportes", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Otros", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Retiros", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Cap. Disp. Actual", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Cap. Integrado", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Continúa en la pag. siguiente... " , font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		if(conDistribucion) {
			cell.setColspan(13);
		}
		else {
			cell.setColspan(12);
		}
		table.addCell(cell);
		table.setHeaderRows(2);
		table.setFooterRows(1);
		table.setSkipLastFooter(true);

	}
	
	private void writeTableData(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);
		
		for(CapitalesForDisplay cfd : lstCapitales) {
			table.addCell(new Phrase(cfd.getNrofuncionario().toString(), fontd));
			PdfPCell cellNom = new PdfPCell();
			cellNom.setPhrase(new Phrase(cfd.getNombre(), fontd));
			table.addCell(cellNom);
			
			table.addCell(new Phrase(getAsString(cfd.getCapitalDispAnterior()), fontd));
			if(conDistribucion) {
				table.addCell(new Phrase(getAsString(cfd.getImporteDistribucion()), fontd));
			}
			table.addCell(new Phrase(getAsString(cfd.getAmortizacion()), fontd));
			table.addCell(new Phrase(getAsString(cfd.getCancelaciones()), fontd));
			table.addCell(new Phrase(getAsString(cfd.getPrstnuevos()), fontd));
			table.addCell(new Phrase(getAsString(cfd.getTotalMovPrst()), fontd));
			table.addCell(new Phrase(getAsString(cfd.getTotalMovAportes()), fontd));
			table.addCell(new Phrase(getAsString(cfd.getOtros()), fontd));
			table.addCell(new Phrase(getAsString(cfd.getRetiros()), fontd));
			table.addCell(new Phrase(getAsString(cfd.getCapitalDispActual()), fontd));
			table.addCell(new Phrase(getAsString(cfd.getCapitalIntegActual()), fontd));
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

		title = new Paragraph("Capitales Disponibles y Numerales correspondiente a: " + this.mesLiquidacion, font);
		title.setAlignment(Paragraph.ALIGN_CENTER);			
		
		document.add(title);
				
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		
		Paragraph fecha = new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(currentDate));
		fecha.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(fecha);

		PdfPTable table = new PdfPTable(12);
		if(conDistribucion) {
			table = new PdfPTable(13);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] {0.5f, 2.0f, 1.2f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.8f, 1.0f, 1.2f, 1.2f});
			table.setSpacingBefore(10);
		}
		else {
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] {1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f});
			table.setSpacingBefore(10);			
		}
		
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();

		
	}

	public List<CapitalesForDisplay> getLstCapitales() {
		return lstCapitales;
	}

	public void setLstCapitales(List<CapitalesForDisplay> lstCapitales) {
		this.lstCapitales = lstCapitales;
	}

	public String getMesLiquidacion() {
		return mesLiquidacion;
	}

	public void setMesLiquidacion(String mesLiquidacion) {
		this.mesLiquidacion = mesLiquidacion;
	}
}
