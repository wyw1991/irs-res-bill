package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 主报告状态
 **/
public enum StatusEnum {

    INIT("INIT", "初始化"),
    PROCESS("PROCESS", "处理中"),

    SUCCESS("SUCCESS", "已出具"),

    FAIL("FAIL", "不通过"),
    ;


    private String code;

    private String name;

    StatusEnum(String code, String name) {
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
    public static StatusEnum fromCode(String code) {
        for (StatusEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
