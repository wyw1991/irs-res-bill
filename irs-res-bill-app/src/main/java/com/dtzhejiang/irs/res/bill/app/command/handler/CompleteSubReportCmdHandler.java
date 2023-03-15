package com.dtzhejiang.irs.res.bill.app.command.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.command.cmd.CompleteReportCmd;
import com.dtzhejiang.irs.res.bill.app.command.cmd.CompleteSubReportCmd;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessTask;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.infra.repository.SubReportRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class CompleteSubReportCmdHandler{

    @Autowired
    private ProcessGateway processGateway;
    @Autowired
    private UserGateway userGateway;
    @Autowired
    private SubReportRepository subReportRepository;

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
        query.select(SubReport::getId, SubReport::getProcessId, SubReport::getTaskId, SubReport::getHistoryHandler);
        List<SubReport> list = subReportRepository.list(query);
        Map<String, Object> variables = cmd.getVariables();
        for (SubReport subReport : list){
            complete(subReport, variables);
        }
        return Response.buildSuccess();
    }


    private void complete(SubReport subReport, Map<String, Object> variables){
        String username = userGateway.getCurrentUser().getUserName();
        ProcessTask currentProcessTask = processGateway.getCurrentProcessTask(subReport.getProcessId(), username);
        if (currentProcessTask == null || !Objects.equals(currentProcessTask.getId(), subReport.getTaskId())) {
            throw new BusinessException("当前页面信息已失效,请刷新页面！");
        }
        processGateway.completeProcessTask(subReport.getTaskId(), variables, username);
        String historyHandler = subReport.getHistoryHandler();
        if(StringUtils.isNotBlank(historyHandler)){
            historyHandler  = historyHandler + "," + username;
        }else {
            historyHandler=username;
        }
        subReport.setHistoryHandler(historyHandler);
        subReportRepository.updateById(subReport);
    }

}
