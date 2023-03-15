package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 子报告类型
 **/
public enum SubTypeEnum {
    BUSINESS_APPLICATION("BUSINESS_APPLICATION", "业务应用"),
    NETWORK_SECURITY("NETWORK_SECURITY", "网络安全"),
    OPERATION("OPERATION", "运行情况"),
    APPLICATION_SUPPORT("APPLICATION_SUPPORT", "应用支撑"),
    DATA_RESOURCES("DATA_RESOURCES", "数据资源"),
    BASIC_FACILITIES("BASIC_FACILITIES", "基层设施"),
    ;



    private String code;

    private String name;

    SubTypeEnum(String code, String name) {
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
    public static SubTypeEnum fromCode(String code) {
        for (SubTypeEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
