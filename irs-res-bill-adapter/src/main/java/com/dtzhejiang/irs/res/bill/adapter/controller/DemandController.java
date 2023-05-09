package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.DemandDTO;
import com.dtzhejiang.irs.res.bill.app.query.qry.DemandPageQry;
import com.dtzhejiang.irs.res.bill.app.service.DemandService;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.model.Demand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *需求
 */
@RestController
@RequestMapping("/demand")
public class DemandController {

    @Autowired
    private DemandService service;

    /**
     * 分页查询
     */
    @PostMapping("/page")
    public PageResponse<Demand> page(@RequestBody DemandPageQry pageQry) {
        return service.page(pageQry);
    }
    /**
     * 可绑定需求列表
     */
    @PostMapping("/unBandQuotaList")
    public SingleResponse<Map<Long,String>> getUnBandQuotaList() {
        return SingleResponse.of(service.getUnBandQuotaList());
    }


    /**
     * 需求详情
     */
    @PostMapping("/detail")
    public SingleResponse<DemandDTO> getDemand(@RequestBody DemandPageQry pageQry) {
        return SingleResponse.of(service.getDetail(pageQry));
    }

    /**
     * 需求保存
     */
    @PostMapping("/save")
    public SingleResponse<Demand> saveDemand(@RequestBody Demand demand) {
        return SingleResponse.of(service.saveOrUpdate(demand));
    }


}
