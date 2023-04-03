package com.dtzhejiang.irs.res.bill.app.command.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.command.cmd.FinalCompleteReportCmd;
import com.dtzhejiang.irs.res.bill.app.service.ReportService;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import com.dtzhejiang.irs.res.bill.common.enums.StatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.repository.SubReportRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FinalCompleteCmdHandler extends BaseCompleteCmdHandler{

    @Autowired
    private ReportService reportService;
    @Autowired
    private SubReportRepository subReportRepository;
    @Transactional
    public Response apply(FinalCompleteReportCmd cmd) {
        Report report = reportService.getReport(cmd.getReportId());
        LambdaQueryWrapper<SubReport> query = new LambdaQueryWrapper<>();
        query.eq(SubReport::getReportId, cmd.getReportId());
        query.ne(SubReport::getSubStatus, SubStatusEnum.SUCCESS);
        List<SubReport> list = subReportRepository.list(query);
        Collection<Long> backOffIds = cmd.getSubReportIds();
        Map<Long, SubReport> subReportMap = list.stream().collect(Collectors.toMap(SubReport::getId, Function.identity()));
        Map<String, Object> variables = cmd.getVariables();
        // 退回处理
        if(CollectionUtils.isNotEmpty(backOffIds)){
            Collection<Long> allIds = list.stream().map(SubReport::getId).collect(Collectors.toList());
            Collection<Long> agreeIds = CollectionUtils.subtract(allIds, backOffIds);
            backOffIds.forEach(backOffId -> complete(subReportMap.get(backOffId), variables));
            agreeIds.forEach(agreeId -> complete(subReportMap.get(agreeId), buildSuccessVariables()));
            //更新主报告状态
            report.setStatus(StatusEnum.FAIL);
        }else {
            // 全部通过处理
            list.forEach(e -> complete(e, variables));
            //更新主报告状态
            report.setFinishTime(new Date());
            report.setStatus(StatusEnum.SUCCESS);
        }
        reportService.saveOrUpdate(report);
        return Response.buildSuccess();
    }


    public static Map<String,Object> buildSuccessVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("status", "SUCCESS");
        variables.put("remark", "指标符合要求，审批通过");
        variables.put("remark__Label", "处理意见");
        variables.put("leader_audit_operate", "agree");
        Map<String, Object> optionInfo = new HashMap<>();
        optionInfo.put("configOpId", "agree");
        optionInfo.put("configOpName", "通过");
        variables.put("opInfo", optionInfo);
        return variables;
    }
}
