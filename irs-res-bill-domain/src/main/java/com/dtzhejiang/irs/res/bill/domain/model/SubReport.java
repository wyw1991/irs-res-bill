package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@TableName(value = "sub_report",autoResultMap = true)
public class SubReport {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 主报告ID
     */
    private Long reportId;

    /**
     * 子报告名称
     */
    private String name;

    /**
     * 子报告类型: BUSINESS_APPLICATION-业务应用,NETWORK_SECURITY-网络安全,OPERATION-运行情况,APPLICATION_SUPPORT-应用支撑,DATA_RESOURCES-数据资源,BASIC_FACILITIES-基层设施
     */
    private SubTypeEnum subType;

    /**
     * 审批人IDS
     */
    private String auditIds;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审批ID
     */
    private String approvalId;

    /**
     * 当前任务节点id
     */
    private String taskId;

    /**
     * 当前任务节点名称
     */
    private String taskName;

    /**
     * 当前处理人
     */
    private String currentHandler;

    /**
     * 当前处理角色
     */
    private String currentRole;

    /**
     * 历史处理人
     */
    private String historyHandler;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}