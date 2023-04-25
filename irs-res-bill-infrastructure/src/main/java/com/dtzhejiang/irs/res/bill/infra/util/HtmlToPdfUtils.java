package com.dtzhejiang.irs.res.bill.infra.util;
import com.dtzhejiang.irs.res.bill.infra.handle.PageEventHandler;
import com.dtzhejiang.irs.res.bill.infra.handle.WaterMarkEventHandler;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HtmlToPdfUtils {

    /**
     * html转pdf
     * @param  inputStream 输入流
     * @param  waterMark 水印
     * @param fontPath 字体路径，ttc后缀的字体需要添加<b>,0<b/>
     * @param  outputStream 输出流
     */
    public static void convertToPdf(InputStream inputStream, String waterMark, String fontPath, OutputStream outputStream) throws IOException {

        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfReader reader = new PdfReader(inputStream);//读文件
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
    }

}