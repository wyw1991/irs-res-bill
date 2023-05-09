package com.dtzhejiang.irs.res.bill.app.query.qry;


import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import com.dtzhejiang.irs.res.bill.common.enums.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemandPageQry extends PageQuery {


    /**
     * 需求Id
     */
    private Long id;
    /**
     * 需求名称（模糊搜索）
     */
    private String name;
    /**
     * 需求描述（模糊搜索）
     */
    private String describe;
    /**
     * 需求类型：APP-应用、DATA-数据、COMPONENT-组件、CLOUD-云资源、OTHER-其它
     */
    private DemandTypeEnum type;

    /**
     * 需求提出时间（开始）
     */
    private String startTime;

    /**
     * 指标状态 PROCESS-待报告出具,ON_LINE-已上线,OFF_LINE-已下线
     */
    private QuotaStatusEnum status;

    /**
     * 需求提出单位
     */
    private String orgCode;
    /**
     * 我发起的列表: true
     */
    private Boolean myOwn=false;


}
