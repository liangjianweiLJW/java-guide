package com.ljw.itext;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

/**
 * @Description: 将表格添加到 Pdf
 * @Author: jianweil
 * @date: 2022/7/26 17:14
 */
public class AddingTable {
    public static void main(String args[]) throws Exception {
        // Creating a PdfDocument object
        String dest = "C:/itextExamples/addingTable.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdf);

        // Creating a table
        float[] pointColumnWidths = {150F, 150F, 150F};
        Table table = new Table(pointColumnWidths);

        // Adding cells to the table
        table.addCell(new Cell().add(new Paragraph("Name")));
        table.addCell(new Cell().add(new Paragraph("Raju")));
        table.addCell(new Cell().add(new Paragraph("Id")));
        table.addCell(new Cell().add(new Paragraph("1001")));
        table.addCell(new Cell().add(new Paragraph("Designation")));
        table.addCell(new Cell().add(new Paragraph("Programmer")));

        // Adding Table to document
        doc.add(table);

        // Closing the document
        doc.close();
        System.out.println("Table created successfully..");
    }
}
