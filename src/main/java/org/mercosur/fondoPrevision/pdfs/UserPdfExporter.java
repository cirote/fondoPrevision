package org.mercosur.fondoPrevision.pdfs;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.mercosur.fondoPrevision.entities.User;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class UserPdfExporter {

	private Iterable<User> listUsers;

	public UserPdfExporter(Iterable<User> listUsers) {
		super();
		this.listUsers = listUsers;
	}

	private void writeTableHeader(PdfPTable table) {
				
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("Tarjeta", font));		
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Nombre", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Apellido", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("E-mail", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Roles", font));
		table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table) {
		for(User u : listUsers) {
			table.addCell(String.valueOf(u.getTarjeta()));
			table.addCell(u.getNombre());
			table.addCell(u.getApellido());
			table.addCell(u.getEmail());
			table.addCell(u.getRoles().toString());
		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document =  new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.DARK_GRAY);
		font.setSize(18);
		
		Paragraph title = new Paragraph("List of all users", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(title);
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {1.0f, 2.0f, 2.0f, 3.0f, 3.0f});
		table.setSpacingBefore(10);
		
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();
		
		
	}
	public Iterable<User> getListUsers() {
		return listUsers;
	}

	public void setListUsers(Iterable<User> listUsers) {
		this.listUsers = listUsers;
	}
}
