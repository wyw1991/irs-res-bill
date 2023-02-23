package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 应用领域
 **/
public enum FieldEnum {
    TOTAL_GOVERNMENT("TOTAL_GOVERNMENT", "政机关整体智治"),
    GOVERNMENT("GOVERNMENT", "数字政府"),
    ECONOMY("ECONOMY", "数字经济"),
    SOCIETY("SOCIETY", "数字社会"),
    LAW("LAW", "数字法治"),
    CULTURE("CULTURE", "数字文化"),
    ;


    private String code;

    private String name;

    FieldEnum(String code, String name) {
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
    public static FieldEnum fromCode(String code) {
        for (FieldEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
