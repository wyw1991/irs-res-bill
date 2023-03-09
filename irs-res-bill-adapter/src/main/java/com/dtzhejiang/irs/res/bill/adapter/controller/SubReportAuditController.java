package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *子报告审核
 */
@RestController
@RequestMapping("/sub/report/audit")
public class SubReportAuditController {

    /**
     * 单个审核(通过)
     */
    @PostMapping("/agree")
    public SingleResponse agree() {
        return SingleResponse.buildSuccess();
    }

    /**
     * 单个审核(驳回)
     */
    @PostMapping("/backoff")
    public SingleResponse backoff() {
        return SingleResponse.buildSuccess();
    }

    /**
     * 全部通过
     */
    @RequiresRoles(value = {"irs-res-bill_business_leader", "irs-res-bill_compliance_confirm", "irs-res-bill_compliance_leader"})
    @PostMapping("/allAgree")
    public SingleResponse allAgree() {
        return SingleResponse.buildSuccess();
    }

    /**
     * 全部退回
     */
    @RequiresRoles(value = {"irs-res-bill_compliance_confirm", "irs-res-bill_compliance_leader"})
    @PostMapping("/allBackoff")
    public SingleResponse allbackoff() {
        return SingleResponse.buildSuccess();
    }

    /**
     * 部分退回
     */
    @RequiresRoles(value = {"irs-res-bill_business_leader"})
    @PostMapping("/someBackoff")
    public SingleResponse someBackoff() {
        return SingleResponse.buildSuccess();
    }

}
