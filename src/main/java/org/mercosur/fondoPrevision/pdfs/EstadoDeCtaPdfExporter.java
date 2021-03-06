package org.mercosur.fondoPrevision.pdfs;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.AportesSummary;
import org.mercosur.fondoPrevision.dto.EstadoDeCta;
import org.mercosur.fondoPrevision.entities.Prestamo;

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

public class EstadoDeCtaPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);
	public static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 11);
	public static final Font BOLD = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
	public static final Font SOLO_BOLD = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
	public static final Font NORMAL10 = FontFactory.getFont(FontFactory.HELVETICA, 10);

	private EstadoDeCta estadoDeCta;
	
	private String mesLiquidacion;
	
	public EstadoDeCtaPdfExporter(EstadoDeCta estadoDeCta, String mesLiquidacion) {
		super();
		this.estadoDeCta = estadoDeCta;
		this.mesLiquidacion = mesLiquidacion;
	}

	private void writeTableNombre(PdfPTable table) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		font.setSize(10);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);
		cell.setPhrase(new Phrase("N° FUNCIONARIO: " , font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("NOMBRE" , font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("FECHA DE INGRESO", font));			
		table.addCell(cell);			
				
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		cell.setPhrase(new Phrase(estadoDeCta.getFuncionario().getTarjeta().toString() , NORMAL10));
		table.addCell(cell);

		String[] na = estadoDeCta.getFuncionario().getNombre().split(",");
		String nombre = "";
		if(na.length > 1) {
			nombre = na[1] + " " + na[0];			
		}
		else {
			na = estadoDeCta.getFuncionario().getNombre().split(" ");
			switch(na.length) {
			case 4:{
				nombre = na[2] + " " + na[3] + " " + na[0] + " " + na[1];
				break;
			}
			case 3:{
				nombre = na[2] + " " + na[0] + " " + na[1];
				break;
			}
			case 2:{
				nombre = na[1] + " " + na[0];
				break;
			}
			case 1:{
				nombre = na[0];
				break;
			}
			}
		}
		cell.setPhrase(new Phrase(nombre , NORMAL10));
		table.addCell(cell);
		
		if(estadoDeCta.getFuncionario().getIngreso() != null) {
			cell.setPhrase(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(estadoDeCta.getFuncionario().getIngreso()) , NORMAL10));
			table.addCell(cell);			
		}
		else {
			cell.setPhrase(new Phrase(Chunk.NEWLINE));
			table.addCell(cell);
		}

		
	}

	private void writeTableEstadoFinanciero(PdfPTable tablee) {

		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);

		cell.setPhrase(new Phrase("Sueldo: ", BOLD));
		tablee.addCell(cell);

		cell.setPhrase(new Phrase(getAsString(estadoDeCta.getBasico()), NORMAL10));
		tablee.addCell(cell);

		cell.setPhrase(new Phrase("40% :", BOLD));
		tablee.addCell(cell);

		cell.setPhrase(new Phrase(getAsString(estadoDeCta.getCuarentaPorCiento()), NORMAL10));
		tablee.addCell(cell);

		cell.setPhrase(new Phrase(Chunk.NEWLINE));
		tablee.addCell(cell);
		
		cell.setPhrase(new Phrase(Chunk.NEWLINE));
		tablee.addCell(cell);

		cell.setPhrase(new Phrase(Chunk.NEWLINE));
		tablee.addCell(cell);
		
		cell.setPhrase(new Phrase(Chunk.NEWLINE));
		tablee.addCell(cell);

		cell.setPhrase(new Phrase("Capital Integrado Actual: ", BOLD));
		tablee.addCell(cell);

		cell.setPhrase(new Phrase(getAsString(estadoDeCta.getCapIntegActual()), NORMAL10));
		tablee.addCell(cell);

		cell.setPhrase(new Phrase("Pct. de Reserva: ", BOLD));
		tablee.addCell(cell);
		
		cell.setPhrase(new Phrase(getAsString(estadoDeCta.getPctReserva().multiply(new BigDecimal("100"))) + "%", NORMAL10));
		tablee.addCell(cell);
		
		cell.setPhrase(new Phrase("Capital Integrado Operable: ", BOLD));
		tablee.addCell(cell);
		
		cell.setPhrase(new Phrase(getAsString(estadoDeCta.getCapDispOperable()), NORMAL10));
		tablee.addCell(cell);
		
		
		cell.setPhrase(new Phrase("Saldo Disponible: ", BOLD));
		tablee.addCell(cell);

		cell.setPhrase(new Phrase(getAsString(estadoDeCta.getSaldoDisponible()), NORMAL10));
		tablee.addCell(cell);

	
		if(estadoDeCta.getLstPrst() != null) {
			cell.setPhrase(new Phrase(Chunk.NEWLINE));
			tablee.addCell(cell);
			
			cell.setPhrase(new Phrase(Chunk.NEWLINE));
			tablee.addCell(cell);
			cell.setPhrase(new Phrase(Chunk.NEWLINE));
			tablee.addCell(cell);
			
			cell.setPhrase(new Phrase(Chunk.NEWLINE));
			tablee.addCell(cell);

			cell.setPhrase(new Phrase("Suma de Cuotas Comprometidas: ", BOLD));
			tablee.addCell(cell);

			cell.setPhrase(new Phrase(getAsString(estadoDeCta.getSumaDeCuotas()), NORMAL10));
			tablee.addCell(cell);
			
			cell.setPhrase(new Phrase("Saldo de Préstamos Acumulado: ", BOLD));
			tablee.addCell(cell);

			cell.setPhrase(new Phrase(getAsString(estadoDeCta.getSaldoPrstAcum()), NORMAL10));
			tablee.addCell(cell);			
		}
		
	}
	
	private void writeTableHeader(PdfPTable table) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		font.setSize(9);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);
		
		cell.setPhrase(new Phrase("Nro. Prst.", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Fecha Prst.", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Capital", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Interés", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Valor Cuota", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Cant. Cuotas", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Cuotas Pagas", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Saldo Prst.", font));
		table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(10);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		for(Prestamo p : estadoDeCta.getLstPrst()) {
			table.addCell(new Phrase(p.getNroprestamo().toString(), fontd));
			table.addCell(new Phrase(formatter.format(p.getFechaPrestamo()), fontd));
			table.addCell(new Phrase(getAsString(p.getCapitalPrestamo()), fontd));
			table.addCell(new Phrase(getAsString(p.getInteresPrestamo()), fontd));
			table.addCell(new Phrase(getAsString(p.getCuota()), fontd));
			table.addCell(new Phrase(p.getCantCuotas().toString(), fontd));
			table.addCell(new Phrase(p.getCuotasPagas().toString(), fontd));
			table.addCell(new Phrase(getAsString(p.getSaldoPrestamo()), fontd));

		}
	}
	
	private void writeTableAportesHeader(PdfPTable table) {
			Font font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setColor(Color.WHITE);
			font.setSize(9);
			
			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(Color.GRAY);
			cell.setPadding(5);
			
			cell.setPhrase(new Phrase("Concepto", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Aporte Func. 7% s/Básico", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Aporte Sec. 14% s/Total Nominal", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Aporte Total", font));
			table.addCell(cell);
		
	}
	
	private void writeTableAportesData(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		Font fontd = FontFactory.getFont(FontFactory.HELVETICA);
		fontd.setSize(10);

		for(AportesSummary as: estadoDeCta.getLstAportes()) {
			PdfPCell cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.addElement(new Phrase(as.getConcepto(), fontd));
			table.addCell(cell);
			table.addCell(new Phrase(getAsString(as.getAporteFun()), fontd));
			table.addCell(new Phrase(getAsString(as.getAporteSec()), fontd));
			table.addCell(new Phrase(getAsString(as.getAporteTotal()), fontd));
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
			return NumberFormat.getInstance(Locale.getDefault()).format(nro) + ".00";
		}

	}
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document =  new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();

		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.DARK_GRAY);
		font.setSize(11);
		
		Paragraph title = new Paragraph();
		title = new Paragraph("FONDO DE PREVISIÓN DE LOS FUNCIONARIOS DE LA SM", BOLD_UNDERLINED);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		
		title = new Paragraph();
		title.setSpacingBefore(15);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		title.add(new Phrase("ESTADO DE CUENTA AL ", BOLD_UNDERLINED));
		title.add(new Phrase("    " + new SimpleDateFormat("dd/MM/yyyy").format(currentDate) , NORMAL));
		
		document.add(title);

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {30.0f, 40.0f, 30.0f});
		table.setSpacingBefore(18);
		writeTableNombre(table);
		document.add(table);
				
		PdfPTable tablee = new PdfPTable(4);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {30.0f, 20.0f, 30.0f, 20.0f});
		tablee.setSpacingBefore(18);
		writeTableEstadoFinanciero(tablee);
		document.add(tablee);
		
		if(estadoDeCta.getLstPrst() != null) {
			title = new Paragraph("Prestamos Vigentes", font);
			title.setSpacingBefore(18);
			
			document.add(title);
			table = new PdfPTable(8);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f});
			table.setSpacingBefore(8);
			
			writeTableHeader(table);
			writeTableData(table);
			document.add(table);			
		}
		else {
			title = new Paragraph("Sin Prestamos Pendientes", font);
			title.setSpacingBefore(18);
			
			document.add(title);
			
		}
		
		if(estadoDeCta.getLstAportes() != null) {
			title = new Paragraph("Últimos Aportes Integrados", font);
			title.setSpacingBefore(18);
			
			document.add(title);
			table = new PdfPTable(4);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] {2.0f, 1.0f, 1.0f, 1.0f});
			table.setSpacingBefore(8);
			
			writeTableAportesHeader(table);
			writeTableAportesData(table);
			document.add(table);
		}
		document.close();
		
	}


	public EstadoDeCta getEstadoDeCta() {
		return estadoDeCta;
	}

	public void setEstadoDeCta(EstadoDeCta estadoDeCta) {
		this.estadoDeCta = estadoDeCta;
	}

	public String getMesLiquidacion() {
		return mesLiquidacion;
	}

	public void setMesLiquidacion(String mesLiquidacion) {
		this.mesLiquidacion = mesLiquidacion;
	}
}
