package com.ljw.print;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

import java.io.IOException;

/**
 * @author lizj
 * 打印页码事件类
 */
public class TextFooterEventHandler implements IEventHandler {

    protected Document doc;
    protected int page;

    public TextFooterEventHandler(Document doc) {
        this.doc = doc;

    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
        Rectangle pageSize = docEvent.getPage().getPageSize();
        page++;
        canvas.beginText();
        try {
            canvas.setFontAndSize(PdfFontFactory.createFont("D:/project/my/java-guide/itext-pdf-spring-boot/src/main/resources/static/font/simsun.ttf", PdfEncodings.IDENTITY_H, true), 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        canvas.moveText((pageSize.getRight() - doc.getRightMargin() + (pageSize.getLeft() + doc.getLeftMargin())) / 2, pageSize.getBottom() + doc.getBottomMargin())
                .showText(page + "")
                .endText()
                .release();
    }

}
