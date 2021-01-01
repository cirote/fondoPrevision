package org.mercosur.fondoPrevision.pdfs;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class HeaderAndFooter extends PdfPageEventHelper {

	public void onEndPage (PdfWriter writer, Document document) {
	    Rectangle rect = writer.getBoxSize("art");
	    switch(writer.getPageNumber() % 2) {
	    case 0:
	        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("even header"),
	                rect.getBorderWidthRight(), rect.getBorderWidthTop(), 0);
	        break;
	    case 1:
	        ColumnText.showTextAligned(writer.getDirectContent(),
	                Element.ALIGN_CENTER, new Phrase(String.format("%d", writer.getPageNumber())),
	                300f, 62f, 0);
	        break;
	    }
	}
}
