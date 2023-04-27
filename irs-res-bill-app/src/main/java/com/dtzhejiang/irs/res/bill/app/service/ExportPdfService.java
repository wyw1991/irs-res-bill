package com.dtzhejiang.irs.res.bill.app.service;
import com.dtzhejiang.irs.res.bill.app.response.FileResp;
import com.dtzhejiang.irs.res.bill.infra.util.HtmlToPdfUtils;
import com.dtzhejiang.irs.res.bill.infra.util.WkHtmltoxPdfUtil;
import com.itextpdf.io.source.ByteArrayOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


@Service
@Slf4j
public class ExportPdfService {

    @Autowired
    FileService fileService;
    @Autowired
    ReportService reportService;
    public void exportPdf(MultipartFile file, ServletOutputStream outputStream) throws Exception {
        String waterMarkText = "自定义水印";
        InputStream inputStream = file.getInputStream();
        //微软雅黑在windows系统里的位置如下，linux系统直接拷贝该文件放在linux目录下即可
        InputStream inputStreamPdf=HtmlToPdfUtils.convertToPdf(inputStream, waterMarkText, "");
        FileResp.FileResult obj=(FileResp.FileResult)fileService.uploadFile(file.getOriginalFilename(),inputStreamPdf,file.getSize()).getData();
        //更新fileIds
        reportService.updateFileId(Long.parseLong(file.getOriginalFilename()), obj.getFileId());
    }

    public void exportPdfWk(MultipartFile file, ServletOutputStream outputStream) throws Exception {
        String waterMarkText = "自定义水印";
        InputStream inputStream = file.getInputStream();
        //微软雅黑在windows系统里的位置如下，linux系统直接拷贝该文件放在linux目录下即可
        InputStream inputStreamPdf=new ByteArrayInputStream(WkHtmltoxPdfUtil.getPdfByte(file));
        FileResp.FileResult obj=(FileResp.FileResult)fileService.uploadFile(file.getOriginalFilename(),inputStreamPdf,file.getSize()).getData();
        //更新fileIds
        log.info("-------------------开始存入fileIds==================");
        reportService.updateFileId(Long.parseLong(file.getOriginalFilename().replace(".html","")), obj.getFileId());
        log.info("-------------------结束存入fileIds=================");
    }


}