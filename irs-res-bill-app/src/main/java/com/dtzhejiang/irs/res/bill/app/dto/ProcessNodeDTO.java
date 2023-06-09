package com.dtzhejiang.irs.res.bill.app.dto;

import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import lombok.Data;

@Data
public class ProcessNodeDTO {
    /**
     * 流程任务
     */
    private ProcessTaskDTO processTask;
    /**
     * 流程任务分配人展示名
     */
    private String assigneeDisplayName;
    /**
     * 操作配置
     */
    private Operation operation;
}
