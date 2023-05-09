package com.dtzhejiang.irs.res.bill.app.response;


import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationQuotaPageResp {

    /**
     * 指标id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 指标名称
     */
    private String name;
    /**
     * 指标描述
     */
    private String quotaDesc;

    /**
     * 业务分类，1：应用、2：数据、3：组件、4：云资源、5：其它
     */
    private Byte businessCode;

    /**
     * 版本
     */
    private Byte version;

    /**
     * 0：已下线， 1：上线中
     */
    private Integer status;

    /**
     * 申请跳转地址
     */
    private String applyUrl;

}
