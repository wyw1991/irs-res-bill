package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dtzhejiang.irs.res.bill.common.enums.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@TableName(value = "report",autoResultMap = true)
public class Report {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用ID
     */
    private String applicationId;

    /**
     * 应用状态：TEST_RUN-试运行，RUN-运行中
     */
    private ApplicationStatusEnum applicationStatus;

    /**
     * 应用类型：OFFICE-办公类、BUSINESS_APPLICATION-业务应用类、PORTAL_SITE-门户网站、WEIBO_OR_WECHAT_ACCOUNT-宣传微博\微信公众号、HARDWARE-硬件类、TOOL-工具类、OTHER-其他；
     */
    private TypeEnum type;

    /**
     * 应用领域：TOTAL_GOVERNMENT-政机关整体智治、GOVERNMENT-数字政府、ECONOMY-数字经济、SOCIETY-数字社会、LAW-数字法治、CULTURE-数字文化；
     */
    private String field;

    /**
     * 建设层级
     */
    private String level;
    /**
     * 应用管理员
     */
    private String appAdmin;
    /**
     * 应用管理员id
     */
    private String appAdminId;

    /**
     * 报告状态：UN_INIT-待生成,INIT-待提交,PROCESS-处理中，SUCCESS-已出具，FAIL-不通过
     */
    private StatusEnum status;


    /**
     * 是否最新： 0 否，1 是 默认是
     */
    private boolean newReport = true;
    /**
     * 版本号
     */
    private String version;

    /**
     * 出具时间
     */
    private Date finishTime;

    /**
     * 是否关联项目：true:是、false:否 默认是；
     */
    private Boolean linkProject;

    /**
     * 问题数
     */
    @TableField(exist = false)
    private Long failNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * oss地址
     */
    @TableField(exist = false)
    private String reportOss;
    /**
     * oss fileId
     */
    private String fileIds;
    /**
     * 子报告权限列表
     */
    @TableField(exist = false)
    private List<SubTypeEnum> typeList;

    public Report() {
    }

    public Report(Long id, String fileIds) {
        this.id = id;
        this.fileIds = fileIds;
    }
}