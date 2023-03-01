package com.dtzhejiang.irs.res.bill.domain.model;

import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;

import java.util.Date;

public class HisIndices {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 每一类的子报告ID
     */
    private String subReportId;

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
     * 运行结果状态：success 成功，fail 失败，question 有疑问
     */
    private OperationResultsStatusEnum operationResultsStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}