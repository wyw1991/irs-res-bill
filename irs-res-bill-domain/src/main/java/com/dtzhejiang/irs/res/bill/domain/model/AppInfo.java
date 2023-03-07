package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dtzhejiang.irs.res.bill.common.enums.ApplicationStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.FieldEnum;
import com.dtzhejiang.irs.res.bill.common.enums.LevelEnum;
import com.dtzhejiang.irs.res.bill.common.enums.TypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@TableName(value = "app_info",autoResultMap = true)
public class AppInfo {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用ID
     */
    private String applicationId;

    /**
     * 应用类型：OFFICE-办公类、BUSINESS_APPLICATION-业务应用类、PORTAL_SITE-门户网站、WEIBO_OR_WECHAT_ACCOUNT-宣传微博\微信公众号、HARDWARE-硬件类、TOOL-工具类、OTHER-其他；
     */
    private TypeEnum type;

    /**
     * 是否关联项目：true:是、false:否；
     */
    private boolean linkProject;

    /**
     * 建设层级：PROVINCE-省级、CITY-县（市、区）、VILLAGE-村（社区）；
     */
    private LevelEnum level;

    /**
     * 应用状态：TEST_RUN-试运行，RUN-运行中
     */
    private ApplicationStatusEnum applicationStatus;

    /**
     * 应用领域：TOTAL_GOVERNMENT-政机关整体智治、GOVERNMENT-数字政府、ECONOMY-数字经济、SOCIETY-数字社会、LAW-数字法治、CULTURE-数字文化；
     */
    private FieldEnum field;

    /**
     * 规范上云 
     */
    private String basicStatus;

    /**
     * 合规利用
     */
    private String complianceUse;

    /**
     * 目录归集数据总量 
     */
    private String dataGatherCount;

    /**
     * 云资源总数 
     */
    private String cloudSum;

    /**
     * CPU平均利用率 
     */
    private String cpuAvg;

    /**
     * 硬盘平均利用率 
     */
    private String ioAvg;

    /**
     * 内存平均利用率 
     */
    private String memAvg;

    /**
     * 数据按需编目
     */
    private String dataCatalog;

    /**
     * 数据按需归集 
     */
    private String dataGather;

    /**
     * 数据编目数量 
     */
    private String dataCatalogQuantity;

    /**
     * 已归集目录数量 
     */
    private String dataGatherQuantity;

    /**
     * 批量数据申请数量 
     */
    private String batchDataApply;

    /**
     * 接口申请数量 
     */
    private String apiApplyQuantity;

    /**
     * 接口调用数量 
     */
    private String apiInvokeQuantity;

    /**
     * 统一组件规范使用 
     */
    private String appStatus;

    /**
     * 按需解耦组件
     */
    private String decoupling;

    /**
     * 注册组件量 
     */
    private String registerComponent;

    /**
     * 注册组件申请量 
     */
    private String registerComponentApplyQuantity;

    /**
     * 统一组件申请量 
     */
    private String unifyComponentApplyQuantity;

    /**
     * 统一组件调用量 
     */
    private String unifyComponentInvokeQuantity;

    /**
     * 治理侧规范发布
     */
    private String governPublish;

    /**
     * 服务侧规范发布
     */
    private String serverPublish;

    /**
     * 浙里办发布数 
     */
    private String zlbPub;

    /**
     * 浙里办月访问量 
     */
    private String zlbPv;

    /**
     * 浙里办用户数 
     */
    private String zlbUser;

    /**
     * 浙政钉发布数 
     */
    private String zzdPub;

    /**
     * 浙政钉月访问量 
     */
    private String zzdPv;

    /**
     * 浙政钉用户数 
     */
    private String zzdUser;

    /**
     * 市县贯通情况 
     */
    private String regionConnect;

    /**
     * ISV规范登记 
     */
    private String isvPermit;

    /**
     * 通过代码检测 
     */
    private String codeCheck;

    /**
     * 通过等保评测 
     */
    private String securityProtect;

    /**
     * 通过密码评测 
     */
    private String secretCheck;

    /**
     * 是否业务协同 
     */
    private String coordination;

    /**
     * 协同接口注册量 
     */
    private String registQuantity;

    /**
     * 注册协同接口调用量 
     */
    private String invokeQuantity;

    /**
     * 协同接口申请量 
     */
    private String applyQuantity;

    /**
     * 申请协同接口调用量
     */
    private String applyInvokeQuantity;

    /**
     * 项目编码
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 应用建设单位
     */
    private String appBuildOrg;
    /**
     * 应用管理员
     */
    private String appAdmin;
    /**
     * 上线时间
     */
    private Date onlineTime;
    /**
     * 统建范围
     */
    private String areaRange;
    /**
     * 访问地址
     */
    private String appUrl;


}