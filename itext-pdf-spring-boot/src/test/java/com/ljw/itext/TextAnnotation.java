package com.ljw.itext;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
import com.itextpdf.layout.Document;

/**
 * @Description: 在PDF中创建文本注释
 * @Author: jianweil
 * @date: 2022/7/26 17:21
 */
public class TextAnnotation {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter
        String dest = "C:/itextExamples/textAnnotation.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document
        Document document = new Document(pdf);

        // Creating PdfTextAnnotation object
        Rectangle rect = new Rectangle(20, 800, 0, 0);
        PdfAnnotation ann = new PdfTextAnnotation(rect);

        // Setting color to the annotation
        ann.setColor(ColorConstants.GREEN);

        // Setting title to the annotation
        ann.setTitle(new PdfString("Hello"));

        // Setting contents of the annotation
        ann.setContents("Hi welcome to Tutorialspoint.");

        // Creating a new page
        PdfPage page =  pdf.addNewPage();

        // Adding annotation to a page in a PDF
        page.addAnnotation(ann);

        // Closing the document
        document.close();

        System.out.println("Annotation added successfully");
    }
}
