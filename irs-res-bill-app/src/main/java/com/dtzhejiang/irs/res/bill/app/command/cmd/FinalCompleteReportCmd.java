package com.dtzhejiang.irs.res.bill.app.command.cmd;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
public class FinalCompleteReportCmd {
    /**
     * 主报告id
     */
    @NotNull
    private Long reportId;
    /**
     * 退回的子报告id
     */
    private List<Long> subReportIds;
    /**
     * 日志展示所需额外变量
     */
    @NotNull
    private Map<String, Object> variables;

}
