package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.SubReportDTO;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.app.service.HisIndicesService;
import com.dtzhejiang.irs.res.bill.app.service.SubReportService;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 历史参数
 */
@RestController
@RequestMapping("/indices")
public class HisIndicesController {
    @Autowired
    private HisIndicesService hisIndicesService;

    /**
     * 保存接口
     * @param entity 保存实体
     */
    @GetMapping("/save")
    public SingleResponse save(@RequestBody HisIndices entity) {
        return SingleResponse.of(hisIndicesService.save(entity));
    }



}
