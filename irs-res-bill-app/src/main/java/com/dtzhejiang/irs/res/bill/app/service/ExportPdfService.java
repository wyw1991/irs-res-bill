package com.dtzhejiang.irs.res.bill.app.service;
import com.dtzhejiang.irs.res.bill.app.response.FileResp;
import com.dtzhejiang.irs.res.bill.common.util.DateUtil;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
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
        InputStream inputStreamPdf=HtmlToPdfUtils.updatePdf(inputStream, waterMarkText, "");
        FileResp.FileResult obj=(FileResp.FileResult)fileService.uploadFile(file.getOriginalFilename(),inputStreamPdf,file.getSize()).getData();
        //更新fileIds
        reportService.updateFileId(Long.parseLong(file.getOriginalFilename()), obj.getFileId());
    }

    public String exportPdfWk(MultipartFile file, ServletOutputStream outputStream) throws Exception {
        //微软雅黑在windows系统里的位置如下，linux系统直接拷贝该文件放在linux目录下即可
        log.error("-------------------wyw:开始转化file==================");
        InputStream inputStreamPdf=new ByteArrayInputStream(WkHtmltoxPdfUtil.getPdfByte(file));
        log.error("-------------------wyw:开始获取reportId==================");
        Long reportId=Long.parseLong(file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".")));
        log.error("-------------------wyw:开始获取reportName==================");
        Report report=reportService.getReport(reportId);
        //组装水印
        String waterMarkText = "IRS应用试运行报告"+ DateUtil.convertLocalDate(DateUtil.toLocalDate(report.getFinishTime()));
        inputStreamPdf=HtmlToPdfUtils.setWatermarkText(inputStreamPdf, waterMarkText);
        String name=report.getName()+".html";
        log.error("-------------------wyw:开始上传file==================");
        FileResp.FileResult obj=(FileResp.FileResult)fileService.uploadFile(name,inputStreamPdf,file.getSize()).getData();
        //更新fileIds
        log.error("-------------------wyw:开始存入fileIds==================");
        reportService.updateFileId(reportId, obj.getFileId());
        log.error("-------------------wyw:结束存入fileIds=================");
        return obj.getFileId();
    }


}