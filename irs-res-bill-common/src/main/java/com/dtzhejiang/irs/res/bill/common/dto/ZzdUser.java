package com.dtzhejiang.irs.res.bill.common.dto;

import lombok.Data;

@Data
public class ZzdUser {
    private Long accountId;
    private String employeeCode;
    private Long tenantId;
    private String clientType;

    public ZzdUser(Long accountId, String employeeCode) {
        this.accountId = accountId;
        this.employeeCode = employeeCode;
        this.tenantId = 196729L;
        this.clientType = "PC";
    }

    public UserId getUserId() {
        return new UserId(this.accountId,this.employeeCode);
    }

}
