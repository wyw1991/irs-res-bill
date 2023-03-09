package com.dtzhejiang.irs.res.bill.app.command.cmd;


import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SubReportSingleSubmitCmd {
    /**
     * 子报告id
     */
    private Long subReportId;
    /**
     * 变量
     */
    private Map<String, Object> variable;
}
