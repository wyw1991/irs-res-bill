package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 流程节点
 **/
public enum BillPermissionEnum {

    VALID_CONFIRM("VALID_CONFIRM", "合规性确认"),
    VALID_PASS("VALID_PASS", "合规性出具"),
    GENERATE("GENERATE", "报告生成"),
    REVIEW("REVIEW", "报告初审"),
    CONFIRM("CONFIRM", "报告确认"),
    AUDIT("AUDIT", "报告审核"),
    PASS("PASS", "报告出具"),
    ;


    private String code;

    private String name;

    BillPermissionEnum(String code, String name) {
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
    public static BillPermissionEnum fromCode(String code) {
        for (BillPermissionEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
