package com.dtzhejiang.irs.res.bill.app.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
public class OperateLogDTO {

    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 操作名
     */
    private String opName;
    /**
     * 部门名
     */
    private String deptName;
    /**
     * 日志展示所需额外变量
     */
    private Map<String,Object> logVars;

}
