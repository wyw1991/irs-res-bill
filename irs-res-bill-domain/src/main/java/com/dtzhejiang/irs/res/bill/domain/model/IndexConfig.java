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
@TableName(value = "index_config",autoResultMap = true)
public class IndexConfig {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 子报告类型: BUSINESS_APPLICATION-业务应用,NETWORK_SECURITY-网络安全,OPERATION-运行情况,APPLICATION_SUPPORT-应用支撑,DATA_RESOURCES-数据资源,BASIC_FACILITIES-基层设施
     */
    private SubTypeEnum type;

    /**
     * 指标名称
     */
    private String indexName;

    /**
     * 指标编码
     */
    private String indexCode;

    /**
     * 指标单位
     */
    private String indexUnit;

    /**
     * 指标来源
     */
    private String indexSource;

    /**
     * 校验规则
     */
    private String checkRule;

    /**
     * 正常区间
     */
    private String normalValue;

    /**
     * 异常提示
     */
    private String errorTips;

    /**
     * 预警提示
     */
    private String warnTips;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志，0 未删除，1 已删除，默认为0
     */
    private Byte deleteFlag;


}