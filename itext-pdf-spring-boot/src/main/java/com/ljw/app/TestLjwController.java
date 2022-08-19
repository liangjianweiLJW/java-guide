package com.ljw.app;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.ljw.print.TextFooterEventHandler;
import com.ljw.utils.CnNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;

/**
 * 订单子表控制器
 */

@RestController
@RequestMapping({"/test"})
public class TestLjwController {

    private static final Logger logger = LoggerFactory.getLogger(TestLjwController.class);


    @GetMapping("/ljw2")
    public String ljw(HttpServletResponse response) throws Exception {

        String filename = URLEncoder.encode("公司1", "UTF-8") + 123 + ".pdf";
        response.setContentType("application/pdf");
        response.setHeader("filename", filename);
        response.setHeader("Content-Disposition", "inline;filename=" + filename);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        printPdf(out, filename);
        out.writeTo(response.getOutputStream());
        return null;
    }

    /**
     * 测试打印pdf demo
     *
     * @param out
     * @return
     */
    private void printPdf(ByteArrayOutputStream out, String filePath1) {

        PdfDocument pdf = null;
        Document document = null;
        String classpath = "D:\\project\\my\\java-guide\\itext-pdf-spring-boot\\src\\main\\resources";
        try {
            //###################定义字体###################
            final PdfFont fontHei = PdfFontFactory.createFont(classpath + "/static/font/simhei.ttf", PdfEncodings.IDENTITY_H, true);//中文黑体
            final PdfFont fontSong = PdfFontFactory.createFont(classpath + "/static/font/simsun.ttf", PdfEncodings.IDENTITY_H, true);//中文宋体
            final PdfFont wingdings = PdfFontFactory.createFont(classpath + "/static/font/Wingdings.ttf", PdfEncodings.IDENTITY_H, true);//英文Arial
            //内容样式
            final Style contentStyle = new Style().setFont(fontSong).setFontSize(10).setPaddingTop(0).setPaddingBottom(0).setMarginTop(1).setMarginBottom(0);
            //内容样式
            final Style wingdingsStyle = new Style().setFont(wingdings).setFontSize(10).setPaddingTop(0).setPaddingBottom(0).setMarginTop(1).setMarginBottom(0);
            //表体样式
            final Style tableBodyStyleEn = new Style().setFont(fontSong).setFontSize(8).setMarginTop(0.5f);
            //表体样式无边框
            final Style tableBodyStyleEnNoBorder = new Style().setFont(fontSong).setFontSize(10).setBorder(Border.NO_BORDER).setMarginTop(0).setPaddings(0, 0, 0, 0);
            //单元格样式
            final Style cellStyle = new Style().setTextAlignment(TextAlignment.LEFT)
                    //设置垂直对齐
                    .setVerticalAlignment(VerticalAlignment.MIDDLE).setPaddings(0.5f, 3, 0.5f, 3);


            //创建Writer实例
            PdfWriter writer = null;

            File file = null;
            if (out != null) {
                //创建Writer实例
                writer = new PdfWriter(out);
            } else {
                String filePath = "D:/project/my/java-guide/itext-pdf-spring-boot/src/main/resources/static/templates/testljw.pdf";
                file = new File(filePath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                writer = new PdfWriter(new FileOutputStream(file));
            }

            boolean isSeal = true;
            //创建pdf对象
            pdf = new PdfDocument(writer);
            //创建Document对象
            //isSeal true 盖章  false 不盖章  用于电子签约
            if (isSeal) {
                document = new Document(pdf, PageSize.A4, false);
            } else {
                document = new Document(pdf, PageSize.A4);
            }
            //文档设置边距
            document.setMargins(10, 25, 10, 25);
            //文本页脚事件处理程序
            TextFooterEventHandler eh = new TextFooterEventHandler(document);
            pdf.addEventHandler(PdfDocumentEvent.START_PAGE, eh);

            //################每一行一个段落：Paragraph ################
            //段落-标题
            Paragraph title = new Paragraph();
            final Style titleStyle = new Style().setFont(fontHei).setFontSize(12).setTextAlignment(TextAlignment.CENTER).setMarginBottom(5).setMarginTop(5);
            title.add(new Text("我的标题")).addStyle(titleStyle);
            document.add(title);

            //内容(上)
            Paragraph topContent2 = new Paragraph();
            topContent2.add(new Text("合同编号：  1234567890123"))
                    .addStyle(contentStyle)
                    //设置右上角
                    .addStyle(new Style().setTextAlignment(TextAlignment.RIGHT));
            //把单独的段落添加到文档上
            document.add(topContent2);

            //一行分别有左右两个段落
            Paragraph topContent1 = new Paragraph();
            topContent1.add(new Text("供方：xxxxxxx1有限公司 (以下简称甲方)"))
                    .addStyle(contentStyle)
                    .addStyle(new Style().setTextAlignment(TextAlignment.LEFT));
            Paragraph topContentBuyer = new Paragraph();
            topContentBuyer.add(new Text("需方：xxxxxxx2有限公司  (以下简称乙方)"))
                    .addStyle(contentStyle)
                    .addStyle(new Style().setTextAlignment(TextAlignment.RIGHT));
            //新建表格，按百分比设定列宽度，三列宽为45 10 45 ，最终的列宽度取决于选定的布局、表格宽度、单元格的宽度、单元格的最小宽度和单元格的最大宽度
            Table table2 = new Table(UnitValue.createPercentArray(new float[]{45, 10, 45}));
            //表格宽度为100%
            table2.setWidth(UnitValue.createPercentValue(100));
            table2.setFixedLayout();//表格固定布局
            table2.setMarginTop(0);//表格前空白

            //单元格样式
            final Style cellStyleNotVer = new Style().setTextAlignment(TextAlignment.LEFT)
                    .setPaddings(0.5f, 3, 0.5f, 3);
            //单元格样式无边框
            final Style tableBodyStyleEnNoBorder2 = new Style().setFont(fontSong).setFontSize(10)
                    .setBorder(Border.NO_BORDER).setMarginTop(0).setPaddings(0, 0, 0, 0);

            //margin是指从自身边框到另一个容器边框之间的距离，就是容器外距离。（外边距）
            //padding是指自身边框到自身内部另一个容器边框之间的距离，就是容器内距离。（内边距)
            //表添加单元格
            table2.addCell(new Cell().add(topContent1)
                    //单元格样式
                    .addStyle(cellStyleNotVer).addStyle(tableBodyStyleEnNoBorder));
            table2.addCell(new Cell().add(new Paragraph(""))
                    .addStyle(cellStyleNotVer).addStyle(tableBodyStyleEnNoBorder));
            table2.addCell(new Cell().add(topContentBuyer)
                    .addStyle(cellStyleNotVer).addStyle(tableBodyStyleEnNoBorder));
            document.add(table2);


            Paragraph contentBegin1 = new Paragraph();
            contentBegin1.add(new Text("一、产品名称、商标型号、数量、金额及时间：           " + "发票类型：增值税专用发票" + "             币种：人民币"))
                    .addStyle(contentStyle);
            document.add(contentBegin1);


            //新建表格，按百分比设定8列宽度
            Table table = new Table(UnitValue.createPercentArray(new float[]{40, 10, 16, 5, 10, 10, 12, 12}));
            table.setWidth(UnitValue.createPercentValue(100));//表格宽度
            table.setFixedLayout();//表格固定布局
            table.setMarginTop(5);//表格前空白
            //表头
            table.addCell(new Cell().add(new Paragraph("产品名称")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("型号")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("规格")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("单位")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("数量")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("单价(元)")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("金额(元)")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("备注")).addStyle(cellStyle).addStyle(tableBodyStyleEn));

            //表体

            table.addCell(new Cell().add(new Paragraph("05--聚酰胺6（尼龙6、PA 6）UItramid® EX350\n09(南碱,扬子石化)")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("医药级,食品级")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("" + "TO-22,工业33")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("" + "袋")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("" + 10.56)).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("" + 1000)).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("" + 10560)).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("备注11")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            //document.add(table);


            BigDecimal sum = new BigDecimal("10560");
            BigDecimal notIncludeTaxSum = sum.divide(BigDecimal.ONE.add(new BigDecimal("0.13")), 2, BigDecimal.ROUND_HALF_UP);
            table.addCell(new Cell().add(new Paragraph("未税合计金额")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            int colspan = 5;
            //单元格合并1到5列，从0开始
            table.addCell(new Cell(1, colspan).add(new Paragraph("大写 " + CnNumberUtils.toUppercase(notIncludeTaxSum.stripTrailingZeros().toPlainString()))).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("" + notIncludeTaxSum.stripTrailingZeros().toPlainString())).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("备注22")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("含税合计金额（应付金额）")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell(1, colspan).add(new Paragraph("大写 " + CnNumberUtils.toUppercase(new BigDecimal("10560").stripTrailingZeros().toPlainString()))).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("" + new BigDecimal("10560").stripTrailingZeros().toPlainString())).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            table.addCell(new Cell().add(new Paragraph("")).addStyle(cellStyle).addStyle(tableBodyStyleEn));
            document.add(table);

            //内容(下)
            Paragraph contentEnd1 = new Paragraph();
            contentEnd1.add(new Text("备注：1、以上单价为含税单价 2、发票含13%的增值税")).addStyle(contentStyle);
            document.add(contentEnd1);

            Paragraph contentEnd2 = new Paragraph();
            contentEnd2.add(new Text("二、质量要求、技术标准及包装方式、验收期限：")).addStyle(contentStyle);
            document.add(contentEnd2);

            Paragraph contentEnd3 = new Paragraph();
            contentEnd3.add(new Text("1、质量要求、技术标准：以生产厂家的质量报告为准。 2、乙方收货时，须当场验收有无包装破损、数量缺漏等情况，在物流签收单上详细注明，并于当天将情况反馈给甲方。 " +
                    "3、若乙方在收到甲方货物后发现有质量问题，乙方须自收货之日起 柒 个工作日内向甲方提出书面异议。逾期未提出异议，则视为甲方所供货物符合要求。异议期满后，甲方不接受退、换货索赔等事宜。 " +
                    "4、因乙方在使用、保管等方面原因，造成产品质量下降等质量问题概与甲方无关，不得向甲方提出质量异议。")).addStyle(contentStyle);
            contentEnd3.setMultipliedLeading(1);
            document.add(contentEnd3);


            Paragraph contentEnd4 = new Paragraph();
            contentEnd4.add(new Text("三、交(提)货地点/时间/签收人/电话：")).addStyle(contentStyle);
            document.add(contentEnd4);

            Paragraph contentEnd5 = new Paragraph();
            contentEnd5.add(new Text("1、交（提）货地点： 北京北京市东城区3")).addStyle(contentStyle);
            contentEnd5.setMultipliedLeading(1);
            document.add(contentEnd5);

            Paragraph contentEnd6 = new Paragraph();
            contentEnd6.add(new Text("2、交（提）货时间：根据双方约定 " + "            3、乙方签收人及电话：ljw" + "  123456789")).addStyle(contentStyle);
            contentEnd6.setMultipliedLeading(1);
            document.add(contentEnd6);

            Paragraph contentEnd7 = new Paragraph();
            contentEnd7.add(new Text("4、凡乙方在收货栏签字确认，收货签字人员均视为乙方公司收货的授权代表人，乙方对此无异议。")).addStyle(contentStyle);
            contentEnd7.setMultipliedLeading(1);
            document.add(contentEnd7);

            Paragraph contentEnd8 = new Paragraph();
            contentEnd8.add(new Text("四、运输方式及风险承担：")).addStyle(contentStyle);
            document.add(contentEnd8);


            Paragraph contentEnd9 = new Paragraph();
            contentEnd9.add(new Text("1、运输方式：自提； 2、需方自提的,货物灭失、损毁的风险自货物装上需方指定的运输工具之时起转移;")).addStyle(contentStyle);
            contentEnd9.setMultipliedLeading(1);
            document.add(contentEnd9);

            Paragraph contentEnd10 = new Paragraph();
            contentEnd10.add(new Text("3、供方安排送货的,货物灭失、损毁的风险自货物进入需方指定地点时转移； 4、委托第三方承运，风险自货物转移时风险转移。")).addStyle(contentStyle);
            contentEnd10.setMultipliedLeading(1);
            document.add(contentEnd10);

            Paragraph contentEnd11 = new Paragraph();
            contentEnd11.add(new Text("五、结算方式、期限及收款账户：")).addStyle(contentStyle);
            contentEnd11.setMultipliedLeading(0.8F);
            document.add(contentEnd11);

            Paragraph contentEnd12 = new Paragraph();
            contentEnd12.add(new Text("1、结算方式:  ").addStyle(contentStyle))
                    //未勾选
                    .add(new Text(String.valueOf('\u00A8')).addStyle(wingdingsStyle))
                    .add(new Text("银行转账:款到发货，即甲方收到乙方支付的货款后发。  ").addStyle(contentStyle))
                    //未勾选
                    .add(new Text(String.valueOf('\u00A8')).addStyle(wingdingsStyle))
                    .add(new Text("账期(货到票到):  ").addStyle(contentStyle))
                    //勾选框
                    .add(new Text(String.valueOf('\u00FE')).addStyle(wingdingsStyle))
                    .add(new Text("承兑汇票").addStyle(contentStyle));
            contentEnd12.setMultipliedLeading(0.8F);
            document.add(contentEnd12);


            Paragraph contentEnd13 = new Paragraph();
            contentEnd13.add(new Text("2、甲方收款信息：乙方必须将货款汇入本合同甲方指定的银行账户，否则视为乙方未履行付款义务。")).addStyle(contentStyle);
            contentEnd13.setMultipliedLeading(1);
            document.add(contentEnd13);

            Paragraph contentEnd14 = new Paragraph();
            contentEnd14.add(new Text("收款公司名称：xxxxxxx1有限公司" + "  款行名称：建设银行天河支行（股份现款）" + "  帐号：1234567890")).addStyle(contentStyle);
            contentEnd14.setMultipliedLeading(1);
            document.add(contentEnd14);


            Paragraph contentEnd15 = new Paragraph();
            contentEnd15.add(new Text("六、违约责任：")).addStyle(contentStyle);
            document.add(contentEnd15);

            Paragraph contentEnd16 = new Paragraph();
            contentEnd16.add(new Text("1、乙方逾期向甲方支付货款的，每逾期一天应向甲方支付逾期货款总额的千分之三作为违约金，逾期超过15天，" +
                    "乙方应向甲方支付逾期货款总额的30%作为违约金。 2、如乙方违约，甲方因维权而支出的一切合理费用（包括" +
                    "但不限于律师费、诉讼费、鉴定费、差旅费等）均由乙方承担。3、如乙方支付定金后不履行合同或单方解除合同，乙方支付的定金甲方不予返还。")).addStyle(contentStyle);
            contentEnd16.setMultipliedLeading(1);
            document.add(contentEnd16);

            Paragraph contentEnd17 = new Paragraph();
            contentEnd17.add(new Text("七、纠纷解决方式：")).addStyle(contentStyle);
            document.add(contentEnd17);

            Paragraph contentEnd18 = new Paragraph();
            contentEnd18.add(new Text("本合同履行过程中产生争议的，双方友好协商解决，协商不成的，提交本合同签订地广州市黄埔区人民法院诉讼解决。")).addStyle(contentStyle);
            document.add(contentEnd18);

            Paragraph contentEnd19 = new Paragraph();
            contentEnd19.add(new Text("八、补充条款：")).addStyle(contentStyle);
            document.add(contentEnd19);

            //发货公司是原料的自动取消订单规则的有效时间为48小时，生成的合同模板的自动取消说明的有效时间为48小时
            int validTime = 48;
            Paragraph contentEnd20 = new Paragraph();
            contentEnd20.add(new Text("1、凡涉及易制毒化学品管理条例中规定的产品，需方需持有当地公安机关所办理的第二、第三类易制毒化学品购买备案证明原件方能购买，购买合同自签订日期起六个月内有效。 " +
                    "2、供方原因导致的有产品质量问题的产品可退换货，退换手续必须符合国家相关规定，非供方原因导致的的质量问题产品不予退、换货。3、本合同生效以乙方付款为要件。" +
                    "乙方下达订单后应在" + validTime + "小时内付款，超过" + validTime + "小时未付款，订单失效。本合同不生效。4、散水类产品的合理损耗为0.3%")).addStyle(contentStyle);
            contentEnd20.setMultipliedLeading(1);
            document.add(contentEnd20);

            Paragraph contentEnd21 = new Paragraph();
            contentEnd21.add(new Text("九、其他约定事项：")).addStyle(contentStyle);
            document.add(contentEnd21);

            Paragraph contentEnd22 = new Paragraph();
            contentEnd22.add(new Text("1、本协议一式两份，经甲乙双方盖实物印章或电子印章后生效。电子印章与实物印章具有同等法律效力，甲乙双方各持一份。本协议由双方盖章后通过传真、微信、QQ、邮箱等电子形式互传，因此双方确认且同意双方通过各种电子形式互传的扫描件、" +
                    "传真件等电子档合同具有与合同原件同等的法律效力。 2、乙方应最迟于发货前24小时内以书面方式将有效收货人信息通知甲方。否则，在收货单或者其他送货凭证上签字的人员均视为得到乙方授权。")).addStyle(contentStyle);
            contentEnd22.setMultipliedLeading(1);
            document.add(contentEnd22);

            float topBegin = document.getRenderer().getCurrentArea().getBBox().getTop();
            if (topBegin < 141) {
                //翻页，加7行
                int num = (int) (topBegin / 20) + 1;
                for (int i1 = 0; i1 < num; i1++) {
                    Paragraph contentEndTemp = new Paragraph();
                    contentEndTemp.add(new Text("\n")).addStyle(contentStyle);
                    document.add(contentEndTemp);

                }
                Table tableEnd = new Table(UnitValue.createPercentArray(new float[]{50, 50}));//新建表格，按百分比设定列宽度
                tableEnd.setWidth(UnitValue.createPercentValue(100));//表格宽度
                tableEnd.setFixedLayout();//表格固定布局
                tableEnd.setMarginTop(5);//表格前空白
                tableEnd.setBorder(Border.NO_BORDER);
                tableEnd.addCell(new Cell().add(new Paragraph("甲方：xxxxxxx1有限公司 ")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("乙方：xxxx公司")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));

                tableEnd.addCell(new Cell().add(new Paragraph("单位名称(章)：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("单位名称(章)：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));

                tableEnd.addCell(new Cell().add(new Paragraph("法定代表人：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("法定代表人：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));

                tableEnd.addCell(new Cell().add(new Paragraph("甲方委托人：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("乙方委托人：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));

                tableEnd.addCell(new Cell().add(new Paragraph("委托代理人身份证号：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("委托代理人身份证号：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));

                tableEnd.addCell(new Cell().add(new Paragraph("电话：1243535" + "；传真：22222")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("电话/传真：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("签约日期：2021-06-01")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("签约日期：2021-06-01")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                document.add(tableEnd);
            } else {
                Table tableEnd = new Table(UnitValue.createPercentArray(new float[]{50, 50}));//新建表格，按百分比设定列宽度
                tableEnd.setWidth(UnitValue.createPercentValue(100));//表格宽度
                tableEnd.setFixedLayout();//表格固定布局
                tableEnd.setMarginTop(5);//表格前空白
                tableEnd.setBorder(Border.NO_BORDER);
                tableEnd.addCell(new Cell().add(new Paragraph("甲方：xxxxxxx1有限公司")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("乙方：xxxx公司")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("单位名称(章)：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("单位名称(章)：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("法定代表人：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("法定代表人：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("甲方委托人：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("乙方委托人：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("委托代理人身份证号：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("委托代理人身份证号：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("电话：1243535" + "；传真：22222")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("电话/传真：")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("签约日期：2021-06-01")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                tableEnd.addCell(new Cell().add(new Paragraph("签约日期：2021-06-01")).addStyle(cellStyle).addStyle(tableBodyStyleEnNoBorder));
                document.add(tableEnd);
            }

            int pageCount = pdf.getNumberOfPages();//获取总页数
            for (int j = 1; j <= pageCount; j++) {
                if (isSeal) {
                    String filePath;

                    filePath = classpath + File.separator + "static" + File.separator + "image" + File.separator + "yz.png";

                    ImageData imageData = ImageDataFactory.create(filePath);
                    //每英寸点数 分辨率
                    int dpi = 300;
                    imageData.setDpi(dpi, dpi);
                    Image image = new Image(imageData);
                    //将图像缩放到绝对大小
                    float fitNumber = 117.559f;
                    image.scaleAbsolute(fitNumber, fitNumber);
                    //盖章 x轴
                    float x = 200;
                    //如果是最后一页，取渲染器当前位置，否则使用固定垂直位置
                    //getTop 渲染器当前位置的左上角点的y坐标
                    float y = j == pageCount ? document.getRenderer().getCurrentArea().getBBox().getTop() : 100;
                    if (pageCount > 1 && j == 1) {
                        //有多页的第一页
                        document.add(image.setFixedPosition(j, 30, 723));
                    } else if (pageCount == j) {
                        //第一页
                        x = 40;
                        //印章设置在以y为底边距，x为左边距
                        document.add(image.setFixedPosition(j, x, y));
                    } else {
                        document.add(image.setFixedPosition(j, x, y));
                    }
                }

            }
        } catch (RuntimeException e) {
            logger.error("", e);
            throw e;
        } catch (Exception e) {
            logger.error("合同打印异常: 11223333", e);
            throw new RuntimeException(e.getMessage());
        } finally {
            //关闭Document
            if (document != null) {
                try {
                    document.close();
                } catch (Exception e) {
                    logger.error("合同打印流document关闭异常: 11223333", e);
                    throw new RuntimeException("合同打印失败: 11223333" + " " + e.getMessage());
                }
            }
            if (pdf != null) {
                try {
                    pdf.close();
                } catch (Exception e) {
                    logger.error("合同打印流pdf关闭异常: 11223333", e);
                    throw new RuntimeException("合同打印失败: 11223333" + " " + e.getMessage());
                }
            }
        }
    }

}
