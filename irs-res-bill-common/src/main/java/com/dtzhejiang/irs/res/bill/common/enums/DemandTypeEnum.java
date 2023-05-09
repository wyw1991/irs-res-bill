package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 需求类型
 **/
public enum DemandTypeEnum {
    APP("APP", "应用"),
    DATA("DATA", "数据"),
    COMPONENT("COMPONENT", "组件"),
    CLOUD("CLOUD", "云资源"),
    OTHER("OTHER", "其它")
    ;


    private String code;

    private String name;

    DemandTypeEnum(String code, String name) {
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
    public static DemandTypeEnum fromCode(String code) {
        for (DemandTypeEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
