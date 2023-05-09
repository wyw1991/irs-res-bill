package com.dtzhejiang.irs.res.bill.app.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class QuotaDto {
    /**
     * ID
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 指标名称
     */
    private String name;

    /**
     * 数据目录code
     */
    private String dataCatalogCode;

    /**
     * 数据目录中文名
     */
    private String dataCatalogName;

    /**
     * 数据目录是否可编辑
     */
    private Boolean dataCatalogEditFlag;

    /**
     * 需求id
     */
    private Long demandId;

    /**
     * 需求名称
     */
    private String demandName;

    /**
     * 业务分类，1：应用、2：数据、3：组件、4：云资源、5：其它 
     */
    private Byte businessCode;

    /**
     * 更新频率，1：分钟级、2：小时级、3：每日、4：每月、5：每周、6：每季度、7：每年、8：不定期
     */
    private Byte updateFrequency;

    /**
     * 安全级别，1：1级（不敏感）、2：2级（低敏感）、3：3级（较敏感）、4：4级（敏感）  
     */
    private Byte securityLevel;

    /**
     * 指标描述
     */
    private String quotaDesc;

    /**
     * 计算逻辑
     */
    private String calLogic;

    /**
     * 统计维度
     */
    private String aggDimension;

    /**
     * 指标联系人
     */
    private String quotaContacts;

    /**
     * 指标联系人联系方式
     */
    private String quotaContactsInfo;

}