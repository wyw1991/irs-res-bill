package com.dtzhejiang.irs.res.bill.app.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class QuotaApplyDto {
    /**
     * 申请编码
     */
    private String code;

    /**
     * 数据目录编码
     */
    private String dataCatalogCode;

    /**
     * 申请用户名称
     */
    private String applyUserName;

    /**
     * 申请部门名称
     */
    private String applyOrgName;

    /**
     * 申请人联系方式
     */
    private String applyContactsInfo;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 状态，0 审批通过 1 审批驳回 2 审批中
     */
    private String status;

}