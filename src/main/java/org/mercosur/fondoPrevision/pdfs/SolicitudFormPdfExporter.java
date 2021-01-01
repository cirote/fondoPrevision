package org.mercosur.fondoPrevision.pdfs;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.dto.EstadoDeCta;
import org.mercosur.fondoPrevision.entities.Gplanta;
import org.mercosur.fondoPrevision.entities.Prestamo;
import org.mercosur.fondoPrevision.entities.SolicitudPrestamo;

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

public class SolicitudFormPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);
	public static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 11);
	public static final Font BOLD = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
	public static final Font SOLO_BOLD = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
	public static final Font BOLD9 = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
	public static final Font NORMAL9 = FontFactory.getFont(FontFactory.HELVETICA, 9);
	public static final Font NORMAL10 = FontFactory.getFont(FontFactory.HELVETICA, 10);
	public static final Font NORMAL12 = FontFactory.getFont(FontFactory.HELVETICA, 12);
	public static final Font NORMA14 = FontFactory.getFont(FontFactory.HELVETICA, 14);

	private SolicitudPrestamo solicitud;

	private EstadoDeCta estadodeCta;
	
	private Gplanta funcionario;
	
	private String mesliquidacion;
	
	public SolicitudFormPdfExporter(SolicitudPrestamo solicitud, EstadoDeCta estadodeCta, Gplanta funcionario, String mesliquidacion) {
		super();
		this.solicitud = solicitud;
		this.funcionario = funcionario;
		this.estadodeCta = estadodeCta;
		this.mesliquidacion = mesliquidacion.substring(4) + "/" + mesliquidacion.substring(0, 4);
	}

	private void writeEncabezado(PdfPTable tablee) {
				
		PdfPCell cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase("Modalidad/N° de Ptmo.", BOLD9));
		tablee.addCell(cell);

		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();

		Paragraph p = new Paragraph();
		p.add(new Phrase("Fecha  ", BOLD9));
		p.add(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(currentDate), NORMAL));
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(p);
		tablee.addCell(cell);
		
		String[] na = funcionario.getNombre().split(",");
		String nombre = na[1] + " " + na[0];
		
		p = new Paragraph();
		p.add(new Phrase("Funcionario:   ", BOLD));
		p.add(new Phrase(nombre, NORMAL));
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(p);
		tablee.addCell(cell);
		
		p = new Paragraph();
		p.add(new Phrase("Número:   ", BOLD));
		p.add(new Phrase(funcionario.getTarjeta().toString(), NORMAL));
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(p);
		tablee.addCell(cell);
		
	}
	
	private void writeDatosPrestamo(PdfPTable tablee) {

		PdfPCell cell = new PdfPCell();
		
		PdfPTable tchica = new PdfPTable(4);
		tchica.setWidthPercentage(80.0f);
		tchica.setWidths(new float[] {20.0f, 20.0f, 10.0f, 30.0f});
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPhrase(new Phrase("Importe", BOLD9));
		tchica.addCell(cell);
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPhrase(new Phrase("No. de Cuotas", BOLD9));
		tchica.addCell(cell);
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPhrase(new Phrase("Tasa", BOLD9));
		tchica.addCell(cell);
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPhrase(new Phrase("Importe Cuota", BOLD9));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.LEFT);
		cell.setBorder(Rectangle.RIGHT);
		cell.setPhrase(new Phrase(getAsString(solicitud.getCapitalPrestamo()), NORMAL10));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.LEFT);
		cell.setBorder(Rectangle.RIGHT);
		cell.setPhrase(new Phrase(solicitud.getCantCuotas().toString(), NORMAL10));
		tchica.addCell(cell);
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.LEFT);
		cell.setBorder(Rectangle.RIGHT);
		cell.setPhrase(new Phrase(getAsString(solicitud.getInteresPrestamo()), NORMAL10));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.LEFT);
		cell.setBorder(Rectangle.RIGHT);
		cell.setPhrase(new Phrase(getAsString(solicitud.getCuota()), NORMAL10));
		tchica.addCell(cell);

		tablee.addCell(tchica);

		PdfPTable tcol = new PdfPTable(1);
		tcol.setWidthPercentage(20.0f);
		tcol.setWidths(new float[] {20.0f});
	
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPhrase(new Phrase("Importe Neto a recibir", BOLD9));
		tcol.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.LEFT);
		cell.setBorder(Rectangle.RIGHT);
		cell.setPhrase(new Phrase(getAsString(solicitud.getImporteNeto()), NORMAL10));
		tcol.addCell(cell);

		tablee.addCell(tcol);
		
	}

	private void writeEstadoDeCta(PdfPTable tablee) {
		PdfPCell cell = new PdfPCell();
		cell.setColspan(2);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Cuenta Individual Mes/Año: " + mesliquidacion, BOLD9));
		tablee.addCell(cell);
		
		Paragraph p = new Paragraph();
		p.add(new Phrase("Sueldo Base:  ", BOLD9));
		p.add(new Phrase(getAsString(estadodeCta.getBasico()) + " USD", NORMAL9));
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.addElement(p);
		tablee.addCell(cell);
		
		p = new Paragraph();
		p.add(new Phrase("85% Cap. Integrado:  ", BOLD9));
		p.add(new Phrase(getAsString(estadodeCta.getCapDispOperable()) + " USD", NORMAL9));
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.addElement(p);
		tablee.addCell(cell);
		
		
		p = new Paragraph();
		p.add(new Phrase("40%       ", BOLD9));
		p.add(new Phrase(getAsString(estadodeCta.getCuarentaPorCiento()) + " USD", NORMAL9));
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.addElement(p);
		tablee.addCell(cell);

		p = new Paragraph();
		p.add(new Phrase("Saldo no Comprometido:   ", BOLD9));
		p.add(new Phrase(getAsString(estadodeCta.getSaldoDisponible()) + " USD", NORMAL9));
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.addElement(p);
		tablee.addCell(cell);

	}
	
	private void writeCancelacionesyPendientes(PdfPTable tablee) {
		PdfPCell cell = new PdfPCell();
		
		PdfPTable tchica = new PdfPTable(2);
		tchica.setWidthPercentage(50.0f);
		tchica.setWidths(new float[] {40.0f, 10.0f});
		cell = new PdfPCell();
		cell.setColspan(2);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Cancelación de Préstamos Anteriores", BOLD9));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Modalidad-No. de Pmo.", NORMAL9));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Importe", NORMAL9));
		tchica.addCell(cell);
		
		BigDecimal sumaSaldos = BigDecimal.ZERO;
		tchica.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
		tchica.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tchica.getDefaultCell().setBorder(Rectangle.BOX);
		int i = 0;
		
		String cancelPrst = solicitud.getCancelPrstNros();
		if(cancelPrst != null && !cancelPrst.isEmpty()) {
			List<String> lcancel = Arrays.asList(cancelPrst.split(","));
			List<Prestamo> lstPrst = estadodeCta.getLstPrst();
			for(String nro : lcancel) {
				Integer inro = Integer.valueOf(nro);
				i += 1;
				for(Prestamo p : lstPrst) {
					Integer pnro = p.getNroprestamo();
					if(pnro.equals(inro)) {
						sumaSaldos = sumaSaldos.add(p.getSaldoPrestamo());
						tchica.addCell(new Phrase(p.getNroprestamo().toString(), NORMAL9));
						tchica.addCell(new Phrase(getAsString(p.getSaldoPrestamo()), NORMAL9));
					}
				}
			}
		}
		for(int j = i+1; j < 4; j++) {
			cell = new PdfPCell();
			cell.addElement(Chunk.NEWLINE);
			tchica.addCell(cell);
			cell = new PdfPCell();
			cell.addElement(Chunk.NEWLINE);
			tchica.addCell(cell);
		}
		tchica.addCell(new Phrase("Total", NORMAL9));
		if(sumaSaldos.compareTo(BigDecimal.ZERO) > 0){
			tchica.addCell(new Phrase(getAsString(sumaSaldos), NORMAL9));
		}
		else {
			tchica.addCell(cell);
		}
		tablee.addCell(tchica);

		
		tchica = new PdfPTable(3);
		tchica.setWidthPercentage(50.0f);
		tchica.setWidths(new float[] {30.0f, 10.0f, 10.0f});
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setColspan(3);
		cell.setPhrase(new Phrase("Operaciones Anteriores Pendientes", BOLD9));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Modalidad-No. de Pmo.", NORMAL9));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Saldo", NORMAL9));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Cuotas", NORMAL9));
		tchica.addCell(cell);
		
		i = 0;
		tchica.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
		tchica.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tchica.getDefaultCell().setBorder(Rectangle.BOX);

		cancelPrst = solicitud.getCancelPrstNros();
		sumaSaldos = BigDecimal.ZERO;
		List<Prestamo> lstPrst = estadodeCta.getLstPrst();
		if(lstPrst != null) {
			for(Prestamo p : lstPrst) {
				if(cancelPrst == null || !cancelPrst.contains(p.getNroprestamo().toString())) {
					i += 1;
					sumaSaldos = sumaSaldos.add(p.getSaldoPrestamo());
					tchica.addCell(new Phrase(p.getNroprestamo().toString(), NORMAL9));
					tchica.addCell(new Phrase(getAsString(p.getSaldoPrestamo()), NORMAL9));
					tchica.addCell(new Phrase(p.getCuotasPagas().toString(), NORMAL9));
				}
			}			
		}
		for(int j = i+1; j < 5; j++) {
			cell = new PdfPCell();
			cell.addElement(Chunk.NEWLINE);
			tchica.addCell(cell);
			cell = new PdfPCell();
			cell.addElement(Chunk.NEWLINE);
			tchica.addCell(cell);
			cell = new PdfPCell();
			cell.addElement(Chunk.NEWLINE);
			tchica.addCell(cell);
		}
		tchica.addCell(new Phrase("Total", NORMAL9));
		if(sumaSaldos.compareTo(BigDecimal.ZERO) > 0) {
			tchica.addCell(new Phrase(getAsString(sumaSaldos), NORMAL9));			
		}
		else {
			cell = new PdfPCell();
			cell.addElement(Chunk.NEWLINE);
			tchica.addCell(cell);			
		}
		tablee.addCell(tchica);		
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document =  new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.DARK_GRAY);
		font.setSize(11);
		
		Paragraph title = new Paragraph("SECRETARÍA DEL MERCOSUR ", font);
		title.setAlignment(Paragraph.ALIGN_LEFT);
		
		document.add(title);
		document.add(Chunk.NEWLINE);
		
		
		title = new Paragraph("FONDO DE PREVISIÓN DE LOS FUNCIONARIOS DE LA SM", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		document.add(Chunk.NEWLINE);
		
		font.setColor(Color.GRAY);
		font.setSize(12);
		title = new Paragraph("FORMULARIO DE PRÉSTAMO", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		
		PdfPTable tablee = new PdfPTable(2);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {80.0f, 20.0f});
		tablee.setSpacingBefore(20);

		writeEncabezado(tablee);
		document.add(tablee);
		
		String letrachica = "De acuerdo a lo dispuesto en la Instrucción de Servicio Nro. 34/02 'Reglamento del Fondo de Previsión " +
		" de los Funcionarios de la SM', y sus modificaciones, que declaro conocer y aceptar en todos sus términos, solicito se me " +
		"conceda un préstamo sobre mi cuenta individual, en las siguientes condiciones:";
		Paragraph nota = new Paragraph(letrachica, NORMAL9);
		nota.setSpacingBefore(8);
		nota.setAlignment(Paragraph.ALIGN_LEFT);
		
		document.add(nota);
		
		tablee = new PdfPTable(2);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {70.0f, 30.0f});
		tablee.setSpacingBefore(20);
		writeDatosPrestamo(tablee);
	
		document.add(tablee);
		
		tablee = new PdfPTable(2);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {50.0f, 50.0f});
		tablee.setSpacingBefore(15);
		writeEstadoDeCta(tablee);
	
		document.add(tablee);
		
		tablee = new PdfPTable(2);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {50.0f, 50.0f});
		tablee.setSpacingBefore(15);
		writeCancelacionesyPendientes(tablee);
		
		document.add(tablee);
		
		tablee = new PdfPTable(1);
		tablee.setWidthPercentage(50.0f);
		tablee.setSpacingBefore(25.0f);
		tablee.setHorizontalAlignment(Element.ALIGN_RIGHT);
		PdfPCell celda = new PdfPCell();
		celda.setBorder(Rectangle.TOP);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setPhrase(new Phrase("Firma del Solicitante", NORMAL9));
		tablee.addCell(celda);
		
		document.add(tablee);
		
		document.add(Chunk.NEWLINE);
		
		tablee = new PdfPTable(2);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {50.0f, 50.0f});
		
		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPhrase(new Phrase("Sector Administración", BOLD));
		tablee.addCell(celda);
		
		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPhrase(new Phrase("Comisión Administradora del Fondo de Previsión", BOLD));
		tablee.addCell(celda);

		PdfPTable t1 = new PdfPTable(2);
		t1.setWidthPercentage(50.0f);
		t1.setWidths(new float[] {25.0f, 25.0f});
		celda = new PdfPCell();
		celda.setPhrase(new Phrase("1ra. Cuota Mes: ", BOLD9));
		t1.addCell(celda);
		
		celda = new PdfPCell();
		celda.setPhrase(new Phrase("Última Cuota Mes: ", BOLD9));
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setColspan(2);
		celda.setPhrase(new Phrase("Cálculos revisados por: ", BOLD9));
		t1.addCell(celda);
		
		celda = new PdfPCell();
		celda.setPhrase(new Phrase("Fecha: ", NORMAL9));
		t1.addCell(celda);
		
		celda = new PdfPCell();
		celda.setPhrase(new Phrase("Firma: ", NORMAL9));
		t1.addCell(celda);

		Phrase ph1 = new Phrase("Recibí conforme el préstamo por la suma de USD ___________, según Cheque N° ____________ " +
				"del Bco.________________ de acuerdo a las condiciones expresadas más arriba.", NORMAL9);
		
		Phrase ph2 = new Phrase("Autorizo al Sector de Administración a realizar las retenciones de las cuotas, de mis haberes " +
				"mensuales correspondientes, para la cancelación de la operación.", NORMAL9);
		
		Phrase ph3 = new Phrase("Asimismo en caso de cese de funciones en la SM, autorizo a que el saldo del préstamo sea descontado " +
				"del monto de mi cuenta individual.", NORMAL9);
		
		Paragraph p = new Paragraph();
		p.add(ph1);
		p.add(ph2);
		p.add(ph3);
		
		celda = new PdfPCell();
		celda.setColspan(2);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.addElement(p);
		t1.addCell(celda);
		
		celda = new PdfPCell();
		celda.setColspan(2);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.addElement(Chunk.NEWLINE);
		t1.addCell(celda);
		
		celda = new PdfPCell();
		celda.setColspan(2);
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
		celda.addElement(Chunk.NEWLINE);
		celda.setPhrase(new Phrase("Firma", NORMAL9));
		t1.addCell(celda);
		
		tablee.addCell(t1);
		

		t1 = new PdfPTable(2);
		t1.setWidthPercentage(50.0f);
		t1.setWidths(new float[] {25.0f, 25.0f});
		p = new Paragraph();
		p.add(new Phrase("RESOLUCIÓN: ", BOLD));
		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.addElement(p);
		t1.addCell(celda);
		
		celda = new PdfPCell();
		celda.setBorder(Rectangle.BOTTOM);
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setColspan(2);
		celda.setBorder(Rectangle.BOTTOM);
		p = new Paragraph();
		p.add(new Phrase(solicitud.getMotivo()== null? " ": solicitud.getMotivo(), NORMAL9));
		celda.addElement(p);
		t1.addCell(celda);

		
		celda = new PdfPCell();
		celda.setColspan(2);
		celda.setBorder(Rectangle.BOTTOM);
		celda.addElement(Chunk.NEWLINE);
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPhrase(new Phrase("FIRMAS: ", NORMAL9));
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.addElement(Chunk.NEWLINE);
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPhrase(new Phrase("Director de la SM", NORMAL9));
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.BOTTOM);
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		celda.addElement(Chunk.NEWLINE);
		t1.addCell(celda);
		
		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		celda.addElement(Chunk.NEWLINE);
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPhrase(new Phrase("Adm. del Fondo", NORMAL9));
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.TOP);
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setColspan(2);
		celda.addElement(Chunk.NEWLINE);
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		celda.setPhrase(new Phrase("Adm. del Fondo", NORMAL9));
		t1.addCell(celda);

		celda = new PdfPCell();
		celda.setBorder(Rectangle.BOTTOM);
		t1.addCell(celda);

		tablee.addCell(t1);

		document.add(tablee);
		document.close();

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

	public Gplanta getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Gplanta funcionario) {
		this.funcionario = funcionario;
	}

	public SolicitudPrestamo getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudPrestamo solicitud) {
		this.solicitud = solicitud;
	}

	public EstadoDeCta getEstadodeCta() {
		return estadodeCta;
	}

	public void setEstadodeCta(EstadoDeCta estadodeCta) {
		this.estadodeCta = estadodeCta;
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}
}
