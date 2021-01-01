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

import org.mercosur.fondoPrevision.dto.MovimientosForDisplay;

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

public class MovimientosPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);

	private List<MovimientosForDisplay> lstMovimientos;
	
	private String mesDesde;
	
	private String mesHasta;
		
	public MovimientosPdfExporter(List<MovimientosForDisplay> lstmovimientos, String mesDesde, String mesHasta) {
		super();
		this.setLstMovimientos(lstmovimientos);
		this.setMesDesde(mesDesde);
		this.setMesHasta(mesHasta);
	}

	private void writeTableHeader(PdfPTable table) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("Fecha", font));		
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Concepto", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Amortiz. / Aporte Sec", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Interes / Aporte Fun.", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Mov. Total", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Saldo Anterior", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Saldo Actual", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Continúa en la pag. siguiente... " , font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setColspan(7);
		table.addCell(cell);
		table.setHeaderRows(2);
		table.setFooterRows(1);
		table.setSkipLastFooter(true);

	}
	
	private void writeTableData(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		for(MovimientosForDisplay mov : lstMovimientos) {
			PdfPCell cellNom = new PdfPCell();
			cellNom.setPhrase(new Phrase(sdf.format(mov.getFecha()), fontd));
			table.addCell(cellNom);
			
			cellNom = new PdfPCell();			
			cellNom.setPhrase(new Phrase(mov.getConcepto(), fontd));
			table.addCell(cellNom);
			
			table.addCell(new Phrase(getAsString(mov.getImporteCapSec()), fontd));
			table.addCell(new Phrase(getAsString(mov.getImporteIntFun()), fontd));
			table.addCell(new Phrase(getAsString(mov.getImporteTotal()), fontd));
			table.addCell(new Phrase(getAsString(mov.getSaldoAnterior()), fontd));
			table.addCell(new Phrase(getAsString(mov.getSaldoActual()), fontd));

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

		title = new Paragraph("Movimientos del Período: " + this.mesDesde + " - " + this.mesHasta, font);
		title.setAlignment(Paragraph.ALIGN_CENTER);			
		
		document.add(title);
		
		String[] na = lstMovimientos.get(0).getNombre().split(",");
		String nombre = na[1] + " " + na[0];
		
		title = new Paragraph(nombre, font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);
		
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		
		Paragraph fecha = new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(currentDate));
		fecha.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(fecha);

		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f});
		table.setSpacingBefore(10);
		
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();

		
	}
	
	public List<MovimientosForDisplay> getLstMovimientos() {
		return lstMovimientos;
	}

	public void setLstMovimientos(List<MovimientosForDisplay> lstMovimientos) {
		this.lstMovimientos = lstMovimientos;
	}

	public String getMesDesde() {
		return mesDesde;
	}

	public void setMesDesde(String mesDesde) {
		this.mesDesde = mesDesde;
	}

	public String getMesHasta() {
		return mesHasta;
	}

	public void setMesHasta(String mesHasta) {
		this.mesHasta = mesHasta;
	}
	
}
