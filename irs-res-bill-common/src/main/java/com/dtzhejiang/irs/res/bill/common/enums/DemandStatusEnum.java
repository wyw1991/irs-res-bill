package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 主报告状态
 **/
public enum DemandStatusEnum {

    UN_SUBMIT("UN_SUBMIT", "待提交"),
    UN_AUDIT("UN_AUDIT", "待审批"),
    AUDIT("AUDIT", "审批中"),
    SUCCESS("SUCCESS", "已通过"),
    FAIL("FAIL", "已拒绝"),
    ;


    private String code;

    private String name;

    DemandStatusEnum(String code, String name) {
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
    public static DemandStatusEnum fromCode(String code) {
        for (DemandStatusEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
