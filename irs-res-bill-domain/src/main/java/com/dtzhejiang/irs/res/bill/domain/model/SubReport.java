package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
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
     * 子报告名称
     */
    private String name;

    /**
     * 子报告类型
     */
    private Long subType;

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
     * 运行结果状态：success 成功，fail 失败
     */
    private String operationResultsStatus;

    /**
     * 子报告状态：待提交，待确认，待审核，已审核
     */
    private String subStatus;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSubType() {
        return subType;
    }

    public void setSubType(Long subType) {
        this.subType = subType;
    }

    public String getOperationIndices() {
        return operationIndices;
    }

    public void setOperationIndices(String operationIndices) {
        this.operationIndices = operationIndices == null ? null : operationIndices.trim();
    }

    public String getOperationData() {
        return operationData;
    }

    public void setOperationData(String operationData) {
        this.operationData = operationData == null ? null : operationData.trim();
    }

    public String getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(String normalValue) {
        this.normalValue = normalValue == null ? null : normalValue.trim();
    }

    public String getOperationResults() {
        return operationResults;
    }

    public void setOperationResults(String operationResults) {
        this.operationResults = operationResults == null ? null : operationResults.trim();
    }

    public String getOperationResultsStatus() {
        return operationResultsStatus;
    }

    public void setOperationResultsStatus(String operationResultsStatus) {
        this.operationResultsStatus = operationResultsStatus == null ? null : operationResultsStatus.trim();
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus == null ? null : subStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId == null ? null : approvalId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}