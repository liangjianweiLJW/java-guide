package com.ljw.itext;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;


/**
 * @Description: 格式化表格中的单元格
 * @Author: jianweil
 * @date: 2022/7/26 17:16
 */
public class BackgroundToTable {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter object
        String dest = "C:/itextExamples/addingBackground.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdfDoc);

        // Creating a table
        float[] pointColumnWidths = {200F, 200F};
        Table table = new Table(pointColumnWidths);

        // Populating row 1 and adding it to the table
        Cell c1 = new Cell();                        // Creating cell 1
        c1.add(new Paragraph("Name"));                              // Adding name to cell 1
        c1.setBackgroundColor(ColorConstants.DARK_GRAY);      // Setting background color
        c1.setBorder(Border.NO_BORDER);              // Setting border
        c1.setTextAlignment(TextAlignment.CENTER);   // Setting text alignment
        table.addCell(c1);                           // Adding cell 1 to the table

        Cell c2 = new
                Cell();
        c2.add(new Paragraph("Raju"));
        c2.setBackgroundColor(ColorConstants.GRAY);
        c2.setBorder(Border.NO_BORDER);
        c2.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c2);

        // Populating row 2 and adding it to the table
        Cell c3 = new Cell();
        c3.add(new Paragraph("Id"));
        c3.setBackgroundColor(ColorConstants.WHITE);
        c3.setBorder(Border.NO_BORDER);
        c3.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c3);

        Cell c4 = new Cell();
        c4.add(new Paragraph("001"));
        c4.setBackgroundColor(ColorConstants.WHITE);
        c4.setBorder(Border.NO_BORDER);
        c4.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c4);

        // Populating row 3 and adding it to the table
        Cell c5 = new Cell();
        c5.add(new Paragraph("Designation"));
        c5.setBackgroundColor(ColorConstants.DARK_GRAY);
        c5.setBorder(Border.NO_BORDER);
        c5.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c5);

        Cell c6 = new Cell();
        c6.add(new Paragraph("Programmer"));
        c6.setBackgroundColor(ColorConstants.GRAY);
        c6.setBorder(Border.NO_BORDER);
        c6.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c6);

        // Adding Table to document
        doc.add(table);

        // Closing the document
        doc.close();

        System.out.println("Background added successfully..");
    }
}
