package com.dtzhejiang.irs.res.bill.app.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProcessTaskDTO {

    /**
     * 流程id
     */
    private String processId;
    /**
     * 流程任务id
     */
    private String processTaskId;
    /**
     * 流程任务名称
     */
    private String processTaskName;
    /**
     * 流程任务分类
     */
    private String processTaskCategory;
    /**
     * 流程任务分配人
     */
    private String processTaskAssignee;
    /**
     * 流程任务分配角色
     */
    private List<String> processTaskGroups;
}
