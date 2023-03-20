package com.dtzhejiang.irs.res.bill.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.Arrays;
import java.util.List;

/**
 * 子报告状态
 **/
public enum SubStatusEnum {

    UN_SUBMIT("UN_SUBMIT", "待提交"),

    UN_VALID_CONFIRM("UN_VALID_CONFIRM", "待合规性确认"),

    UN_RE_SUBMIT("UN_RE_SUBMIT", "待重新提交"),

    UN_COMMIT("UN_COMMIT", "待确认"),

    UN_DOUBLE_COMMIT("UN_DOUBLE_COMMIT", "待确认(复审)"),

    UN_AUDIT("UN_AUDIT", "待审核"),

    UN_AUDIT_ISSUE("UN_AUDIT_ISSUE", "待合规性出具"),

    UN_REPORT_ISSUE("UN_REPORT_ISSUE", "待报告出具"),

    SUCCESS("SUCCESS", "已出具"),

    FAIL("FAIL", "不通过"),
    ;

    private String code;

    private String name;

    /**
     * 需要统一审批状态List
     */
    public static List<SubStatusEnum> unifyList=Arrays.asList(UN_SUBMIT,UN_RE_SUBMIT,UN_VALID_CONFIRM,UN_AUDIT_ISSUE,UN_REPORT_ISSUE);

    /**
     * 是否审核完成：true 是 false 否
     * @param subStatusEnum
     * @return
     */
    public static boolean isApproved(SubStatusEnum subStatusEnum){
        List<SubStatusEnum> list=Arrays.asList(SUCCESS,FAIL);
        return list.contains(subStatusEnum);
    }



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
