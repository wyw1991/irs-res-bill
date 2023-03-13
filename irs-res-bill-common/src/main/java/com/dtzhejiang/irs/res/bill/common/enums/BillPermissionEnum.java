package com.dtzhejiang.irs.res.bill.common.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 流程节点
 **/
public enum BillPermissionEnum {

    generate("generate", "报告生成"),
    //valid_confirm("valid_confirm", "合规性确认"),

    //review("review", "报告初审"),
    //confirm("confirm", "报告确认"),

    audit("audit", "报告审核"),

    //valid_pass("valid_pass", "合规性出具"),

    //pass("pass", "报告出具"),
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
