package com.dtzhejiang.irs.res.bill.app.dto;

import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
public class AppAndReportDTO {
    /**
     * 应用Code
     */
    private String appCode;

    /**
     * 应用Code
     */
    private String appName;

    /**
     * 应用Code
     */
    private List<OssDTO> resourceList;

    public AppAndReportDTO() {
    }

    public AppAndReportDTO(String appCode, String appName) {
        this.appCode = appCode;
        this.appName = appName;
    }
}
