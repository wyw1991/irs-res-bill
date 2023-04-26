package com.dtzhejiang.irs.res.bill.infra.util;

import com.dtzhejiang.irs.res.bill.infra.handle.PageEventHandler;
import com.dtzhejiang.irs.res.bill.infra.handle.WaterMarkEventHandler;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.ICssApplierFactory;
import com.itextpdf.html2pdf.css.apply.impl.DefaultCssApplierFactory;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.text.Document;
import com.itextpdf.tool.xml.html.CssAppliers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.*;

@Slf4j
public class HtmlToPdfUtils {
    /**
     * html转pdf
     * @param  inputStream 输入流
     * @param  waterMark 水印
     * @param fontPath 字体路径，ttc后缀的字体需要添加<b>,0<b/>
     * @param  outputStream 输出流
     */
    public static InputStream convertToPdf(InputStream inputStream, String waterMark, String fontPath,OutputStream outputStream) throws IOException {
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
        outputStream=baos;
        return getFile(baos) ;
    }

      public static InputStream getFile(ByteArrayOutputStream outputStream) throws IOException {

          MultipartFile multipartFile = new OutputStreamToMultipartFile("file", "wyw", "application/octet-stream", outputStream);

        return multipartFile.getInputStream();
    }



}