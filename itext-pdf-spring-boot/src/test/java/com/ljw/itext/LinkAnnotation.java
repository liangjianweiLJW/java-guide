package com.ljw.itext;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;

/**
 * @Description: 在PDF中创建链接注释
 * @Author: jianweil
 * @date: 2022/7/26 17:22
 */
public class LinkAnnotation {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter
        String dest = "C:/itextExamples/linkAnnotation.pdf";

        PdfWriter writer = new
                PdfWriter(dest);

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document
        Document document = new Document(pdf);

        // Creating a PdfLinkAnnotation object
        Rectangle rect = new Rectangle(0, 0);
        PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);

        // Setting action of the annotation
        PdfAction action = PdfAction.createURI("http:// www.tutorialspoint.com/");
        annotation.setAction(action);

        // Creating a link
        Link link = new Link("Click here", annotation);

        // Creating a paragraph
        Paragraph paragraph = new Paragraph("Hi welcome to Tutorialspoint ");

        // Adding link to paragraph
        paragraph.add(link.setUnderline());

        // Adding paragraph to document
        document.add(paragraph);

        // Closing the document
        document.close();

        System.out.println("Annotation added successfully");
    }
}
