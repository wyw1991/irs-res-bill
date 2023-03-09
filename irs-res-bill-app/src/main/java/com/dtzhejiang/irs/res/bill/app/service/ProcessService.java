package com.dtzhejiang.irs.res.bill.app.service;


import com.dtzhejiang.irs.res.bill.app.command.handler.CompleteSubReportCmdHandler;
import com.dtzhejiang.irs.res.bill.app.dto.OperateLogDTO;
import com.dtzhejiang.irs.res.bill.app.dto.ProcessNodeDTO;
import com.dtzhejiang.irs.res.bill.app.query.handler.CurrentProcessNodeQryHandler;
import com.dtzhejiang.irs.res.bill.app.query.handler.ProcessLogsQueryHandler;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProcessService {

    @Autowired
    private ProcessLogsQueryHandler processLogsQueryHandler;

    @Autowired
    private CurrentProcessNodeQryHandler currentProcessNodeQryHandler;

    @Autowired
    private CompleteSubReportCmdHandler completeProcessTaskCmdHandler;


    public MultiResponse<OperateLogDTO> listProcessLogs(String processId) {
        return processLogsQueryHandler.apply(processId);
    }

    public SingleResponse<ProcessNodeDTO> getCurrentProcessNode(String processId) {
        return currentProcessNodeQryHandler.apply(processId);
    }
}
