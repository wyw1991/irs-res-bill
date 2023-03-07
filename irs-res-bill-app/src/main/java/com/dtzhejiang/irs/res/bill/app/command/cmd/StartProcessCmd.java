package com.dtzhejiang.irs.res.bill.app.command.cmd;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StartProcessCmd {
    /**
     * 流程key
     */
    private String processKey;
    /**
     * 业务唯一标识
     */
    private String businessKey;
}
