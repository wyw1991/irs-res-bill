package com.dtzhejiang.irs.res.bill.infra.gateway.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TaskResp implements Serializable {

    private static final long serialVersionUID = 8438485615251161093L;
    /**
     * 任务Id
     */
    private String id;
    /**
     * 任务所有人
     */
    private String owner;
    /**
     * 任务分配人
     */
    private String assignee;
    /**
     * 任务分配人姓名
     */
    private String assigneeName;
    /**
     * 任务角色
     */
    private List<String> groups;
    /**
     * 任务角色名称
     */
    private List<String> groupNames;
    /**
     * 委托状态
     */
    private String delegationState;
    /**
     * 任务名
     */
    private String name;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务创建时间
     */
    private Date createTime;
    /**
     * 任务截止时间
     */
    private Date dueDate;
    /**
     * 任务优先级
     */
    private int priority;
    /**
     * 是否暂停
     */
    private boolean suspended;
    /**
     * 任务认领时间
     */
    private Date claimTime;
    /**
     * 任务定义key
     */
    private String taskDefinitionKey;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 分类
     */
    private String category;
    /**
     * 表单key
     */
    private String formKey;
    /**
     * 父任务id
     */
    private String parentTaskId;
    /**
     * 执行id
     */
    private String executionId;
    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 流程定义id
     */
    private String processDefinitionId;
}
