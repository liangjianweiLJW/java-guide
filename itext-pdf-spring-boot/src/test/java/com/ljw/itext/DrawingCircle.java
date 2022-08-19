package com.ljw.itext;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

/**
 * @Description: 在PDF上画圆
 * @Author: jianweil
 * @date: 2022/7/26 17:25
 */
public class DrawingCircle {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter
        String dest = "C:/itextExamples/drawingCircle.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdfDoc);

        // Creating a new page
        PdfPage pdfPage = pdfDoc.addNewPage();

        // Creating a PdfCanvas object
        PdfCanvas canvas = new PdfCanvas(pdfPage);

        // Setting color to the circle
        Color color = ColorConstants.GREEN;
        canvas.setColor(color, true);

        // creating a circle
        canvas.circle(300, 400, 200);

        // Filling the circle
        canvas.fill();

        // Closing the document
        doc.close();

        System.out.println("Object drawn on pdf successfully");
    }
}
