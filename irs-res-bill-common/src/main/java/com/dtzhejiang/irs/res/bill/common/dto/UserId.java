package com.dtzhejiang.irs.res.bill.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UserId {
    /**
     * 浙政钉账号id
     */
    private Long accountId;
    /**
     * 浙政钉员工编码
     */
    private String employeeCode;
}
