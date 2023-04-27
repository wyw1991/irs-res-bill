package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.AppAndReportDTO;
import com.dtzhejiang.irs.res.bill.app.dto.AppInfoDTO;
import com.dtzhejiang.irs.res.bill.app.dto.ReportDTO;
import com.dtzhejiang.irs.res.bill.app.job.AppInfoSyncTask;
import com.dtzhejiang.irs.res.bill.app.query.qry.ReportPageQry;
import com.dtzhejiang.irs.res.bill.app.service.ReportService;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import lombok.NonNull;
import org.apache.ibatis.annotations.Param;
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
    @Autowired
    private AppInfoSyncTask task;
    /**
     * 分页查询
     */
    @PostMapping("/page")
    public PageResponse<Report> page(@RequestBody ReportPageQry pageQry) {
        return service.page(pageQry);
    }


    /**
     * 应用ID查询主报告列表
     * @param pageQry
     */
    @PostMapping("/list")
    public MultiResponse<Report> appRegister(@RequestBody ReportPageQry pageQry) {
        return MultiResponse.of(service.getList(pageQry));
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
    @PostMapping("/detail")
    public SingleResponse<ReportDTO> getReportDto(@RequestBody ReportPageQry pageQry) {
        return SingleResponse.of(service.getDetail(pageQry));
    }  
    
    /**
     * pdf详情
     */
    @GetMapping("/pdf")
    public SingleResponse<AppInfoDTO> getPdf(@NonNull Long reportId) {
        return SingleResponse.of(service.getPdf(reportId));
    }

    /**
     * 生成主报告（首次使用）
     */
    @PostMapping("/sync")
    public void sync() {
        task.execute();
    }

    /**
     * pdf链接
     */
    @GetMapping("/pdfUrl")
    public SingleResponse<AppAndReportDTO> getPdfUrl(@NonNull String appCode) throws Exception {
        return SingleResponse.of(service.getPdfUrl(appCode));
    }

}
