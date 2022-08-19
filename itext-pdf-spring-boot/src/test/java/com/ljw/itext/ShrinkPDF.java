package com.ljw.itext;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;

/**
 * @Description: 缩小PDF中的内容
 * @Author: jianweil
 * @date: 2022/7/26 17:26
 */
public class ShrinkPDF {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter object
        String dest = "C:/itextExamples/shrinking.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfReader
        String src = "C:/itextExamples/pdfWithImage.pdf";
        PdfReader reader = new PdfReader(src);

        // Creating a PdfDocument objects
        PdfDocument destpdf = new PdfDocument(writer);
        PdfDocument srcPdf = new PdfDocument(reader);

        // Opening a page from the existing PDF
        PdfPage origPage = srcPdf.getPage(1);

        // Getting the page size
        Rectangle orig = origPage.getPageSizeWithRotation();

        // Adding a page to destination Pdf
        PdfPage page = destpdf.addNewPage();

        // Scaling the image in a Pdf page
        AffineTransform transformationMatrix = AffineTransform.getScaleInstance(
                page.getPageSize().getWidth() / orig.getWidth() / 2,
                page.getPageSize().getHeight() / orig.getHeight() / 2);

        // Shrink original page content using transformation matrix
        PdfCanvas canvas = new PdfCanvas(page);
        canvas.concatMatrix(transformationMatrix);

        // Add the object to the canvas
        PdfFormXObject pageCopy = origPage.copyAsFormXObject(destpdf);
        canvas.addXObject(pageCopy, 0, 0);

        // Creating a Document object
        Document doc = new Document(destpdf);

        // Closing the document
        doc.close();

        System.out.println("Table created successfully..");
    }
}
