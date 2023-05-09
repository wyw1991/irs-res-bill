package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "quota",autoResultMap = true)
public class Quota {
    /**
     * ID
     */
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
     * 需求id
     */
    private Long demandId;

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

    /**
     * 当前版本
     */
    private Integer version;

    /**
     * 指标创建时间
     */
    private Date quotaCreateTime;

    /**
     * 创建人浙政钉code
     */
    private String createUserCode;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建人部门code
     */
    private String createOrgCode;

    /**
     * 创建人部门名称
     */
    private String createOrgName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}