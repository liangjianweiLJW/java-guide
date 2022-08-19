package com.ljw.itext;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;

/**
 * @Description: 将列表添加到 PDF 中的表格
 * @Author: jianweil
 * @date: 2022/7/26 17:19
 */
public class AddingListsToTable {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter object
        String file = "C:/itextExamples/addingObjects.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));

        // Creating a Document object
        Document doc = new Document(pdfDoc);

        // Creating a table
        float [] pointColumnWidths = {300F, 300F};
        Table table = new Table(pointColumnWidths);

        // Adding row 1 to the table
        Cell c1 = new Cell();
        c1.add(new Paragraph("Java Related Tutorials"));
        c1.setTextAlignment(TextAlignment.LEFT);
        table.addCell(c1);

        List list1 = new List();
        ListItem item1 = new ListItem("JavaFX");
        ListItem item2 = new ListItem("Java");
        ListItem item3 = new ListItem("Java Servlets");
        list1.add(item1);
        list1.add(item2);
        list1.add(item3);

        Cell c2 = new Cell();
        c2.add(list1);
        c2.setTextAlignment(TextAlignment.LEFT);
        table.addCell(c2);

        // Adding row 2 to the table
        Cell c3 = new Cell();
        c3.add(new Paragraph("No SQL Databases"));
        c3.setTextAlignment(TextAlignment.LEFT);
        table.addCell(c3);

        List list2 = new List();
        list2.add(new ListItem("HBase"));
        list2.add(new ListItem("Neo4j"));
        list2.add(new ListItem("MongoDB"));

        Cell c4 = new Cell();
        c4.add(list2);
        c4.setTextAlignment(TextAlignment.LEFT);
        table.addCell(c4);

        // Adding Table to document
        doc.add(table);

        // Closing the document
        doc.close();
        System.out.println("Lists added to table successfully..");
    }
}
