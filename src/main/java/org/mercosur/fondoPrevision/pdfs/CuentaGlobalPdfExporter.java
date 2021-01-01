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
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.CuentaGlobalSummary;
import org.mercosur.fondoPrevision.entities.CuentaGlobal;
import org.mercosur.fondoPrevision.repository.CuentaGlobalRepository;
import org.springframework.beans.factory.annotation.Autowired;

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

public class CuentaGlobalPdfExporter {

	@Autowired
	CuentaGlobalRepository cuentaGlobalRepository;

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);

	private List<CuentaGlobal> lstResumen;
	
	private BigDecimal sumaTotal;
	
	private String mesliquidacion;
	
	private Map<String, List<CuentaGlobalSummary>> ctaGlobalMap;
	
	public CuentaGlobalPdfExporter(List<CuentaGlobal> lstResumen, BigDecimal sumaTotal, String mesliquidacion, 
			Map<String, List<CuentaGlobalSummary>> ctaGlobalMap) {
		super();
		this.lstResumen = lstResumen;
		this.sumaTotal = sumaTotal;
		this.mesliquidacion = mesliquidacion;
		this.setCtaGlobalMap(ctaGlobalMap);
	}

	private void writeTableHeader(PdfPTable tableRes) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("MesLiquidación", font));		
		tableRes.addCell(cell);
		
		cell.setPhrase(new Phrase("Total Mensual", font));
		tableRes.addCell(cell);
		
	}

	private void writeSubTableHeader(PdfPTable subtable) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("Nro. Prst", font));		
		subtable.addCell(cell);

		cell.setPhrase(new Phrase("Titular", font));		
		subtable.addCell(cell);

		cell.setPhrase(new Phrase("Nro. Cuota", font));		
		subtable.addCell(cell);

		cell.setPhrase(new Phrase("Importe", font));
		subtable.addCell(cell);
		
		cell.setPhrase(new Phrase("Continúa en la pag. siguiente... " , font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setColspan(4);
		subtable.addCell(cell);
		subtable.setHeaderRows(2);
		subtable.setFooterRows(1);
		subtable.setSkipLastFooter(true);

		
	}

	private void writeSubTableData(PdfPTable subtable, List<CuentaGlobalSummary> lstdetalle) {
		subtable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);
		
		for(CuentaGlobalSummary cg : lstdetalle) {
			PdfPCell cellst = new PdfPCell();
			cellst.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellst.setPhrase(new Phrase(cg.getTitular(), fontd));
			
			subtable.addCell(new Phrase(cg.getNroPrestamo().toString(), fontd));
			subtable.addCell(cellst);
			subtable.addCell(new Phrase(cg.getNroCuota().toString(), fontd));
			subtable.addCell(new Phrase(getAsString(cg.getImporte()), fontd));
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
		
		Document document =  new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
        document.setPageCount(document.getPageNumber());
		document.open();
		document.newPage();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.DARK_GRAY);
		font.setSize(12);
		
		Paragraph title = new Paragraph();
		title = new Paragraph("FONDO DE PREVISIÓN DE LOS FUNCIONARIOS DE LA SM", BOLD_UNDERLINED);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);
		document.add(Chunk.NEWLINE);

		title = new Paragraph("Estado de la Cuenta Global en el Ejercicio ", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		
		Paragraph fecha = new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(currentDate));
		fecha.setAlignment(Paragraph.ALIGN_RIGHT);
		fecha.setSpacingAfter(10);
		document.add(fecha);

		Paragraph subtit = new Paragraph("Total Anual Actual U$S " + getAsString(sumaTotal));
		subtit.setAlignment(Paragraph.ALIGN_LEFT);
		subtit.setSpacingAfter(10);
		document.add(subtit);
		
		PdfPTable tableRes = new PdfPTable(2);

		PdfPTable subTable = new PdfPTable(4);
		subTable.setWidthPercentage(100.0f);
		subTable.setWidths(new float[] {1.0f, 2.0f, 1.0f, 1.0f});
		
		tableRes.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(11);

		for(CuentaGlobal cgr : this.lstResumen) {
			tableRes = new PdfPTable(2);
			tableRes.setWidthPercentage(100.0f);
			tableRes.setWidths(new float[] {3.0f, 1.0f});
			writeTableHeader(tableRes);
			PdfPCell cellMes = new PdfPCell();
			cellMes.setPhrase(new Phrase(cgr.getMesliquidacion().substring(4)+ "/" + cgr.getMesliquidacion().substring(0, 4), fontd));
			tableRes.addCell(cellMes);
			tableRes.addCell(new Phrase(getAsString(cgr.getImporte()), fontd));			
			document.add(tableRes);
			
			String key = cgr.getMesliquidacion() + " - " + getAsString(cgr.getImporte());
			List<CuentaGlobalSummary> lstDetalles = ctaGlobalMap.get(key);
			writeSubTableHeader(subTable);
			writeSubTableData(subTable, lstDetalles);
			document.add(subTable);	
			document.newPage();
			
			subTable = new PdfPTable(4);
			subTable.setWidthPercentage(100.0f);
			subTable.setWidths(new float[] {1.0f, 2.0f, 1.0f, 1.0f});
		}
		
		document.close();
		
	}

	public List<CuentaGlobal> getLstResumen() {
		return lstResumen;
	}

	public void setLstResumen(List<CuentaGlobal> lstResumen) {
		this.lstResumen = lstResumen;
	}

	public BigDecimal getSumaTotal() {
		return sumaTotal;
	}

	public void setSumaTotal(BigDecimal sumaTotal) {
		this.sumaTotal = sumaTotal;
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}

	public Map<String, List<CuentaGlobalSummary>> getCtaGlobalMap() {
		return ctaGlobalMap;
	}

	public void setCtaGlobalMap(Map<String, List<CuentaGlobalSummary>> ctaGlobalMap) {
		this.ctaGlobalMap = ctaGlobalMap;
	}
	
	
}
