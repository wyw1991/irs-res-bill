package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.command.cmd.CompleteReportCmd;
import com.dtzhejiang.irs.res.bill.app.command.cmd.CompleteSubReportCmd;
import com.dtzhejiang.irs.res.bill.app.command.cmd.FinalCompleteReportCmd;
import com.dtzhejiang.irs.res.bill.app.service.SubReportAuditService;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *子报告审核
 */
@RestController
@RequestMapping("/sub/report/audit")
public class SubReportAuditController {

    @Autowired
    private SubReportAuditService subReportAuditService;

    /**
     * 操作子报告(通过/驳回)
     */
    @PostMapping("/complete/subReport")
    public Response completeSubReport(@RequestBody CompleteSubReportCmd cmd){
        return subReportAuditService.completeSubReport(cmd);
    }

    /**
     * 操作主报告(通过/驳回)
     */
    @PostMapping("/complete/report")
    public Response completeReport(@RequestBody CompleteReportCmd cmd){
        return subReportAuditService.completeReport(cmd);
    }

    /**
     * 业务终审全部通过/部分驳回
     */
    @PostMapping("/final/complete")
    public Response finalComplete(@RequestBody FinalCompleteReportCmd cmd) {
        return subReportAuditService.finalComplete(cmd);
    }

}
