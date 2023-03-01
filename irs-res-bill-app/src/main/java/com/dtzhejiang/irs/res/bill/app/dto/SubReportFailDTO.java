package com.dtzhejiang.irs.res.bill.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubReportFailDTO {

    /**
     * 基层设施
     */
    private SubReportDTO basicFacilities;
    /**
     * 数据资源
     */
    private SubReportDTO dataResources;
    /**
     * 应用支撑
     */
    private SubReportDTO applicationSupport;
    /**
     * 运行情况
     */
    private SubReportDTO operation;
    /**
     * 网络安全
     */
    private SubReportDTO networkSecurity;
    /**
     * 业务应用
     */
    private SubReportDTO businessApplication;

}