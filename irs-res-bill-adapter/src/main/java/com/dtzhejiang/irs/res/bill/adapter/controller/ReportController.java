package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import com.dtzhejiang.irs.res.bill.app.qry.ReportPageQry;
import com.dtzhejiang.irs.res.bill.app.service.ReportService;
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
     * 创建报告
     */
    @PostMapping("/create")
    public Response create(@RequestBody ReportPageQry cmd) {
        return Response.buildSuccess();
    }

    /**
     * 应用ID查询主报告列表
     * @param applicationId 应用ID
     */
    @GetMapping("/list")
    public MultiResponse<Report> aggRegister(String applicationId) {
        return MultiResponse.of(service.getList(applicationId));
    }


}
