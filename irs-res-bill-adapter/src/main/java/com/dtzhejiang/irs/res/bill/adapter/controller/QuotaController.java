package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.QuotaApplyDto;
import com.dtzhejiang.irs.res.bill.app.dto.QuotaDto;
import com.dtzhejiang.irs.res.bill.app.query.qry.ApplicationQuotaApplyPageQry;
import com.dtzhejiang.irs.res.bill.app.query.qry.QuotaPageQry;
import com.dtzhejiang.irs.res.bill.app.query.qry.QuotaVersionSaveReq;
import com.dtzhejiang.irs.res.bill.app.response.ApplicationQuotaApplyPageResp;
import com.dtzhejiang.irs.res.bill.app.response.ApplicationQuotaPageResp;
import com.dtzhejiang.irs.res.bill.app.service.QuotaService;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.model.DataCatalog;
import com.dtzhejiang.irs.res.bill.domain.model.QuotaVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 指标相关
 */
@RestController
@RequestMapping("/quota")
public class QuotaController {

    @Autowired
    private QuotaService service;

    /**
     * 指标列表
     */
    @PostMapping("/page")
    public PageResponse<ApplicationQuotaPageResp> applicantPage(@RequestBody QuotaPageQry pageQry) {
        return service.page(pageQry);
    }

    /**
     * 指标详情
     * @param quotaId 指标id
     */
    @GetMapping("/detail")
    public SingleResponse<QuotaDto> detail(@RequestParam("id") Long quotaId) {
        return SingleResponse.of(service.detail(quotaId));
    }

    /**
     * 申请员申请列表
     */
    @PostMapping("/applicant/apply/page")
    public PageResponse<ApplicationQuotaApplyPageResp> applicantApplyPage(@RequestBody ApplicationQuotaApplyPageQry pageQry) {
        return service.applicantApplyPage(pageQry);
    }

    /**
     * 申请员申请详情
     * @param applyCode 申请编码
     */
    @GetMapping("/applicant/apply/detail")
    public SingleResponse<QuotaApplyDto> applicantApplyDetail(@RequestParam("applyCode") String applyCode) {
        return SingleResponse.of(service.applicantApplyDetail(applyCode));
    }

    /**
     * 我管理的指标列表
     */
    @PostMapping("/manager/page")
    public PageResponse<ApplicationQuotaPageResp> managerPage(@RequestBody QuotaPageQry pageQry) {
        return service.page(pageQry);
    }

    /**
     * 指标版本信息
     * @param quotaId 指标id
     */
    @GetMapping("/version")
    public PageResponse<QuotaVersion> version(@RequestParam("quotaId") Long quotaId) {
        return service.version(quotaId);
    }

    /**
     * 管理员指标申请记录
     * @param quotaId 指标id
     */
    @GetMapping("/manager/apply/list")
    public PageResponse<QuotaApplyDto> managerApplyList(@RequestParam("quotaId") Long quotaId) {
        return service.managerApplyList(quotaId);
    }

    /**
     * 新增编辑指标
     */
    @PostMapping("/save")
    public SingleResponse save(@RequestBody QuotaDto quotaDto) {
        service.save(quotaDto);
        return SingleResponse.buildSuccess();
    }

    /**
     * 新增编辑版本信息
     */
    @PostMapping("/version/save")
    public SingleResponse versionSave(@RequestBody QuotaVersionSaveReq req) {
        service.versionSave(req);
        return SingleResponse.buildSuccess();
    }

    /**
     * 发送通知
     * @param versionId 版本id
     */
    @GetMapping("/version/notify")
    public SingleResponse versionNotify(@RequestParam("versionId") Long versionId) {
        service.versionNotify(versionId);
        return SingleResponse.buildSuccess();
    }

    /**
     * 数据目录列表
     */
    @GetMapping("/dataCatalog/list")
    public MultiResponse<DataCatalog> dataCatalogList() {
        return MultiResponse.of(service.dataCatalogList());
    }
}
