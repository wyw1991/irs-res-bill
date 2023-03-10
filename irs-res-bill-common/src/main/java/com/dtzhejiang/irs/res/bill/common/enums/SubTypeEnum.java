package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 子报告类型
 **/
public enum SubTypeEnum {
// (basic_facilities!=null?basic_facilities.getFailNum():0)
//                +( data_resources!=null? data_resources.getFailNum():0)
//                +(application_support!=null?application_Support.getFailNum():0)
//                +( operation!=null?operation.getFailNum():0)
//                +(network_security!=null?network_security.getFailNum():0)
//                +(business_application!=null?business_application.getFailNum():0);
    business_application("business_application", "业务应用"),
    network_security("network_security", "网络安全"),
    operation("operation", "运行情况"),
    application_support("application_support", "应用支撑"),
    data_resources("data_resources", "数据资源"),
    basic_facilities("basic_facilities", "基层设施"),
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
