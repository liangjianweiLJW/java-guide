package com.ljw.itext;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

/**
 * @Description: 平铺PDF页面
 * @Author: jianweil
 * @date: 2022/7/26 17:27
 */
public class TilingPDFPages {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter object
        String dest = "C:/itextExamples/tilingPdfPages.pdf";
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

        // Getting the size of the page
        PdfFormXObject pageCopy = origPage.copyAsFormXObject(destpdf);

        // Tile size
        Rectangle tileSize = PageSize.A4.rotate();
        AffineTransform transformationMatrix =
                AffineTransform.getScaleInstance(tileSize.getWidth() / orig.getWidth() *
                        2f, tileSize.getHeight() / orig.getHeight() * 2f);

        // The first tile
        PdfPage page =
                destpdf.addNewPage(PageSize.A4.rotate());

        PdfCanvas canvas = new PdfCanvas(page);
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObject(pageCopy, 0, -orig.getHeight() / 2f);

        // The second tile
        page = destpdf.addNewPage(PageSize.A4.rotate());
        canvas = new PdfCanvas(page);
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObject(pageCopy, -orig.getWidth() / 2f, -orig.getHeight() / 2f);

        // The third tile
        page = destpdf.addNewPage(PageSize.A4.rotate());
        canvas = new PdfCanvas(page);
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObject(pageCopy, 0, 0);

        // The fourth tile
        page = destpdf.addNewPage(PageSize.A4.rotate());
        canvas = new PdfCanvas(page);
        canvas.concatMatrix(transformationMatrix);
        canvas.addXObject(pageCopy, -orig.getWidth() / 2f, 0);

        // closing the documents
        destpdf.close();
        srcPdf.close();

        System.out.println("PDF created successfully..");
    }
}
