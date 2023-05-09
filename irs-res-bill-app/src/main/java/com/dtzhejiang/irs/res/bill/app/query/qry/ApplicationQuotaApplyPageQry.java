package com.dtzhejiang.irs.res.bill.app.query.qry;


import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicationQuotaApplyPageQry extends PageQuery {

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
     * 申请时间开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyTimeStart;

    /**
     * 申请时间结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyTimeEnd;
}
