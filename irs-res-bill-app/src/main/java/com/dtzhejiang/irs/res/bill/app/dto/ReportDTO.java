package com.dtzhejiang.irs.res.bill.app.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dtzhejiang.irs.res.bill.common.enums.*;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReportDTO {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用ID
     */
    private String applicationId;

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
     * 应用管理员
     */
    private String appAdmin;
    /**
     * 应用管理员id
     */
    private String appAdminId;

    /**
     * 报告状态：UN_INIT-待生成,INIT-待提交,PROCESS-处理中，SUCCESS-已出具，FAIL-不通过
     */
    private StatusEnum status;

    /**
     * 出具时间
     */
    private Date finishTime;

    /**
     * 显示的权限列表
     */
    private List<SubTypeEnum> typeList;

    /**
     * 是否可操作 true 是 false 否
     */
    private Boolean canOperate=false;

    private Operation operationDTO;
}
