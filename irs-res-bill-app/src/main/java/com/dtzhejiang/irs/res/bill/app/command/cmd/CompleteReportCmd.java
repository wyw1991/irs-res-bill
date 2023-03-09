package com.dtzhejiang.irs.res.bill.app.command.cmd;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class CompleteReportCmd {
    /**
     * 主报告id
     */
    @NotNull
    private String reportId;
    /**
     * 日志展示所需额外变量
     */
    @NotNull
    private Map<String, Object> variables;

}
