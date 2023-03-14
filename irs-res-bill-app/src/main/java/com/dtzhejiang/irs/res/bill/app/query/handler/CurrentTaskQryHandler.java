package com.dtzhejiang.irs.res.bill.app.query.handler;

import com.dtzhejiang.irs.res.bill.app.dto.ProcessTaskDTO;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessTask;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CurrentTaskQryHandler implements Function<String, SingleResponse<ProcessTaskDTO>> {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private ProcessGateway processGateway;

    @Override
    public SingleResponse<ProcessTaskDTO> apply(String processId) {
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
        return SingleResponse.of(taskDTO);
    }

}
