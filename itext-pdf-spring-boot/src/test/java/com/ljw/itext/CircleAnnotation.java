package com.ljw.itext;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfCircleAnnotation;
import com.itextpdf.layout.Document;

/**
 * @Description: 在PDF中创建圆形注释
 * @Author: jianweil
 * @date: 2022/7/26 17:24
 */
public class CircleAnnotation {
    public static void main(String args[]) throws Exception {
        // Creating a PdfDocument object
        String file = "C:/itextExamples// circleAnnotation.pdf";
        PdfDocument pdf = new PdfDocument(new PdfWriter(file));

        // Creating a Document object
        Document doc = new Document(pdf);

        // Creating a PdfCircleAnnotation object
        Rectangle rect = new Rectangle(150, 770, 50, 50);
        PdfAnnotation annotation = new PdfCircleAnnotation(rect);

        // Setting color to the annotation
        annotation.setColor(ColorConstants.YELLOW);

        // Setting title to the annotation
        annotation.setTitle(new PdfString("circle annotation"));

        // Setting contents of the annotation
        annotation.setContents(new PdfString("Hi welcome to Tutorialspoint"));

        // Creating a new page
        PdfPage page = pdf.addNewPage();

        // Adding annotation to a page in a PDF
        page.addAnnotation(annotation);

        // Closing the document
        doc.close();

        System.out.println("Annotation added successfully");
    }
}
