package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.command.cmd.SubReportUpdateProcessInfoCmd;
import com.dtzhejiang.irs.res.bill.app.command.handler.SubReportCommandHandler;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import com.dtzhejiang.irs.res.bill.common.enums.FlowableEventType;
import com.dtzhejiang.irs.res.bill.common.util.JsonUtil;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 外部事件监听器
 */
@Slf4j
@RestController
@RequestMapping("/events")
public class ExternalEventsController {
    @Autowired
    private SubReportCommandHandler subReportCommandHandler;

    /**
     * 流程引擎事件分发
     */
    @PostMapping("/workflow")
    public Response dispatchWorkflowEvent(@RequestBody Map<String, Object> eventBody) {
        String eventType = (String) eventBody.get("eventType");
        if (ObjectUtils.isEmpty(eventType)) {
            return Response.buildSuccess();
        }
        log.info("receive event:{},body:{}", eventType, JsonUtil.toJsonString(eventBody));
        String processInstanceId = (String) eventBody.get("processInstanceId");
        if (FlowableEventType.TASK_CREATED.name().equals(eventType)) {
            List<String> taskGroup = Optional.ofNullable((List<String>) eventBody.get("taskGroup")).orElse(new ArrayList<>());
            subReportCommandHandler.updateAssignInfo(SubReportUpdateProcessInfoCmd.builder()
                    .processInstanceId(processInstanceId)
                    .status(eventBody.get("status").toString())
                    .taskId(eventBody.get("taskId").toString())
                    .taskName(eventBody.get("taskName").toString())
                    .assignee(eventBody.get("taskAssignee").toString())
                    .role(String.join(",", taskGroup))
                    .build());
        }
        if (FlowableEventType.PROCESS_COMPLETED.name().equals(eventType)) {
            subReportCommandHandler.updateProcessEnd(SubReportUpdateProcessInfoCmd.builder()
                    .processInstanceId(processInstanceId)
                    .build());
        }
        return Response.buildSuccess();
    }
}
