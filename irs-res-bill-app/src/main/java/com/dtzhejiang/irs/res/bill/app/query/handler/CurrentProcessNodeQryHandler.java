package com.dtzhejiang.irs.res.bill.app.query.handler;

import com.dtzhejiang.irs.res.bill.app.dto.OperationDTO;
import com.dtzhejiang.irs.res.bill.app.dto.ProcessNodeDTO;
import com.dtzhejiang.irs.res.bill.app.dto.ProcessTaskDTO;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessTask;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public class CurrentProcessNodeQryHandler implements Function<String, SingleResponse<ProcessNodeDTO>> {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private ProcessGateway processGateway;

    @Override
    public SingleResponse<ProcessNodeDTO> apply(String processId) {
        UserInfo currentUser = userGateway.getCurrentUser();
        ProcessTask currentProcessTask = processGateway.getCurrentProcessTask(processId, currentUser.getUserName());
        ProcessTaskDTO taskDTO = ProcessTaskDTO.builder()
                .processId(currentProcessTask.getProcessId())
                .processTaskId(currentProcessTask.getId())
                .processTaskName(currentProcessTask.getName())
                .processTaskAssignee(currentProcessTask.getCurrentHandlerName())
                .processTaskGroups(currentProcessTask.getCurrentGroupNames())
                .processTaskCategory(currentProcessTask.getCategory())
                .build();
        Operation operation = processGateway.getCurrentOperation(processId, currentUser.getUserName());
        OperationDTO operationDTO = new OperationDTO();
        BeanUtils.copyProperties(operation, operationDTO);
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessTask(taskDTO);
        processNodeDTO.setOperation(operationDTO);
        processNodeDTO.setAssigneeDisplayName(taskDTO.getProcessTaskAssignee());
        return SingleResponse.of(processNodeDTO);
    }

}
