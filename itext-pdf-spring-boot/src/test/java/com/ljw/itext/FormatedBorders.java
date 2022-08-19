package com.ljw.itext;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

/**
 * @Description: 格式化单元格的边框
 * @Author: jianweil
 * @date: 2022/7/26 17:17
 */
public class FormatedBorders {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter object
        String dest = "C:/itextExamples/coloredBorders.pdf";

        PdfWriter writer = new
                PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdfDoc);

        // Creating a table
        float[] pointColumnWidths = {200F, 200F};
        Table table = new Table(pointColumnWidths);

        // Adding row 1 to the table
        Cell c1 = new Cell();

        // Adding the contents of the cell
        c1.add(new Paragraph("Name"));

        // Setting the back ground color of the cell
        c1.setBackgroundColor(ColorConstants.DARK_GRAY);

        // Instantiating the Border class
        Border b1 = new DashedBorder(ColorConstants.RED, 3);

        // Setting the border of the cell
        c1.setBorder(b1);

        // Setting the text alignment
        c1.setTextAlignment(TextAlignment.CENTER);

        // Adding the cell to the table
        table.addCell(c1);
        Cell c2 = new Cell();
        c2.add(new Paragraph("Raju"));
        c1.setBorder(new SolidBorder(ColorConstants.RED, 3));
        c2.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c2);

        // Adding row 2 to the table
        Cell c3 = new Cell();
        c3.add(new Paragraph("Id"));
        c3.setBorder(new DottedBorder(ColorConstants.DARK_GRAY, 3));
        c3.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c3);

        Cell c4 = new Cell();
        c4.add(new Paragraph("001"));
        c4.setBorder(new DoubleBorder(ColorConstants.DARK_GRAY, 3));
        c4.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c4);

        // Adding row 3 to the table
        Cell c5 = new Cell();
        c5.add(new Paragraph("Designation"));
        c5.setBorder(new RoundDotsBorder(ColorConstants.RED, 3));
        c5.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c5);

        Cell c6 = new Cell();
        c6.add(new Paragraph("Programmer"));
        c6.setBorder(new RoundDotsBorder(ColorConstants.RED, 3));
        c6.setTextAlignment(TextAlignment.CENTER);
        table.addCell(c6);

        // Adding Table to document
        doc.add(table);

        // Closing the document
        doc.close();

        System.out.println("Borders added successfully..");
    }
}
