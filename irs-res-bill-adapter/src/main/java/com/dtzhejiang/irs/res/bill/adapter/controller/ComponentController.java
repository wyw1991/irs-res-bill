package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import com.dtzhejiang.irs.res.bill.qry.ReportPageQry;
import org.springframework.web.bind.annotation.*;

/**
 *主报告
 */
@RestController
@RequestMapping("/report")
public class ComponentController {

    /**
     * 分页查询
     */
    @PostMapping("/page")
    public PageResponse<Report> page(@RequestBody ReportPageQry pageQry) {
        return null;
    }
    /**
     * 创建报告
     */
    @PostMapping("/create")
    public Response create(@RequestBody ReportPageQry cmd) {
        return Response.buildSuccess();
    }


}
