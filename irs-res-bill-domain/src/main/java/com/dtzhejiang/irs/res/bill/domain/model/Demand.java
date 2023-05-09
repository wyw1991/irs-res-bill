package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dtzhejiang.irs.res.bill.common.enums.DemandStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.DemandTypeEnum;
import com.dtzhejiang.irs.res.bill.common.enums.QuotaStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@TableName(value = "demand",autoResultMap = true)
public class Demand {
    /**
     * 自增主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用类型： APP-应用、DATA-数据、COMPONENT-组件、CLOUD-云资源、OTHER-其它  
     */
    private DemandTypeEnum type;

    /**
     * 频率：分钟级、小时级、每日、每月、每周、每季度、每年、不定期
     */
    private String frequency;

    /**
     * 描述
     */
    private String description;

    /**
     * 计算逻辑
     */
    private String calculateLogic;

    /**
     * 统计维度
     */
    private String statisticalDimension;

    /**
     * 需求联系人
     */
    private String contacts;
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 需求所属区域
     */
    private String regionCode;

    /**
     * 需求提出单位
     */
    private String orgCode;

    /**
     * 需求提出单位名称
     */
    private String orgName;

    /**
     * 指标Id
     */
    private Long quotaId;
    /**
     * 指标状态 PROCESS-待报告出具,ON_LINE-已上线,OFF_LINE-已下线
     */
    private QuotaStatusEnum quotaStatus;

    /**
     * 审批ID
     */
    private String processId;

    /**
     * 流程是否结束0-未结束，1-已结束
     */
    private Boolean processEnd;

    /**
     * 当前任务节点id
     */
    private String taskId;

    /**
     * 当前任务节点名称
     */
    private String taskName;

    /**
     * 当前处理人(<permisson>_userId,<permisson>_userId)
     */
    private String currentHandler;

    /**
     * 当前处理角色(<permisson>_roleId,<permisson>_roleId)
     */
    private String currentRole;

    /**
     * 历史处理人(<permisson>_userId,<permisson>_userId)
     */
    private String historyHandler;

    /**
     * 报告状态：UN_SUBMIT待提交，UN_AUDIT-待审批，AUDIT审批中，FAIL-已拒绝，SUCCESS-已通过
     */
    private DemandStatusEnum status;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;


}