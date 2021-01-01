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

public class PrestamoFormPdfExporter {

	public static final Font BOLD_UNDERLINED = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);
	public static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 11);
	public static final Font BOLD = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
	public static final Font SOLO_BOLD = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
	public static final Font NORMAL10 = FontFactory.getFont(FontFactory.HELVETICA, 10);
	public static final Font NORMAL12 = FontFactory.getFont(FontFactory.HELVETICA, 12);
	public static final Font NORMA14 = FontFactory.getFont(FontFactory.HELVETICA, 14);

	private SolicitudPrestamo solicitud;

	private EstadoDeCta estadodeCta;
	
	private Gplanta funcionario;
	
	private String mesliquidacion;
	
	public PrestamoFormPdfExporter(SolicitudPrestamo solicitud, EstadoDeCta estadodeCta, Gplanta funcionario, String mesliquidacion) {
		super();
		this.solicitud = solicitud;
		this.funcionario = funcionario;
		this.estadodeCta = estadodeCta;
		this.mesliquidacion = mesliquidacion.substring(4) + "/" + mesliquidacion.substring(0, 4);
	}

	private void writeEncabezado(PdfPTable tablee) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.GRAY);
		font.setSize(18);
				
		PdfPTable colTit = new PdfPTable(1);
		colTit.setWidthPercentage(80.0f);
		colTit.setWidths(new float[] {80.0f});
		
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase("FORMULARIO DE PRÉSTAMO", font));
		colTit.addCell(cell);
		
		cell  = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		colTit.addCell(cell);

		PdfPTable tcol = new PdfPTable(1);
		tcol.setWidthPercentage(20.0f);
		tcol.setWidths(new float[] {20.0f});
		
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase("Modalidad/N° de Ptmo.", BOLD));
		tcol.addCell(cell);
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tcol.addCell(cell);

		tablee.addCell(colTit);	
		tablee.addCell(tcol);
		
		PdfPTable tchica = new PdfPTable(2);
		tchica.setWidthPercentage(80.0f);
		tchica.setWidths(new float[] {60.0f, 20.0f});
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase("Funcionario: Nombre y Apellido", BOLD));
		tchica.addCell(cell);
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase("Número", BOLD));
		tchica.addCell(cell);
		
		String[] na = funcionario.getNombre().split(",");
		String nombre = na[1] + " " + na[0];
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase(nombre, NORMAL));
		tchica.addCell(cell);
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase(funcionario.getTarjeta().toString(), NORMAL));
		tchica.addCell(cell);
		
		tablee.addCell(tchica);
		
		tcol = new PdfPTable(1);
		tcol.setWidthPercentage(20.0f);
		tcol.setWidths(new float[] {20.0f});
		
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();

		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase("Fecha", BOLD));
		tcol.addCell(cell);
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase(new SimpleDateFormat("dd/MM/yyyy").format(currentDate), NORMAL));
		tcol.addCell(cell);
	
		tablee.addCell(tcol);
		
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
		cell.setPhrase(new Phrase("Importe", BOLD));
		tchica.addCell(cell);
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPhrase(new Phrase("No. de Cuotas", BOLD));
		tchica.addCell(cell);
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPhrase(new Phrase("Tasa", BOLD));
		tchica.addCell(cell);
		
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPhrase(new Phrase("Importe Inicial de la Cuota", BOLD));
		tchica.addCell(cell);

		PdfPCell celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tchica.addCell(celdaVacia);
		
		celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tchica.addCell(celdaVacia);

		celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tchica.addCell(celdaVacia);

		celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tchica.addCell(celdaVacia);

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

		celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tchica.addCell(celdaVacia);

		celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tchica.addCell(celdaVacia);

		celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tchica.addCell(celdaVacia);

		celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tchica.addCell(celdaVacia);

		tablee.addCell(tchica);

		PdfPTable tcol = new PdfPTable(1);
		tcol.setWidthPercentage(20.0f);
		tcol.setWidths(new float[] {20.0f});
	
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPhrase(new Phrase("Importe Neto a recibir", BOLD));
		tcol.addCell(cell);

		celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tcol.addCell(celdaVacia);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.LEFT);
		cell.setBorder(Rectangle.RIGHT);
		cell.setPhrase(new Phrase(getAsString(solicitud.getImporteNeto()), NORMAL10));
		tcol.addCell(cell);

		celdaVacia = new PdfPCell();
		celdaVacia.setBorder(Rectangle.LEFT);
		celdaVacia.setBorder(Rectangle.RIGHT);
		celdaVacia.addElement(Chunk.NEWLINE);
		tcol.addCell(celdaVacia);

		tablee.addCell(tcol);
		
	}

	private void writeEstadoDeCta(PdfPTable tablee) {
		PdfPCell cell = new PdfPCell();
		
		PdfPTable tchica = new PdfPTable(2);
		tchica.setWidthPercentage(80.0f);
		tchica.setWidths(new float[] {50.0f, 30.0f});
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Cancelación de Préstamos Anteriores", BOLD));
		tchica.addCell(cell);
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Sueldo Base", BOLD));
		tchica.addCell(cell);

		PdfPTable tdos = new PdfPTable(2);
		tdos.setWidthPercentage(60.0f);
		tdos.setWidths(new float[] {40.0f, 20.0f});
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Modalidad-No. de Pmo.", NORMAL10));
		tdos.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Importe", NORMAL10));
		tdos.addCell(cell);
		BigDecimal sumaSaldos = BigDecimal.ZERO;
		tdos.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
		tdos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tdos.getDefaultCell().setBorder(Rectangle.BOX);
		int i = 0;
		
		String cancelPrst = solicitud.getCancelPrstNros();
		if(cancelPrst != null) {
			List<String> lcancel = Arrays.asList(cancelPrst.split(","));
			List<Prestamo> lstPrst = estadodeCta.getLstPrst();
			for(String nro : lcancel) {
				Integer inro = Integer.valueOf(nro);
				i += 1;
				for(Prestamo p : lstPrst) {
					Integer pnro = p.getNroprestamo();
					if(pnro.equals(inro)) {
						sumaSaldos = sumaSaldos.add(p.getSaldoPrestamo());
						tdos.addCell(new Phrase(p.getNroprestamo().toString(), NORMAL10));
						tdos.addCell(new Phrase(getAsString(p.getSaldoPrestamo()), NORMAL10));
					}
				}
			}
		}
		for(int j = i+1; j < 6; j++) {
			cell = new PdfPCell();
			cell.addElement(Chunk.NEWLINE);
			tdos.addCell(cell);
			cell = new PdfPCell();
			cell.addElement(Chunk.NEWLINE);
			tdos.addCell(cell);
		}
		tdos.addCell(new Phrase("Total", NORMAL));
		tdos.addCell(new Phrase(getAsString(sumaSaldos), NORMAL10));
		tchica.addCell(tdos);
		
		PdfPTable tcol = new PdfPTable(1);
		tcol = new PdfPTable(1);
		tcol.setWidthPercentage(20.0f);
		tcol.setWidths(new float[] {20.0f});

		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tcol.addCell(cell);
	
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase(getAsString(estadodeCta.getBasico()) + " USD", NORMAL10));
		tcol.addCell(cell);
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tcol.addCell(cell);
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase(" 40% ", NORMAL10));
		tcol.addCell(cell);
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tcol.addCell(cell);
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase(getAsString(estadodeCta.getCuarentaPorCiento()) + " USD", NORMAL10));
		tcol.addCell(cell);
		
		tchica.addCell(tcol);
		
		tablee.addCell(tchica);
		
			// comienza la celda chica de tablee
		tdos = new PdfPTable(2);
		tdos.setWidthPercentage(20.0f);
		tdos.setWidths(new float[] {10.0f, 10.0f});
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Cuenta Individual", BOLD));
		tdos.addCell(cell);
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tdos.addCell(cell);
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase("85% Capital", NORMAL10));
		tdos.addCell(cell);

		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase("Mes/Año", NORMAL10));
		tdos.addCell(cell);
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase("Integrado", NORMAL10));
		tdos.addCell(cell);
		
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tdos.addCell(cell);

		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tdos.addCell(cell);

		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase(mesliquidacion, NORMAL10));
		tdos.addCell(cell);

		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPhrase(new Phrase(getAsString(estadodeCta.getCapDispOperable()), NORMAL10));
		tdos.addCell(cell);

		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tdos.addCell(cell);
		cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tdos.addCell(cell);
		
		tablee.addCell(tdos);
	}
	
	private void writeOtrosPrst(PdfPTable tablee) {
		PdfPCell cell = new PdfPCell();
		
		PdfPTable tchica = new PdfPTable(3);
		tchica.setWidthPercentage(50.0f);
		tchica.setWidths(new float[] {30.0f, 10.0f, 10.0f});
		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setColspan(3);
		cell.setPhrase(new Phrase("Operaciones Anteriores Pendientes", BOLD));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Modalidad-No. de Pmo.", NORMAL10));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Valor Actual", NORMAL10));
		tchica.addCell(cell);

		cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.BOX);
		cell.setPhrase(new Phrase("Cuotas", NORMAL10));
		tchica.addCell(cell);
		
		int i = 0;
		
		String cancelPrst = solicitud.getCancelPrstNros();
		BigDecimal sumaSaldos = BigDecimal.ZERO;
		List<Prestamo> lstPrst = estadodeCta.getLstPrst();
		if(lstPrst != null) {
			for(Prestamo p : lstPrst) {
				if(cancelPrst == null || !cancelPrst.contains(p.getNroprestamo().toString())) {
					i += 1;
					sumaSaldos = sumaSaldos.add(p.getSaldoPrestamo());
					tchica.addCell(new Phrase(p.getNroprestamo().toString(), NORMAL10));
					tchica.addCell(new Phrase(getAsString(p.getSaldoPrestamo()), NORMAL10));
					tchica.addCell(new Phrase(p.getCuotasPagas().toString(), NORMAL10));
				}
			}			
		}
		for(int j = i+1; j < 6; j++) {
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
		tchica.addCell(new Phrase("Total", NORMAL10));
		if(sumaSaldos.compareTo(BigDecimal.ZERO) > 0) {
			tchica.addCell(new Phrase(getAsString(sumaSaldos), NORMAL10));			
		}
		else {
			cell = new PdfPCell();
			cell.addElement(Chunk.NEWLINE);
			tchica.addCell(cell);			
		}
		cell = new PdfPCell();
		cell.addElement(Chunk.NEWLINE);
		tchica.addCell(cell);

		PdfPTable tcol = new PdfPTable(1);
		tcol.setWidthPercentage(50.0f);
		tcol.setWidths(new float[] {50.0f});
		
		cell = new PdfPCell();
		cell.setPhrase(new Phrase("Saldo no comprometido:  " + getAsString(estadodeCta.getSaldoDisponible()) + " USD", NORMAL10));
		cell.setBorder(Rectangle.NO_BORDER);
		tcol.addCell(cell);

		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tcol.addCell(cell);
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tcol.addCell(cell);

		cell = new PdfPCell();
		cell.setBorder(Rectangle.BOTTOM);
		cell.addElement(Chunk.NEWLINE);
		tcol.addCell(cell);
		
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPhrase(new Phrase("Firma del Solicitante"));
		tcol.addCell(cell);
		
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tcol.addCell(cell);
		cell = new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(Chunk.NEWLINE);
		tcol.addCell(cell);
		
		tablee.addCell(tchica);
		tablee.addCell(tcol);
		
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document =  new Document(PageSize.A4.rotate());
		
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.DARK_GRAY);
		font.setSize(14);
		
		Paragraph title = new Paragraph("SECRETARÍA DEL MERCOSUR ", font);
		title.setAlignment(Paragraph.ALIGN_LEFT);
		
		document.add(title);
		document.add(Chunk.NEWLINE);
		
		
		title = new Paragraph("FONDO DE PREVISIÓN DE LOS FUNCIONARIOS DE LA SM", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		document.add(Chunk.NEWLINE);
		
		PdfPTable tablee = new PdfPTable(2);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {80.0f, 20.0f});
		tablee.setSpacingBefore(15);

		writeEncabezado(tablee);
		document.add(tablee);
		
		String letrachica = "De acuerdo a lo dispuesto en la Instrucción de Servicio Nro. 34/02 'Reglamento del Fondo de Previsión " +
		" de los Funcionarios de la SM', y sus modificaciones, que declaro conocer y aceptar en todos sus términos, solicito se me " +
		"conceda un préstamo sobre mi cuenta individual, en las siguientes condiciones:";
		Paragraph nota = new Paragraph(letrachica, NORMAL);
		nota.setSpacingBefore(8);
		nota.setAlignment(Paragraph.ALIGN_LEFT);
		
		document.add(nota);
		
		tablee = new PdfPTable(2);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {80.0f, 20.0f});
		tablee.setSpacingBefore(25);
		writeDatosPrestamo(tablee);
	
		document.add(tablee);
		
		tablee = new PdfPTable(2);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {80.0f, 20.0f});
		tablee.setSpacingBefore(15);
		writeEstadoDeCta(tablee);
	
		document.add(tablee);
		
		tablee = new PdfPTable(2);
		tablee.setWidthPercentage(100.0f);
		tablee.setWidths(new float[] {50.0f, 50.0f});
		tablee.setSpacingBefore(15);
		writeOtrosPrst(tablee);
		
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
