package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "quota_version",autoResultMap = true)
public class QuotaVersion {
    /**
     * ID
     */
    private Long id;

    /**
     * 指标id
     */
    private Long quotaId;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 版本说明
     */
    private String versionDesc;

    /**
     * 是否发送浙政钉通知，0：未通知，1：已通知
     */
    private Byte notifyFlag;

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