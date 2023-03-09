package com.dtzhejiang.irs.res.bill.app.service;


import com.dtzhejiang.irs.res.bill.app.command.cmd.CompleteReportCmd;
import com.dtzhejiang.irs.res.bill.app.command.cmd.CompleteSubReportCmd;
import com.dtzhejiang.irs.res.bill.app.command.handler.CompleteSubReportCmdHandler;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SubReportAuditService {
    @Autowired
    private CompleteSubReportCmdHandler completeSubReportCmdHandler;

    public Response completeSubReport(CompleteSubReportCmd cmd) {
        return completeSubReportCmdHandler.apply(cmd);
    }


    public Response completeReport(CompleteReportCmd cmd) {
        return completeSubReportCmdHandler.applyReport(cmd);
    }
}
