package com.dtzhejiang.irs.res.bill.domain.user.valueobject;

public enum UserRole {

    /**
     * 应用管理员
     */
    APP_MANAGER("irs-res-bill_app_manager"),
    /**
     * 合规性确认员
     */
    COMPLIANCE_CONFIRM("irs-res-bill_compliance_confirm"),
    /**
     * 合规终审员
     */
    COMPLIANCE_LEADER("irs-res-bill_compliance_leader"),
    /**
     * 应用审核终审员
     */
    BUSINESS_LEADER("irs-res-bill_business_leader");

    private final String code;

    UserRole(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
