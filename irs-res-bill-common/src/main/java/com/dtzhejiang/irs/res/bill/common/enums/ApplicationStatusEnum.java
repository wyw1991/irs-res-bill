package com.dtzhejiang.irs.res.bill.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 应用状态
 */
public enum ApplicationStatusEnum {

    /**
     * 试运行
     */
    TEST_RUN("APPLY", "试运行"),
    /**
     * 运行中
     */
    RUN("SUCCESS", "运行中")
    ;

    @EnumValue
    private String code;

    private String name;

    ApplicationStatusEnum(String code, String name) {
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
    public static ApplicationStatusEnum fromCode(String code) {
        for (ApplicationStatusEnum areaRangeEnum : values()) {
            if(areaRangeEnum.code.equals(code)) {
                return areaRangeEnum;
            }
        }
        return null;
    }


}
