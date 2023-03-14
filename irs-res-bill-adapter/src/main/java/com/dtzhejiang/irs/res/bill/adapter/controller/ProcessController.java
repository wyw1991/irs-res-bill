package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.OperateLogDTO;
import com.dtzhejiang.irs.res.bill.app.dto.ProcessNodeDTO;
import com.dtzhejiang.irs.res.bill.app.service.ProcessService;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
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
    public MultiResponse<OperateLogDTO> listProcessLogsByProcessId(String processId) {
        return processService.listProcessLogs(processId);
    }

    /**
     * 查询当前流程信息(节点、操作项)
     */
    @GetMapping("/currentNode")
    public SingleResponse<ProcessNodeDTO> getCurrentOperation(String processId) {
        return processService.getCurrentProcessNode(processId);
    }
}
