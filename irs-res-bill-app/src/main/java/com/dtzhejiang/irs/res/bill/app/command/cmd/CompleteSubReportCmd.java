package com.dtzhejiang.irs.res.bill.app.command.cmd;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
public class CompleteSubReportCmd {
    /**
     * 子报告id
     */
    @NotNull
    private Long subReportId;
    /**
     * 日志展示所需额外变量
     */
    @NotNull
    private Map<String, Object> variables;

}
