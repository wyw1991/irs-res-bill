package com.dtzhejiang.irs.res.bill.app.service;


import com.dtzhejiang.irs.res.bill.app.dto.QuotaApplyDto;
import com.dtzhejiang.irs.res.bill.app.dto.QuotaDto;
import com.dtzhejiang.irs.res.bill.app.query.qry.ApplicationQuotaApplyPageQry;
import com.dtzhejiang.irs.res.bill.app.query.qry.QuotaPageQry;
import com.dtzhejiang.irs.res.bill.app.query.qry.QuotaVersionSaveReq;
import com.dtzhejiang.irs.res.bill.app.response.ApplicationQuotaApplyPageResp;
import com.dtzhejiang.irs.res.bill.app.response.ApplicationQuotaPageResp;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.domain.model.DataCatalog;
import com.dtzhejiang.irs.res.bill.domain.model.QuotaVersion;
import com.dtzhejiang.irs.res.bill.infra.mapper.AppInfoMapper;
import com.dtzhejiang.irs.res.bill.infra.repository.AppInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class QuotaService {

    @Autowired
    private AppInfoMapper mapper;

    @Autowired
    private AppInfoRepository appInfoRepository;

    /**
     * 指标
     * @param pageQry
     * @return
     */
    public PageResponse<ApplicationQuotaPageResp> page(QuotaPageQry pageQry) {
        return null;
    }

    /**
     * 指标详情
     * @param quotaId
     * @return
     */
    public QuotaDto detail(Long quotaId) {
        return null;
    }

    /**
     * 指标申请员申请列表
     * @param pageQry
     * @return
     */
    public PageResponse<ApplicationQuotaApplyPageResp> applicantApplyPage(ApplicationQuotaApplyPageQry pageQry) {
        return null;
    }

    public QuotaApplyDto applicantApplyDetail(String applyCode) {
        return null;
    }

    public PageResponse<QuotaVersion> version(Long quotaId) {
        return null;
    }

    /**
     * 管理员申请列表
     * @param quotaId
     * @return
     */
    public PageResponse<QuotaApplyDto> managerApplyList(Long quotaId) {
        return null;
    }

    /**
     * 新增编辑指标
     * @param quotaDto
     */
    public void save(QuotaDto quotaDto) {
    }

    /**
     * 保存版本
     * @param req
     */
    public void versionSave(QuotaVersionSaveReq req) {
    }

    /**
     * 发送通知
     * @param versionId
     */
    public void versionNotify(Long versionId) {
    }

    /**
     * 数据目录列表
     * @return
     */
    public List<DataCatalog> dataCatalogList() {
        return null;
    }
}
