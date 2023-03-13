package com.dtzhejiang.irs.res.bill.app.command.handler;

import com.dtzhejiang.irs.res.bill.app.command.cmd.StartProcessCmd;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessInstance;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ProcessCommandHandler {

    @Autowired
    private ProcessGateway processGateway;
    @Autowired
    private UserGateway userGateway;

    public ProcessInstance start(StartProcessCmd cmd){
        return processGateway.startProcess(cmd.getProcessKey(), cmd.getBusinessKey(), buildVariables());
    }

    public void complete(String taskId, Map<String, Object> variable){
        UserInfo user = userGateway.getCurrentUser();
        processGateway.completeProcessTask(taskId, variable, user.getUserName());
    }

    private Map<String, Object> buildVariables() {
        Map<String, Object> variables = new HashMap<>();
        Map<String, String> opInfo = new HashMap<>();
        opInfo.put("configOpId", "submit");
        opInfo.put("configOpName", "提交");
        opInfo.put("logOpName", "提交");
        variables.put("opInfo", opInfo);
        variables.put("status", "UN_VALID_CONFIRM");
        return variables;
    }
}
