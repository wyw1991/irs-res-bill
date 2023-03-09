package com.dtzhejiang.irs.res.bill.app.qry;


import lombok.Data;
import lombok.NonNull;

@Data
public class SubReportQry {

    /**
     * 子报告类型： BUSINESS_APPLICATION-业务应用,NETWORK_SECURITY-网络安全,OPERATION-运行情况,APPLICATION_SUPPORT-应用支撑,DATA_RESOURCES-数据资源,BASIC_FACILITIES-基层设施
     */
    private String subType;


    /**
     * 主报告ID
     */
    private Long reportId;

    /**
     * 当前查询列表
     */
    private String billPermission;

    /**
     * 是否已审核列表：true:是、false:否；默认否
     */
    private Boolean myAudit=false;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 是否过滤权限 默认是
     */
    private Boolean permission=true;


    public SubReportQry(Long reportId) {
        this.reportId = reportId;
    }

    public SubReportQry() {
    }
}
