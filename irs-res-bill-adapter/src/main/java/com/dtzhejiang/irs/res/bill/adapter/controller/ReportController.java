package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.ReportDTO;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import com.dtzhejiang.irs.res.bill.app.query.qry.ReportPageQry;
import com.dtzhejiang.irs.res.bill.app.service.ReportService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *主报告
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private  ReportService service;
    /**
     * 分页查询
     */
    @PostMapping("/page")
    public PageResponse<Report> page(@RequestBody ReportPageQry pageQry) {
        return service.page(pageQry);
    }


    /**
     * 应用ID查询主报告列表
     * @param applicationId 应用ID
     */
    @GetMapping("/list")
    public MultiResponse<Report> appRegister(String applicationId) {
        return MultiResponse.of(service.getList(applicationId));
    }

    /**
     * 生成报告/重新生成
     * @param reportId 主报告ID
     */
    @GetMapping("/generate")
    public SingleResponse saveSubReport(Long reportId) {
        service.generateReport(reportId);
        return SingleResponse.buildSuccess();
    }

    /**
     * 报告详情（子报告权限）
     */
    @GetMapping("/detail")
    public SingleResponse<ReportDTO> getReportDto(@NonNull Long reportId) {
        return SingleResponse.of(service.getDetail(reportId));
    }  
    
    /**
     * pdf详情
     */
    @GetMapping("/pdf")
    public SingleResponse<AppInfo> getPdf(@NonNull Long reportId) {
        return SingleResponse.of(service.getPdf(reportId));
    }

}
