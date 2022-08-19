package com.ljw.itext;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLineAnnotation;
import com.itextpdf.layout.Document;

/**
 * @Description: 在PDF中创建线注释
 * @Author: jianweil
 * @date: 2022/7/26 17:23
 */
public class LineAnnotation {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter
        String dest = "C:/itextExamples/lineAnnotations.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document
        Document document = new Document(pdf);

        // Creating a PdfPage
        PdfPage page = pdf.addNewPage();

        // creating PdfLineAnnotation object
        Rectangle rect = new Rectangle(0, 0);
        float[] floatArray  = new float[]{
                20, 790, page.getPageSize().getWidth() - 20, 790
        };
        PdfAnnotation annotation = new PdfLineAnnotation(rect, floatArray);

        // Setting color of the PdfLineAnnotation
        annotation.setColor(ColorConstants.BLUE);

        // Setting title to the PdfLineAnnotation
        annotation.setTitle(new PdfString("iText"));

        // Setting contents of the PdfLineAnnotation
        annotation.setContents("Hi welcome to Tutorialspoint");

        // Adding annotation to the page
        page.addAnnotation(annotation);

        // Closing the document
        document.close();

        System.out.println("Annotation added successfully");
    }
}
