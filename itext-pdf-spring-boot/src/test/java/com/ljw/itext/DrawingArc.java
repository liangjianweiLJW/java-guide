package com.ljw.itext;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

/**
 * @Description: 在PDF上绘制圆弧
 * @Author: jianweil
 * @date: 2022/7/26 17:24
 */
public class DrawingArc {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter
        String dest = "C:/itextExamples/drawingArc.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdfDoc);

        // Creating a new page
        PdfPage pdfPage = pdfDoc.addNewPage();

        // Creating a PdfCanvas object
        PdfCanvas canvas = new PdfCanvas(pdfPage);

        // Drawing an arc
        canvas.arc(50, 50, 300, 545, 0, 360);

        // Filling the arc
        canvas.fill();

        // Closing the document
        doc.close();

        System.out.println("Object drawn on pdf successfully");
    }
}
