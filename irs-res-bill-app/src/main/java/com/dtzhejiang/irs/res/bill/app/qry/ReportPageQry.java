package com.dtzhejiang.irs.res.bill.app.qry;


import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import com.dtzhejiang.irs.res.bill.common.enums.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportPageQry extends PageQuery {

    /**
     * 搜索关键词
     */
    private String keyword;
    /**
     * 应用状态：TEST_RUN-试运行，RUN-运行中
     */
    private ApplicationStatusEnum applicationStatus;

    /**
     * 应用类型：OFFICE-办公类、BUSINESS_APPLICATION-业务应用类、PORTAL_SITE-门户网站、WEIBO_OR_WECHAT_ACCOUNT-宣传微博\微信公众号、HARDWARE-硬件类、TOOL-工具类、OTHER-其他；
     */
    private TypeEnum type;

    /**
     * 应用领域：TOTAL_GOVERNMENT-政机关整体智治、GOVERNMENT-数字政府、ECONOMY-数字经济、SOCIETY-数字社会、LAW-数字法治、CULTURE-数字文化；
     */
    private FieldEnum field;

    /**
     * 建设层级：PROVINCE-省级、CITY-县（市、区）、VILLAGE-村（社区）；
     */
    private LevelEnum level;

    /**
     * 报告状态：PROCESS-处理中，SUCCESS-已出具，FAIL-不通过
     */
    private StatusEnum status;
    /**
     * 是否关联项目：true:是、false:否；
     */
    private Boolean linkProject;

    /**
     * 是否已审核列表：true:是、false:否；默认否
     */
    private Boolean myAudit=false;

    /**
     * 当前查询列表
     */
    @NonNull
    private String billPermission;

}
