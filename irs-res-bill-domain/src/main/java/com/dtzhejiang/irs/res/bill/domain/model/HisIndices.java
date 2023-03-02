package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@TableName(value = "his_indices",autoResultMap = true)
public class HisIndices {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 每一类的子报告ID
     */
    private Long subReportId;

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