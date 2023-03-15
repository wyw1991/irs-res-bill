package com.dtzhejiang.irs.res.bill.app.command.cmd;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubReportUpdateProcessInfoCmd {
    /**
     * 流程状态
     */
    private String status;
    /**
     * 流程编号
     */
    private String processInstanceId;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务分类
     */
    private String taskCategory;
    /**
     * 备注
     */
    private String remark;
    /**
     * 任务当前处理人id
     */
    private String assignee;
    /**
     * 任务当前处理角色编码
     */
    private String role;
}
