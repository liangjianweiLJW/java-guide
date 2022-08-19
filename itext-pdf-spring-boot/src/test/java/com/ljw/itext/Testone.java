package com.ljw.itext;

import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfStamper;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description: 动态添加表格且自动换页
 * @Author: jianweil
 * @date: 2022/7/26 17:28
 */
public class Testone {

    public static void main(String[] args)throws Exception {
        File file = new File("C:/itextExamples/tilingPdfPages.pdf");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        byte[] bytes = fill_patient_info(fileContent).toByteArray();
        Path path = Paths.get("C:/itextExamples/tilingPdfPages.pdf");
        Files.write(path, bytes);
    }

    public static ByteArrayOutputStream fill_patient_info(byte[] file_data) throws IOException, DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(file_data);
        Rectangle pagesize = reader.getPageSize(1);
        int elementType = Element.ALIGN_LEFT;
        Font tableContent = new Font(com.itextpdf.text.Font.FontFamily.COURIER, 9, Font.BOLD);


        PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);

        // CREATE TABLE
        PdfPTable table = new PdfPTable(3);
        for (String s : Arrays.asList("TableColumn1", "TableColumn2", "TableColumn3")) {
            table.addCell(ItextPdfUtil.getTableHeaderCell(s));
        }

        table.setHeaderRows(1);
        // SET TABLE COLUMN WIDTH
        table.setWidths(new int[]{100,100,100});

        // ADD TABLE DATA
        for (int i = 1; i <= 150; i++) {
            table.addCell(new PdfPCell(new Phrase(elementType,"Test" + i,tableContent)));
            table.addCell(new PdfPCell(new Phrase(elementType,"Test" + i,tableContent)));
            table.addCell(new PdfPCell(new Phrase(elementType,"Test" + i,tableContent)));
        }

        ColumnText column = new ColumnText(stamper.getOverContent(1));
        column.setSimpleColumn(ItextPdfUtil.tableHeaderRectPage);
        column.addElement(table);

        int pagecount = 1;
        int status = column.go();

        while (ColumnText.hasMoreText(status)) {
            status = triggerNewPage(stamper, pagesize, column, ItextPdfUtil.tableContentRectPage, ++pagecount);
        }

        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();


        return byteArrayOutputStream;
    }

    public static int triggerNewPage(PdfStamper stamper, Rectangle pageSize,
                                     ColumnText column, Rectangle rect,
                                     int pageCount) throws DocumentException {
        stamper.insertPage(pageCount, pageSize);
        PdfContentByte canvas = stamper.getOverContent(pageCount);
        column.setCanvas(canvas);
        column.setSimpleColumn(rect);
        return column.go();
    }

    /**
     * Merger两个PDF
     * merger all pdf
     * @param readers all pdf reader
     * @param outputStream out stream
     * @return
     */
    public static ByteArrayOutputStream mergerPdf(List<PdfReader> readers, ByteArrayOutputStream outputStream){
        Document document = new Document();
        try{
            PdfCopy copy = new PdfCopy(document, outputStream);
            document.open();
            int n;
            for(int i = 0 ; i < readers.size(); i++){
                PdfReader reader = readers.get(i);
                n = reader.getNumberOfPages();
                for(int page = 0; page < n;){
                    copy.addPage(copy.getImportedPage(reader, ++page));
                }
                copy.freeReader(reader);
                reader.close();
            }
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return outputStream;
    }

}
