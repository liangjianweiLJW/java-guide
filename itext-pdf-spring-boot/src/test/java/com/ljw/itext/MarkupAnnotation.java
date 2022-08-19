package com.ljw.itext;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation;
import com.itextpdf.layout.Document;

/**
 * @Description: 在PDF中创建标记注释
 * @Author: jianweil
 * @date: 2022/7/26 17:23
 */
public class MarkupAnnotation {
    public static void main(String args[]) throws Exception {
        // Creating a PdfDocument object
        String file = "C:/itextExamples/markupAnnotation.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));

        // Creating a Document object
        Document doc = new Document(pdfDoc);

        // Creating a PdfTextMarkupAnnotation object
        Rectangle rect = new Rectangle(105, 790, 64, 10);
        float[] floatArray = new float[]{169, 790, 105, 790, 169, 800, 105, 800};
        PdfAnnotation annotation =
                PdfTextMarkupAnnotation.createHighLight(rect,floatArray);

        // Setting color to the annotation
        annotation.setColor(ColorConstants.YELLOW);

        // Setting title to the annotation
        annotation.setTitle(new PdfString("Hello!"));

        // Setting contents to the annotation
        annotation.setContents(new PdfString("Hi welcome to Tutorialspoint"));

        // Creating a new Pdfpage
        PdfPage pdfPage = pdfDoc.addNewPage();

        // Adding annotation to a page in a PDF
        pdfPage.addAnnotation(annotation);

        // Closing the document
        doc.close();

        System.out.println("Annotation added successfully");
    }
}
