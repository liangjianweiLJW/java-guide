package com.ljw.itext;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

/**
 * @Description:
 * 第 1 步：创建一个 PdfWriter 对象
 * 该PdfWriter类表示PDF文档的作家。此类属于包com.itextpdf.kernel.pdf。此类的构造函数接受一个字符串，表示要在其中创建 PDF 的文件的路径。
 * 通过向其构造函数传递一个字符串值（表示您需要创建 PDF 的路径）来实例化 PdfWriter 类，如下所示。
 * <p>
 * 第 2 步：创建一个 PdfDocument 对象
 * 该PdfDocument类为表示在iText的PDF文档类。此类属于包com.itextpdf.kernel.pdf。要实例化此类（在写入模式下），您需要将PdfWriter类的对象传递给其构造函数。
 * 通过将上面创建的 PdfWriter 对象传递给其构造函数来实例化 PdfDocument 类，如下所示。
 * <p>
 * 第 3 步：添加一个空页面
 * PdfDocument类的addNewPage()方法用于在 PDF 文档中创建一个空白页面。
 * 为上一步创建的 PDF 文档添加一个空白页面，如下所示。
 * <p>
 * 第 4 步：创建一个 Document 对象
 * 包com.itextpdf.layout的Document类是创建自给自足的 PDF 时的根元素。此类的构造函数之一接受类 PdfDocument 的对象。
 * 通过传递在前面的步骤中创建的类PdfDocument的对象来实例化Document类，如下所示。
 * <p>
 * 步骤 5：关闭文档
 * 使用Document类的close()方法关闭文档，如下所示。
 *
 * @Author: jianweil
 * @date: 2022/7/26 17:08
 */
public class Create_PDF {
    public static void main(String args[]) throws Exception {
        // 1、Creating a PdfWriter
        String dest = "C:/itextExamples/sample.pdf";
        PdfWriter writer = new PdfWriter(dest);

        // 2、Creating a PdfDocument
        PdfDocument pdfDoc = new PdfDocument(writer);

        // 3、Adding an empty page
        pdfDoc.addNewPage();

        // 4、Creating a Document
        Document document = new Document(pdfDoc);

        // 5、Closing the document
        document.close();
        System.out.println("PDF Created");
    }
}
