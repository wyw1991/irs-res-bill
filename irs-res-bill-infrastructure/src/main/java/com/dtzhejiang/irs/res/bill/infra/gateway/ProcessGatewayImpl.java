package com.dtzhejiang.irs.res.bill.infra.gateway;


import com.dtflys.forest.Forest;
import com.dtflys.forest.utils.TypeReference;
import com.dtzhejiang.irs.res.bill.common.util.JsonUtil;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessInstance;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessLog;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessTask;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.User;
import com.dtzhejiang.irs.res.bill.infra.config.WorkflowConfig;
import com.dtzhejiang.irs.res.bill.infra.gateway.req.FlowInstanceRequest;
import com.dtzhejiang.irs.res.bill.infra.gateway.resp.ProcessInstanceResp;
import com.dtzhejiang.irs.res.bill.infra.gateway.resp.TaskResp;
import com.dtzhejiang.irs.res.bill.infra.gateway.resp.WorkflowResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ProcessGatewayImpl implements ProcessGateway {

    public static final String START_PROCESS_API_URI = "/processInstance/start";

    public static final String COMPLETE_TASK_API_URI = "/task/complete";

    @Autowired
    private WorkflowConfig workflowConfig;
    @Autowired
    private UserGateway userGateway;


    @Override
    public ProcessInstance startProcess(String processKey, String businessKey, Map<String, Object> variables) {
        FlowInstanceRequest instanceRequest = FlowInstanceRequest.builder()
                .processDefinitionKey(processKey)
                .businessKey(businessKey)
                .variables(variables)
                .build();
        log.info("starProcess,request:{}", JsonUtil.toJsonString(instanceRequest));
        User currentUser = userGateway.getCurrentUser();
        WorkflowResp<ProcessInstanceResp> flowInstanceResponse = Forest.post(workflowConfig.getApiUrl() + START_PROCESS_API_URI)
                .addHeader("Authorization", buildBasicCredentials(currentUser.getUserName()))
                .addBody(instanceRequest).contentTypeJson()
                .execute(new TypeReference<WorkflowResp<ProcessInstanceResp>>() {
                });
        log.info("startProcess,response:{}", JsonUtil.toJsonString(flowInstanceResponse));
        if (!flowInstanceResponse.getSuccess()) {
            throw new BusinessException("流程启动失败", flowInstanceResponse.getMsg());
        }
        ProcessInstanceResp processInstance = flowInstanceResponse.getData();
        return ProcessInstance.builder()
                .processId(processInstance.getId())
                .status((String) variables.get("status"))
                .build();
    }

    @Override
    public void completeProcessTask(String taskId, Map<String, Object> variables, String username) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("taskId", taskId);
        requestBody.put("variables", variables);
        log.info("completeProcessTask,request:{}", JsonUtil.toJsonString(requestBody));
        WorkflowResp<?> completeTaskResp = Forest.post(workflowConfig.getApiUrl() + COMPLETE_TASK_API_URI)
                .addHeader("Authorization", buildBasicCredentials(username))
                .addBody(requestBody).contentTypeJson()
                .execute(WorkflowResp.class);
        log.info("completeProcessTask,response:{}", JsonUtil.toJsonString(completeTaskResp));
        if (!completeTaskResp.getSuccess()) {
            throw new BusinessException("任务处理失败", completeTaskResp.getMsg());
        }
    }

    @Override
    public ProcessTask getCurrentProcessTask(String processId, String username) {
        log.info("getCurrentTask,processId:{}",processId);
        WorkflowResp<TaskResp> currentTaskResp = Forest.get(workflowConfig.getApiUrl() + "/task/current")
                .addHeader("Authorization", buildBasicCredentials(username))
                .addQuery("processInstanceId",processId)
                .execute(new TypeReference<WorkflowResp<TaskResp>>() {});
        log.info("getCurrentTask,response:{}",JsonUtil.toJsonString(currentTaskResp));
        TaskResp taskResp = currentTaskResp.getData();
        if(taskResp == null) {
            return null;
        }
        return ProcessTask.builder()
                .id(taskResp.getId())
                .processId(taskResp.getProcessInstanceId())
                .name(taskResp.getName())
                .category(taskResp.getCategory())
                .currentHandler(taskResp.getAssignee())
                .currentHandlerName(taskResp.getAssigneeName())
                .currentGroups(taskResp.getGroups())
                .currentGroupNames(taskResp.getGroupNames())
                .build();
    }

    @Override
    public Operation getCurrentOperation(String processId, String username) {
        log.info("getCurrentOperation,processId:{},username:{}",processId,username);
        WorkflowResp<Operation> getCurrentOperationResp = Forest.get(workflowConfig.getApiUrl() + "/processInstance/currentOperation")
                .addHeader("Authorization", buildBasicCredentials(username))
                .addQuery("processInstanceId",processId)
                .execute(new TypeReference<WorkflowResp<Operation>>() {});
        log.info("getCurrentOperation,response:{}",JsonUtil.toJsonString(getCurrentOperationResp));
        return getCurrentOperationResp.getData();
    }

    @Override
    public List<ProcessLog> listProcessLogs(String processId, String username) {
        log.info("listProcessLogs,processId:{},username:{}",processId,username);
        WorkflowResp<List<ProcessLog>> resp = Forest.get(workflowConfig.getApiUrl() + "/processInstance/logs")
                .addHeader("Authorization", buildBasicCredentials(username))
                .addQuery("processInstanceId",processId)
                .execute(new TypeReference<WorkflowResp<List<ProcessLog>>>() {
                });
        log.info("getCurrentOperation,response:{}",JsonUtil.toJsonString(resp));
        if(!resp.getSuccess()) {
            throw new BusinessException(resp.getCode(),resp.getMsg());
        }
        return resp.getData();
    }


    private String buildBasicCredentials(String username) {
        return "basic " + Base64Utils.encodeToString(
                (username + ":" + workflowConfig.getToken())
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

}
