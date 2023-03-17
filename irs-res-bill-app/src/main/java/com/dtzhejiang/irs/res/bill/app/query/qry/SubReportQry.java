package com.dtzhejiang.irs.res.bill.app.query.qry;


import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import com.dtzhejiang.irs.res.bill.common.enums.BillPermissionEnum;
import lombok.Data;
import lombok.NonNull;

@Data
public class SubReportQry extends PageQuery {

    /**
     * 子报告类型： BUSINESS_APPLICATION-业务应用,NETWORK_SECURITY-网络安全,OPERATION-运行情况,APPLICATION_SUPPORT-应用支撑,DATA_RESOURCES-数据资源,BASIC_FACILITIES-基层设施
     */
    private String subType;


    /**
     * 主报告ID
     */
    private Long reportId;


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
