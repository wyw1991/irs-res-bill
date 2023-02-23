package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 建设层级
 **/
public enum LevelEnum {

    /**
     * 省域
     */
    PROVINCE("PROVINCE", "省"),
    /**
     * 市域
     */
    CITY("CITY", "县（市、区）"),
    /**
     * 县域
     */
    VILLAGE("COUNTY_SCOPE", "村（社区）"),
    ;


    private String code;

    private String name;

    LevelEnum(String code, String name) {
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
    public static LevelEnum fromCode(String code) {
        for (LevelEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
