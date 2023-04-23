package com.dtzhejiang.irs.res.bill.app.service;
import com.dtzhejiang.irs.res.bill.infra.util.HtmlToPdfUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;


@Service
public class ExportPdfService {

    public void exportPdf(MultipartFile file, OutputStream outputStream) throws Exception {
        String waterMarkText = "自定义水印";
        InputStream inputStream = file.getInputStream();
        //微软雅黑在windows系统里的位置如下，linux系统直接拷贝该文件放在linux目录下即可
        HtmlToPdfUtils.convertToPdf(inputStream, waterMarkText, "", outputStream);
    }

}