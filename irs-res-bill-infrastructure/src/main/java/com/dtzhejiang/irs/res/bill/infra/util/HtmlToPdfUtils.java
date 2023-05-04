package com.dtzhejiang.irs.res.bill.infra.util;

import com.dtzhejiang.irs.res.bill.infra.handle.PageEventHandler;
import com.dtzhejiang.irs.res.bill.infra.handle.WaterMarkEventHandler;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class HtmlToPdfUtils {
    /**
     * html转pdf
     * @param  inputStream 输入流
     * @param  waterMark 水印
     * @param fontPath 字体路径，ttc后缀的字体需要添加<b>,0<b/>
     */

    public static InputStream updatePdf(InputStream inputStream, String waterMark, String fontPath) throws IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(baos);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        //设置为A4大小
        pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
        //添加水印
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new WaterMarkEventHandler(waterMark));
        //添加页码
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE,new PageEventHandler());
        //添加中文字体支持
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new FontProvider();

        PdfFont sysFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        fontProvider.addFont(sysFont.getFontProgram(), "UniGB-UCS2-H");

        //添加自定义字体，例如微软雅黑
        /*if (StringUtils.isNotBlank(fontPath)){
            PdfFont microsoft = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, false);
            fontProvider.addFont(microsoft.getFontProgram(), PdfEncodings.IDENTITY_H);
        }*/

        properties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(inputStream, pdfDocument, properties);
        pdfWriter.close();
        pdfDocument.close();
        return new ByteArrayInputStream(baos.toByteArray()) ;
    }
    /**
     * 设置文字水印
     * @param input
     * @param text
     * @throws DocumentException
     * @throws IOException
     */
    public static InputStream setWatermarkText(InputStream input, String text)
            throws DocumentException, IOException, com.lowagie.text.DocumentException {
        PdfReader reader = new PdfReader(input);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages()+1;
        PdfContentByte content;
        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.15f);//设置透明度
        PdfGState gs2 = new PdfGState();
        gs2.setFillOpacity(1f);
        for (int i = 1; i < total; i++) {
            content = stamper.getOverContent(i);// 在内容上方加水印
            //content = stamper.getUnderContent(i);//在内容下方加水印
            //水印内容
            content.setGState(gs1);
            content.beginText();
            content.setColorFill(BaseColor.BLACK);
            content.setFontAndSize(base, 30);
            content.setTextMatrix(120, 100);
            //350为x坐标 350y坐标  45为旋转45度
            content.showTextAligned(Element.ALIGN_CENTER, text, 250, 350, 30);
            content.endText();//结束文字
            //页脚内容
            content.setGState(gs2);
            content.beginText();
            content.setColorFill(BaseColor.BLACK);
            content.setFontAndSize(base, 12);
            content.setTextMatrix(100, 200);
            content.showTextAligned(Element.ALIGN_CENTER, "第"+i+ "页,共"+(total-1)+"页", 300, 10, 0);
            content.endText();
        }
        stamper.close();
        return new ByteArrayInputStream(bos.toByteArray()) ;
    }

}