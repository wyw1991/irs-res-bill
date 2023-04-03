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
    private Integer totalNum;
    /**
     * 问题数
     */
    private Integer failNum=0;
    /**
     * 基层设施
     */
    private SubReportDTO basic_facilities;
    /**
     * 数据资源
     */
    private SubReportDTO data_resources;
    /**
     * 应用支撑
     */
    private SubReportDTO application_support;
    /**
     * 运行情况
     */
    private SubReportDTO operation;
    /**
     * 网络安全
     */
    private SubReportDTO network_security;
    /**
     * 业务应用
     */
    private SubReportDTO business_application;

    public Integer getTotalNum() {
        return basic_facilities.getTotalNum()+data_resources.getTotalNum()+application_support.getTotalNum()+operation.getTotalNum()+business_application.getTotalNum()+network_security.getTotalNum();
    }

    public Long getFailNum() {
        return (basic_facilities!=null?basic_facilities.getFailNum():0)
                +( data_resources!=null? data_resources.getFailNum():0)
                +(application_support!=null?application_support.getFailNum():0)
                +( operation!=null?operation.getFailNum():0)
                +(network_security!=null?network_security.getFailNum():0)
                +(business_application!=null?business_application.getFailNum():0);
    }

}