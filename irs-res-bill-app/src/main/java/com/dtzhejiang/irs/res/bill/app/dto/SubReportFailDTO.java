package com.dtzhejiang.irs.res.bill.app.dto;

import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SubReportFailDTO {
    /**
     * 基层设施
     */
    private List<SubReport> basicFacilities;
    /**
     * 数据资源
     */
    private List<SubReport> dataResources;
    /**
     * 应用支撑
     */
    private List<SubReport> applicationSupport;
    /**
     * 运行情况
     */
    private List<SubReport> operation;
    /**
     * 网络安全
     */
    private List<SubReport> networkSecurity;
    /**
     * 业务应用
     */
    private List<SubReport> businessApplication;

}