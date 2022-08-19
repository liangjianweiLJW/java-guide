package com.ljw.itext;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

/**
 * @Description: 缩放PDF中的图像
 * @Author: jianweil
 * @date: 2022/7/26 17:20
 */
public class SettingAutoScale {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter
        String dest = "C:/itextExamples/positionOfImage.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Creating a Document
        Document document = new Document(pdfDoc);

        // Creating an ImageData object
        String imFile = "C:/itextExamples/logo.jpg";
        ImageData data = ImageDataFactory.create(imFile);

        // Creating an Image object
        Image image = new Image(data);

        // Setting the position of the image to the center of the page
        image.setFixedPosition(100, 250);

        // Adding image to the document
        document.add(image);

        // Closing the document
        document.close();
        System.out.println("Image Scaled");
    }
}
