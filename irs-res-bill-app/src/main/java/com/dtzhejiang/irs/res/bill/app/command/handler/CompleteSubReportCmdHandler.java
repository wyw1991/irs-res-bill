package com.dtzhejiang.irs.res.bill.app.command.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.command.cmd.CompleteReportCmd;
import com.dtzhejiang.irs.res.bill.app.command.cmd.CompleteSubReportCmd;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CompleteSubReportCmdHandler extends BaseCompleteCmdHandler{

    public Response apply(CompleteSubReportCmd cmd) {
        SubReport subReport = subReportRepository.getById(cmd.getSubReportId());
        if(subReport == null){
            throw new BusinessException("404", "子报告不存在");
        }
        complete(subReport, cmd.getVariables());
        return Response.buildSuccess();
    }

    public Response applyReport(CompleteReportCmd cmd) {
        LambdaQueryWrapper<SubReport> query = new LambdaQueryWrapper<>();
        query.eq(SubReport::getReportId, cmd.getReportId());
        query.eq(SubReport::getProcessEnd, 0);
        query.select(SubReport::getId, SubReport::getProcessId, SubReport::getTaskId, SubReport::getHistoryHandler);
        List<SubReport> list = subReportRepository.list(query);
        Map<String, Object> variables = cmd.getVariables();
        for (SubReport subReport : list){
            complete(subReport, variables);
        }
        return Response.buildSuccess();
    }

}
