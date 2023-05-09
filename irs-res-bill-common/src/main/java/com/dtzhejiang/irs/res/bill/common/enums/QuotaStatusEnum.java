package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 子报告状态
 **/
public enum QuotaStatusEnum {


    PROCESS("PROCESS", "待报告出具"),

    ON_LINE("ON_LINE", "已上线"),

    OFF_LINE("OFF_LINE", "已下线"),
    ;

    private String code;

    private String name;



    QuotaStatusEnum(String code, String name) {
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
    public static QuotaStatusEnum fromCode(String code) {
        for (QuotaStatusEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
