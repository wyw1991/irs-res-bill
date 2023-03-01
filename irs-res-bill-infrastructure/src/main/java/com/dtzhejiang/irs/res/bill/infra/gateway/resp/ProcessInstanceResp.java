package com.dtzhejiang.irs.res.bill.infra.gateway.resp;

import lombok.Data;

import java.util.Map;

/**
 * ProcessInstanceResp
 * 描述：流程实例返回实体
 * @Company: 数字浙江
 * @Author: zhangming
 * @Date 2021/6/16 17:50
 */
@Data
public class ProcessInstanceResp {
    /**
     * 流程实例id
     */
    private String id;
    /**
     * 流程实例名称
     */
    private String name;
    /**
     * 业务key
     */
    private String businessKey;
    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 流程定义名称
     */
    private String processDefinitionName;
    /**
     * 当前活动id
     */
    private String activityId;
    /**
     * 流程发起人用户名
     */
    private String startUserId;
    /**
     * 流程发起人真实姓名
     */
    private String startDisplayName;
    /**
     * 流程当前处理人真实姓名
     */
    private String currentDisplayName;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 表单数据
     */
    private Map<String, Object> formData;

    /**
     * 当前任务Id
     */
    private String taskId;
    /**
     * 当前节点名称
     */
    private String taskName;
}
