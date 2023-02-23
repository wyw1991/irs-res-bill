package com.dtzhejiang.irs.res.bill.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@TableName(value = "report",autoResultMap = true)
public class Report {
    /**
     * 自增主键
     */
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
     * 应用状态：试运行，运行中
     */
    private String applicationStatus;

    /**
     * 应用类型：办公类、业务应用类、门户网站、宣传微博\微信公众号、硬件类、工具类、其他；
     */
    private String type;

    /**
     * 应用领域：政机关整体智治、数字政府、数字经济、数字社会、数字法治、数字文化；
 
     */
    private String field;

    /**
     * 建设层级：省级、县（市、区）、村（社区）；
     */
    private String level;

    /**
     * 报告状态：处理中，已出具，不通过
     */
    private String subReportStatus;

    /**
     * 审核通过子报告IDS
     */
    private String successSubIds;

    /**
     * 子报告IDS
     */
    private String allSubIds;

    /**
     * 版本号
     */
    private String version;

    /**
     * 出具时间
     */
    private Date finishTime;

    /**
     * 是否关联项目：是、否；
     */
    private String linkProject;

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

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId == null ? null : applicationId.trim();
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus == null ? null : applicationStatus.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getSubReportStatus() {
        return subReportStatus;
    }

    public void setSubReportStatus(String subReportStatus) {
        this.subReportStatus = subReportStatus == null ? null : subReportStatus.trim();
    }

    public String getSuccessSubIds() {
        return successSubIds;
    }

    public void setSuccessSubIds(String successSubIds) {
        this.successSubIds = successSubIds == null ? null : successSubIds.trim();
    }

    public String getAllSubIds() {
        return allSubIds;
    }

    public void setAllSubIds(String allSubIds) {
        this.allSubIds = allSubIds == null ? null : allSubIds.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getLinkProject() {
        return linkProject;
    }

    public void setLinkProject(String linkProject) {
        this.linkProject = linkProject == null ? null : linkProject.trim();
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