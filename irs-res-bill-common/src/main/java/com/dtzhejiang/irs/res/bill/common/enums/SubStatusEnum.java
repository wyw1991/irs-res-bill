package com.dtzhejiang.irs.res.bill.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 子报告状态
 **/
public enum SubStatusEnum {

    UN_SUBMIT("UN_SUBMIT", "待提交"),

    UN_COMMIT("UN_COMMIT", "待确认"),

    UN_AUDIT("UN_AUDIT", "待审核"),

    APPROVED("APPROVED", "已审核"),
    ;

    private String code;

    private String name;

    SubStatusEnum(String code, String name) {
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
    public static SubStatusEnum fromCode(String code) {
        for (SubStatusEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
