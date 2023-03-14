package com.dtzhejiang.irs.res.bill.app.service;


import com.dtzhejiang.irs.res.bill.app.dto.ProcessNodeDTO;
import com.dtzhejiang.irs.res.bill.app.dto.ProcessTaskDTO;
import com.dtzhejiang.irs.res.bill.app.query.handler.CurrentProcessNodeQryHandler;
import com.dtzhejiang.irs.res.bill.app.query.handler.CurrentTaskQryHandler;
import com.dtzhejiang.irs.res.bill.app.query.handler.ProcessLogsQueryHandler;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProcessService {

    @Autowired
    private ProcessLogsQueryHandler processLogsQueryHandler;

    @Autowired
    private CurrentProcessNodeQryHandler currentProcessNodeQryHandler;

    @Autowired
    private CurrentTaskQryHandler currentTaskQryHandler;

    public MultiResponse<ProcessLog> listProcessLogs(String processId) {
        return processLogsQueryHandler.apply(processId);
    }

    public SingleResponse<ProcessNodeDTO> getCurrentProcessNode(String processId) {
        return currentProcessNodeQryHandler.apply(processId);
    }

    public SingleResponse<Operation> getCurrentOperation(String processId) {
        return currentProcessNodeQryHandler.getCurrentOption(processId);
    }

    public SingleResponse<ProcessTaskDTO> getCurrentTask(String processId) {
        return currentTaskQryHandler.apply(processId);
    }
}
