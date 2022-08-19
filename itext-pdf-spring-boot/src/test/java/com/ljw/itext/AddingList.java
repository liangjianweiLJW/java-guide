package com.ljw.itext;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;

/**
 * @Description: 创建列表
 * @Author: jianweil
 * @date: 2022/7/26 17:13
 */
public class AddingList {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter
        String dest = "C:/itextExamples/addngList.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document
        Document document = new Document(pdf);

        // Creating a Paragraph
        Paragraph paragraph = new Paragraph("Tutorials Point provides the following tutorials");

        // Creating a list
        List list = new List();

        // Add elements to the list
        list.add("Java");
        list.add("JavaFX");
        list.add("Apache Tika");
        list.add("OpenCV");
        list.add("WebGL");
        list.add("Coffee Script");
        list.add("Java RMI");
        list.add("Apache Pig");

        // Adding paragraph to the document
        document.add(paragraph);

        // Adding list to the document
        document.add(list);

        // Closing the document
        document.close();
        System.out.println("List added");
    }
}
