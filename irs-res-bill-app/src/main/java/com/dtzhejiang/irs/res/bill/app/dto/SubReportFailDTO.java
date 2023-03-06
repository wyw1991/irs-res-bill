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
    private Long failNum;

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

    //public Integer getTotalNum() {
    //    return basicFacilities.getTotalNum()+dataResources.getTotalNum()+applicationSupport.getTotalNum()+operation.getTotalNum()+networkSecurity.getTotalNum()+businessApplication.getTotalNum();
    //}

    public Long getFailNum() {
        return basicFacilities.getFailNum()+dataResources.getFailNum()+applicationSupport.getFailNum()+operation.getFailNum()+networkSecurity.getFailNum()+businessApplication.getFailNum();
    }

}