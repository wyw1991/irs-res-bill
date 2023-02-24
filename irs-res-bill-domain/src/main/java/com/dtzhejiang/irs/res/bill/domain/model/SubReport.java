package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
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
    private Long id;
    /**
     * 每一类的子报告ID(每次提交审批生成)
     */
    private String subReportId;
    /**
     * 子报告名称
     */
    private String name;

    /**
     * 子报告类型： BUSINESS_APPLICATION-业务应用,NETWORK_SECURITY-网络安全,OPERATION-运行情况,APPLICATION_SUPPORT-应用支撑,DATA_RESOURCES-数据资源,BASIC_FACILITIES-基层设施
     */
    private SubTypeEnum subType;

    /**
     * 运行指标
     */
    private String operationIndices;

    /**
     * 运行数据
     */
    private String operationData;

    /**
     * 正常值
     */
    private String normalValue;

    /**
     * 运行结果描述
     */
    private String operationResults;

    /**
     * 运行结果状态：SUCCESS 成功，FAIL 失败，QUESTION 有疑问
     */
    private OperationResultsStatusEnum operationResultsStatus;

    /**
     * 子报告状态：UN_SUBMIT-待提交，UN_COMMIT-待确认，UN_AUDIT-待审核，APPROVED-已审核
     */
    private SubStatusEnum subStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审批ID
     */
    private String approvalId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}