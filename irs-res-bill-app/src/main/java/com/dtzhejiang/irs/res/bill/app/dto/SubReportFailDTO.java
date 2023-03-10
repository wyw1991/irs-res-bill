package com.dtzhejiang.irs.res.bill.app.dto;

import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

@Getter
@Setter
public class SubReportFailDTO {
    /**
     * 总数
     */
    private Integer totalNum=38;
    /**
     * 问题数
     */
    private Long failNum=0L;
    /**
     * 基层设施
     */
    private SubReportDTO BASIC_FACILITIES;
    /**
     * 数据资源
     */
    private SubReportDTO DATA_RESOURCES;
    /**
     * 应用支撑
     */
    private SubReportDTO APPLICATION_SUPPORT;
    /**
     * 运行情况
     */
    private SubReportDTO OPERATION;
    /**
     * 网络安全
     */
    private SubReportDTO NETWORK_SECURITY;
    /**
     * 业务应用
     */
    private SubReportDTO BUSINESS_APPLICATION;

    //public Integer getTotalNum() {
    //    return basicFacilities.getTotalNum()+dataResources.getTotalNum()+applicationSupport.getTotalNum()+operation.getTotalNum()+networkSecurity.getTotalNum()+businessApplication.getTotalNum();
    //}

    public Long getFailNum() {
        return (BUSINESS_APPLICATION!=null?BUSINESS_APPLICATION.getFailNum():0)
                +( NETWORK_SECURITY!=null? NETWORK_SECURITY.getFailNum():0)
                +(OPERATION.getFailNum())
                +(OPERATION!=null?OPERATION.getFailNum():0)
                +( APPLICATION_SUPPORT!=null?APPLICATION_SUPPORT.getFailNum():0)
                +(DATA_RESOURCES!=null?DATA_RESOURCES.getFailNum():0)
                +(BASIC_FACILITIES!=null?BASIC_FACILITIES.getFailNum():0);
    }

}