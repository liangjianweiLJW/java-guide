package com.ljw.itext;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/7/26 17:09
 */
public class AddingAreaBreak {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter
        String dest = "C:/itextExamples/addingAreaBreak.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document by passing PdfDocument object to its constructor
        Document document = new Document(pdf);

        // Creating an Area Break
        AreaBreak aB = new AreaBreak();

        // Adding area break to the PDF
        document.add(aB);

        // Closing the document
        document.close();
        System.out.println("Pdf created");
    }
}
