package com.ljw.itext;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

/**
 * @Description: 设置PDF中文本的字体
 * @Author: jianweil
 * @date: 2022/7/26 17:26
 */
public class FormatingTheText {
    public static void main(String args[]) throws Exception {
        // Creating a PdfWriter object
        String dest = "C:/itextExamples/fonts.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdf);

        // Creating text object
        Text text1 = new Text("Tutorialspoint");

        // Setting font of the text
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        text1.setFont(font);

        // Setting font color
        text1.setFontColor(ColorConstants.GREEN);

        // Creating text object
        Text text2 = new Text("Simply Easy Learning");
        text2.setFont(PdfFontFactory.createFont(FontConstants.HELVETICA));

        // Setting font color
        text2.setFontColor(ColorConstants.BLUE);

        // Creating Paragraph
        Paragraph paragraph1 = new Paragraph();

        // Adding text1 to the paragraph
        paragraph1.add(text1);
        paragraph1.add(text2);

        // Adding paragraphs to the document
        doc.add(paragraph1);
        doc.close();

        System.out.println("Text added to pdf ..");
    }
}
