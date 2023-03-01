package com.dtzhejiang.irs.res.bill.domain.process.gateway;

import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessInstance;

import java.util.Map;

public interface ProcessGateway {

    ProcessInstance startProcess(String processKey, Long subReportId, Map<String, Object> variables);

    void completeProcessTask(String processTaskId, Map<String, Object> variables, String username);
}
