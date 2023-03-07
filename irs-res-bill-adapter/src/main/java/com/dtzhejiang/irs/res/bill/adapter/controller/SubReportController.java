package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.SubReportDTO;
import com.dtzhejiang.irs.res.bill.app.service.SubReportService;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.qry.SubReportQry;
import lombok.NonNull;
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
     * @param qry
     */
    @GetMapping("/fail/list")
    public SingleResponse<SubReportFailDTO> failList(@RequestBody SubReportQry qry) {

        return SingleResponse.of(subReportService.failList(qry));
    }

    /**
     * 子报告列表
     */
    @PostMapping("/list")
    public SingleResponse<SubReportDTO> getList(@RequestBody SubReportQry qry) {
        return SingleResponse.of(subReportService.getSubReportDTO(qry));
    }

    /**
     * 提交
     */
    @GetMapping("/submit")
    public SingleResponse createSubReport(@NonNull Long reportId) {
        subReportService.submitSubReport(reportId);
        return SingleResponse.buildSuccess();
    }

}
