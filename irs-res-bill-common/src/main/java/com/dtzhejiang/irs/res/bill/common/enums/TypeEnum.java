package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 建设层级
 **/
public enum TypeEnum {

    OFFICE("OFFICE", "办公类"),
    BUSINESS_APPLICATION("BUSINESS_APPLICATION", "业务应用类"),
    PORTAL_SITE("PORTAL_SITE", "门户网站"),
    WEIBO_OR_WECHAT_ACCOUNT("WEIBO_OR_WECHAT_ACCOUNT", "宣传微博\\微信公众号"),
    HARDWARE("HARDWARE", "硬件类"),
    TOOL("TOOL", "工具类"),
    OTHER("OTHER", "其他"),
    ;


    private String code;

    private String name;

    TypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static TypeEnum fromCode(String code) {
        for (TypeEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
