package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "data_catalog_apply",autoResultMap = true)
public class DataCatalogApply {
    /**
     * ID
     */
    private Long id;

    /**
     * 申请编码
     */
    private String code;

    /**
     * 数据目录编码
     */
    private String dataCatalogCode;

    /**
     * 申请用户编码
     */
    private String applyUserCode;

    /**
     * 申请用户名称
     */
    private String applyUserName;

    /**
     * 申请部门code
     */
    private String applyOrgCode;

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
     * 状态
     */
    private String status;

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

    public String getDataCatalogCode() {
        return dataCatalogCode;
    }

    public void setDataCatalogCode(String dataCatalogCode) {
        this.dataCatalogCode = dataCatalogCode == null ? null : dataCatalogCode.trim();
    }

    public String getApplyUserCode() {
        return applyUserCode;
    }

    public void setApplyUserCode(String applyUserCode) {
        this.applyUserCode = applyUserCode == null ? null : applyUserCode.trim();
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName == null ? null : applyUserName.trim();
    }

    public String getApplyOrgCode() {
        return applyOrgCode;
    }

    public void setApplyOrgCode(String applyOrgCode) {
        this.applyOrgCode = applyOrgCode == null ? null : applyOrgCode.trim();
    }

    public String getApplyOrgName() {
        return applyOrgName;
    }

    public void setApplyOrgName(String applyOrgName) {
        this.applyOrgName = applyOrgName == null ? null : applyOrgName.trim();
    }

    public String getApplyContactsInfo() {
        return applyContactsInfo;
    }

    public void setApplyContactsInfo(String applyContactsInfo) {
        this.applyContactsInfo = applyContactsInfo == null ? null : applyContactsInfo.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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