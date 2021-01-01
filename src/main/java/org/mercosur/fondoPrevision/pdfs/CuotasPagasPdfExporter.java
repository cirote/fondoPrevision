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

public class CuotasPagasPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);

	private List<CuotasPagas> lstCuotas;
	
	private String mesLiquidacion;

	public CuotasPagasPdfExporter(List<CuotasPagas> lstCuotas, String mesliquidacion) {
		super();
		this.lstCuotas = lstCuotas;
		this.mesLiquidacion = mesliquidacion;
	}
	
	private void writeTableHeader(PdfPTable table) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("Nros", font));		
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nombre", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nro. Prst.", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Cuota Cap.", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Cuota Int.", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Cuota Total", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Cuotas Pagadas", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Saldo Prst.", font));
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
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);

		for(CuotasPagas apt : lstCuotas) {
			PdfPCell cellNom = new PdfPCell();
			cellNom.setPhrase(new Phrase(apt.getNombre(), fontd));

			table.addCell(String.valueOf(apt.getTarjeta()));
			table.addCell(cellNom);
			table.addCell(new Phrase(apt.getNroPrestamo().toString(), fontd));
			table.addCell(new Phrase(getAsString(apt.getCuotaCap()), fontd));
			table.addCell(new Phrase(getAsString(apt.getCuotaInteres()), fontd));
			table.addCell(new Phrase(getAsString(apt.getCuotaTotal()), fontd));
			table.addCell(new Phrase(apt.getCuotasPagas().toString(), fontd));
			table.addCell(new Phrase(getAsString(apt.getSaldoPrestamo()), fontd));

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

	public void export(HttpServletResponse response) throws DocumentException, IOException{
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

		title = new Paragraph("Cuotas de Prestamos, Pagadas para el mes: " + this.mesLiquidacion.substring(4) + "/" +
				this.mesLiquidacion.substring(0, 4), font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		
		Paragraph fecha = new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(currentDate));
		fecha.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(fecha);

		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f});
		table.setSpacingBefore(10);
		
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();
		
	}
	
	public List<CuotasPagas> getLstCuotas() {
		return lstCuotas;
	}

	public void setLstCuotas(List<CuotasPagas> lstCuotas) {
		this.lstCuotas = lstCuotas;
	}

	public String getMesLiquidacion() {
		return mesLiquidacion;
	}

	public void setMesLiquidacion(String mesLiquidacion) {
		this.mesLiquidacion = mesLiquidacion;
	}
}
