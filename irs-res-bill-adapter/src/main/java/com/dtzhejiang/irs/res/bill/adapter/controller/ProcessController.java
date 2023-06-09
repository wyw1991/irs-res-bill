package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.ProcessNodeDTO;
import com.dtzhejiang.irs.res.bill.app.dto.ProcessTaskDTO;
import com.dtzhejiang.irs.res.bill.app.service.ProcessService;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程管理
 */
@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    /**
     * 查询审核操作记录
     */
    @GetMapping("/Logs")
    public MultiResponse<ProcessLog> listProcessLogsByProcessId(String processId) {
        return processService.listProcessLogs(processId);
    }

    /**
     * 查询当前流程任务
     */
    @GetMapping("/currentTask")
    public SingleResponse<ProcessTaskDTO> getCurrentTask(String processId) {
        return processService.getCurrentTask(processId);
    }

    /**
     * 查询当前流程信息(节点、操作项)
     */
    @GetMapping("/currentNode")
    public SingleResponse<ProcessNodeDTO> getCurrentOperation(String processId) {
        return processService.getCurrentProcessNode(processId);
    }
}
