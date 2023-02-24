package com.dtzhejiang.irs.res.bill.common.enums;

/**
 * 应用类型
 **/
public enum OperationResultsStatusEnum {

    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),
    QUESTION("QUESTION", "有问题"),
    ;


    private String code;

    private String name;

    OperationResultsStatusEnum(String code, String name) {
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
    public static OperationResultsStatusEnum fromCode(String code) {
        for (OperationResultsStatusEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
