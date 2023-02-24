package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.service.SubReportService;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.qry.SubReportQry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *子报告
 */
@RestController
@RequestMapping("/sub/report")
public class SubReportController {
    @Autowired
    private SubReportService subReportService;

    /**
     * 异常结果汇总
     * @param allSubIds 子报告IDs
     */
    @GetMapping("/fail/list")
    public SingleResponse<SubReportFailDTO> failList(String allSubIds) {

        return SingleResponse.of(subReportService.failList(allSubIds));
    }

    /**
     * 子报告列表
     */
    @PostMapping("/list")
    public MultiResponse<SubReport> getList(@RequestBody SubReportQry qry) {
        return MultiResponse.of(subReportService.getList(qry));
    }

}
