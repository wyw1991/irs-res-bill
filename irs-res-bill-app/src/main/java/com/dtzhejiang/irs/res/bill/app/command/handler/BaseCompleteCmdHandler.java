package com.dtzhejiang.irs.res.bill.app.command.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessTask;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.infra.repository.SubReportRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

public abstract class BaseCompleteCmdHandler {
    @Autowired
    protected ProcessGateway processGateway;
    @Autowired
    protected UserGateway userGateway;
    @Autowired
    protected SubReportRepository subReportRepository;

    @Transactional
    protected void complete(SubReport subReport, Map<String, Object> variables){
        String username = userGateway.getCurrentUser().getUserName();
        ProcessTask currentProcessTask = processGateway.getCurrentProcessTask(subReport.getProcessId(), username);
        if (currentProcessTask == null || !Objects.equals(currentProcessTask.getId(), subReport.getTaskId())) {
            throw new BusinessException("当前页面信息已失效,请刷新页面！");
        }
        String historyHandler = subReport.getHistoryHandler();
        if(StringUtils.isNotBlank(historyHandler)){
            historyHandler  = historyHandler + "," + username;
        }else {
            historyHandler= username;
        }
        subReportRepository.update(null, Wrappers.<SubReport>lambdaUpdate()
                .eq(SubReport::getId, subReport.getId())
                .set(SubReport::getHistoryHandler, historyHandler));

        processGateway.completeProcessTask(subReport.getTaskId(), variables, username);
    }
}
