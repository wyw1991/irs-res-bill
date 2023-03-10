package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.command.cmd.SubReportSingleSubmitCmd;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportDTO;
import com.dtzhejiang.irs.res.bill.app.service.SubReportService;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.query.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *子报告
 */
@RestController
@RequestMapping("/sub/report")
public class SubReportController {
    @Autowired
    private SubReportService subReportService;

    /**
     * 异常结果汇总
     * @param qry
     */
    @PostMapping("/fail/list")
    public SingleResponse<SubReportFailDTO> failList(@RequestBody SubReportQry qry) {
        return SingleResponse.of(subReportService.failList(qry));
    }

    /**
     * 单个子报告列表
     */
    @PostMapping("/list")
    public SingleResponse<SubReportDTO> getList(@RequestBody SubReportQry qry) {
        return SingleResponse.of(subReportService.getSubReportDTO(qry));
    }

    /**
     * 全部子报告列表
     */
    @PostMapping("/allList")
    public MultiResponse<SubReportDTO> getDTOList(@RequestBody SubReportQry qry) {
        return MultiResponse.of(subReportService.convertToList(subReportService.getList(qry)));
    }

    /**
     * 子报告权限列表
     */
    @PostMapping("/perList")
    public MultiResponse<SubTypeEnum> getSubReportPermissionList() {
        return MultiResponse.of(subReportService.getSubReportPermissionList());
    }

    /**
     * 提交
     */
    @GetMapping("/submit")
    public SingleResponse createSubReport(@NonNull Long reportId) {
        subReportService.submitSubReport(reportId);
        return SingleResponse.buildSuccess();
    }

    /**
     * 详情页-提交
     */
    @PostMapping("/single/submit")
    public SingleResponse submitSubReport(@RequestBody SubReportSingleSubmitCmd cmd) {
        subReportService.submitSingleSubReport(cmd);
        return SingleResponse.buildSuccess();
    }

}
