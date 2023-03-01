package com.dtzhejiang.irs.res.bill.app.command.handler;

import com.dtzhejiang.irs.res.bill.app.command.cmd.SubReportAssignCmd;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.repository.SubReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class SubReportCommandHandler {

    @Autowired
    private SubReportRepository subReportRepository;

    /**
     * 更新子报告流程分配信息
     * @param assignCmd
     */
    public void updateAssignInfo(SubReportAssignCmd assignCmd){
        SubReport subReport = subReportRepository.getByApprovalId(assignCmd.getProcessInstanceId());
        if(subReport == null){
            log.warn("子报告不存在,approvalId:{}", assignCmd.getProcessInstanceId());
            return;
        }
        subReport.setTaskId(assignCmd.getTaskId());
        subReport.setTaskName(assignCmd.getTaskName());
        subReport.setCurrentHandler(assignCmd.getAssignee());
        subReport.setCurrentRole(assignCmd.getRole());
        subReport.setUpdateTime(new Date());
        subReportRepository.updateById(subReport);
    }
}
