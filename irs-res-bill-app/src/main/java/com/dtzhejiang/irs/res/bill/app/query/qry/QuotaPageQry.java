package com.dtzhejiang.irs.res.bill.app.query.qry;


import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuotaPageQry extends PageQuery {

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
     * 状态 0：已下线， 1：上线中
     */
    private Integer status;

}
