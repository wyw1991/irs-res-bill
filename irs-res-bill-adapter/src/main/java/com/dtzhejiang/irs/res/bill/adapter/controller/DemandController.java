package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.query.qry.DemandPageQry;
import com.dtzhejiang.irs.res.bill.app.service.DemandService;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.model.Demand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *主报告
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
     * 需求详情
     */
    @PostMapping("/detail")
    public SingleResponse<Demand> getDemand(@RequestBody DemandPageQry pageQry) {
        return SingleResponse.of(service.getDetail(pageQry));
    }

    /**
     * 需求详情
     */
    @PostMapping("/save")
    public SingleResponse<Demand> saveDemand(@RequestBody Demand demand) {
        return SingleResponse.of(service.saveOrUpdate(demand));
    }


}
