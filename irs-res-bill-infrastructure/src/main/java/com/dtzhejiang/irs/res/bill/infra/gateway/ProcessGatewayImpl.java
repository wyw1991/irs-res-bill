package com.dtzhejiang.irs.res.bill.infra.gateway;


import com.dtflys.forest.Forest;
import com.dtflys.forest.utils.TypeReference;
import com.dtzhejiang.irs.res.bill.common.util.JsonUtil;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessInstance;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.irs.res.bill.infra.config.WorkflowConfig;
import com.dtzhejiang.irs.res.bill.infra.gateway.req.FlowInstanceRequest;
import com.dtzhejiang.irs.res.bill.infra.gateway.resp.ProcessInstanceResp;
import com.dtzhejiang.irs.res.bill.infra.gateway.resp.WorkflowResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
    public ProcessInstance startProcess(String processKey, Long subReportId, Map<String, Object> variables) {
        FlowInstanceRequest instanceRequest = FlowInstanceRequest.builder()
                .processDefinitionKey(processKey)
                .businessKey(String.valueOf(subReportId))
                .variables(variables)
                .build();
        log.info("starProcess,request:{}", JsonUtil.toJsonString(instanceRequest));
        UserInfo currentUser = userGateway.getCurrentUser();
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
    public void completeProcessTask(String processTaskId, Map<String, Object> variables, String username) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("taskId", processTaskId);
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


    private String buildBasicCredentials(String username) {
        return "basic " + Base64Utils.encodeToString(
                (username + ":" + workflowConfig.getToken())
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

}
