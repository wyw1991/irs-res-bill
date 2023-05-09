package com.dtzhejiang.irs.res.bill.app.response;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicationQuotaApplyPageResp {

    /**
     * 指标id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long quotaId;

    /**
     * 申请编码
     */
    private String applyCode;

    /**
     * 指标描述
     */
    private String quotaName;

    /**
     * 指标描述
     */
    private String quotaDesc;

    /**
     * 业务分类，1：应用、2：数据、3：组件、4：云资源、5：其它
     */
    private Byte businessCode;

    /**
     * 申请时间
     */
    private Date applyTime;

}
