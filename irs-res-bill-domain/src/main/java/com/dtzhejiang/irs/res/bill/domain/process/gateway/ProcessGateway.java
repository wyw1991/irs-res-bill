package com.dtzhejiang.irs.res.bill.domain.process.gateway;

import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessInstance;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessLog;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessTask;

import java.util.List;
import java.util.Map;

public interface ProcessGateway {

    /**
     * 启动流程
     * @param processKey 流程key
     * @param businessKey 应用编码
     * @param variables 流程所需变量
     */
    ProcessInstance startProcess(String processKey, String businessKey, Map<String, Object> variables);

    /**
     * 完成流程任务
     * @param taskId 流程任务id
     * @param username 用户名
     * @param variables 流程所需变量
     */
    void completeProcessTask(String taskId, Map<String, Object> variables, String username);

    /**
     * 查看当前流程任务
     * @param processId 流程id
     * @param username 用户名
     */
    ProcessTask getCurrentProcessTask(String processId, String username);

    /**
     * 查看当前操作
     * @param processId 流程id
     * @param username 用户名
     */
    Operation getCurrentOperation(String processId, String username);

    /**
     * 查流程日志
     * @param processId 流程id
     * @param username 用户名
     */
    List<ProcessLog> listProcessLogs(String processId, String username);
}
