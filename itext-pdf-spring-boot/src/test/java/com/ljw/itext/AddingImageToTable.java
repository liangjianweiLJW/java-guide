package com.ljw.itext;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

/**
 * @Description: 将图像添加到表格
 * @Author: jianweil
 * @date: 2022/7/26 17:18
 */
public class AddingImageToTable {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter object
        String dest = "C:/itextExamples/addingImage.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdfDoc);

        // Creating a table
        float[] pointColumnWidths = {150f, 150f};
        Table table = new Table(pointColumnWidths);

        // Populating row 1 and adding it to the table
        Cell cell1 = new Cell();
        cell1.add(new Paragraph("Tutorial ID"));
        table.addCell(cell1);

        Cell cell2 = new Cell();
        cell2.add(new Paragraph("1"));
        table.addCell(cell2);

        // Populating row 2 and adding it to the table
        Cell cell3 = new Cell();
        cell3.add(new Paragraph("Tutorial Title"));
        table.addCell(cell3);

        Cell cell4 = new Cell();
        cell4.add(new Paragraph("JavaFX"));
        table.addCell(cell4);

        // Populating row 3 and adding it to the table
        Cell cell5 = new Cell();
        cell5.add(new Paragraph("Tutorial Author"));
        table.addCell(cell5);

        Cell cell6 = new Cell();
        cell6.add(new Paragraph("Krishna Kasyap"));
        table.addCell(cell6);

        // Populating row 4 and adding it to the table
        Cell cell7 = new Cell();
        cell7.add(new Paragraph("Submission date"));
        table.addCell(cell7);

        Cell cell8 = new Cell();
        cell8.add(new Paragraph("2016-07-06"));
        table.addCell(cell8);

        // Populating row 5 and adding it to the table
        Cell cell9 = new Cell();
        cell9.add(new Paragraph("Tutorial Icon"));
        table.addCell(cell9);

        // Creating the cell10
        Cell cell10 = new Cell();

        // Creating an ImageData object
        String imageFile = "C:/itextExamples/javafxLogo.jpg";
        ImageData data = ImageDataFactory.create(imageFile);

        // Creating the image
        Image img = new Image(data);

        // Adding image to the cell10
        cell10.add(img.setAutoScale(true));

        // Adding cell110 to the table
        table.addCell(cell10);

        // Adding Table to document
        doc.add(table);

        // Closing the document
        doc.close();

        System.out.println("Image added to table successfully..");
    }
}
