package com.dtzhejiang.irs.res.bill.app.qry;


import com.dtzhejiang.irs.res.bill.common.enums.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubReportQry {

    /**
     * 主报告ID
     */
    private Long id;
    /**
     * 子报告类型： BUSINESS_APPLICATION-业务应用,NETWORK_SECURITY-网络安全,OPERATION-运行情况,APPLICATION_SUPPORT-应用支撑,DATA_RESOURCES-数据资源,BASIC_FACILITIES-基层设施
     */
    private String subType;

}
