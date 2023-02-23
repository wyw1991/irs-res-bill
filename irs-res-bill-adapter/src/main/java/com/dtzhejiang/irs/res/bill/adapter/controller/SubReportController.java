package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.qry.SubReportQry;
import org.springframework.web.bind.annotation.*;

/**
 *子报告
 */
@RestController
@RequestMapping("/sub/report")
public class SubReportController {

    /**
     * 异常结果汇总
     * @param id 主报告主键id
     */
    @GetMapping("/fail/list/{id}")
    public SingleResponse<SubReportFailDTO> failList(@PathVariable Long id) {
        return null;
    }

    /**
     * 子报告列表
     */
    @PostMapping("/list")
    public MultiResponse<SubReport> getList(@RequestBody SubReportQry pageQry) {
        return MultiResponse.of(null);
    }

}
