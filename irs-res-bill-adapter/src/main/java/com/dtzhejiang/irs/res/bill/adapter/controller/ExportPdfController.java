package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.AppAndReportDTO;
import com.dtzhejiang.irs.res.bill.app.service.ExportPdfService;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/**
 * PDF资源
 */
@RestController
@Slf4j
@RequestMapping("/pdf")
public class ExportPdfController {


    @Resource
    private ExportPdfService exportPdfService;

    /**
     * @param file html文件
     * @param response
     * @param request
     * @throws Exception
     */
    @PostMapping("/exportWk")
    public SingleResponse<String> exportWk(MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws Exception {
        try{
            String fileNamePrefix = file.getOriginalFilename();
            String date = format(new Date(), "yy-mm-dd HH:mm");
            String fileName = fileNamePrefix + "_" + date +".pdf";
            this.resolveResponse(request,response, fileName);
           return SingleResponse.of(this.exportPdfService.exportPdfWk(file, response.getOutputStream())) ;
        } catch (Exception e) {
            log.error(e.toString());
           SingleResponse.buildFailure("500", e.getMessage());
        }
        return SingleResponse.of("null");
    }

    private void resolveResponse(HttpServletRequest request,HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/pdf");
        response.setCharacterEncoding("utf-8");
        setFileDownloadHeader(request,response,fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }

    /**
     * 防止浏览器下载文件名中文乱码
     * @param request
     * @param response
     * @param fileName
     */
    public void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        String userAgent = request.getHeader("USER-AGENT");
        try {
            String finalFileName = null;
            if(StringUtils.contains(userAgent, "MSIE")||StringUtils.contains(userAgent, "Trident") || StringUtils.contains(userAgent,"Edge")){//IE 浏览器
                finalFileName = URLEncoder.encode(fileName,"UTF8");
            }else{
                finalFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");//\" 解决Firefox下载英文+中文组合的文件名的问题
        } catch (UnsupportedEncodingException e) {
        }
    }

    private void resetResponse(HttpServletResponse response, Exception exception)throws IOException {
        // 重置response
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        String result = "下载文件失败，" + exception.getMessage();
        response.getWriter().println(result);
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public String format(Date date,String pattern){
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

}
