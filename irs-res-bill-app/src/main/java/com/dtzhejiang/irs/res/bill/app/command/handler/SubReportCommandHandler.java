package com.dtzhejiang.irs.res.bill.app.command.handler;

import com.dtzhejiang.irs.res.bill.app.command.cmd.SubReportUpdateProcessInfoCmd;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
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
     * @param processInfoCmd
     */
    public void updateAssignInfo(SubReportUpdateProcessInfoCmd processInfoCmd){
        SubReport subReport = subReportRepository.getByProcessId(processInfoCmd.getProcessInstanceId());
        if(subReport == null){
            log.warn("子报告不存在,processId:{}", processInfoCmd.getProcessInstanceId());
            throw new BusinessException("404", "子报告不存在");
        }
        subReport.setTaskId(processInfoCmd.getTaskId());
        subReport.setSubStatus(SubStatusEnum.fromCode(processInfoCmd.getStatus()));
        subReport.setTaskName(processInfoCmd.getTaskName());
        subReport.setCurrentHandler(processInfoCmd.getAssignee());
        subReport.setCurrentRole(processInfoCmd.getRole());
        subReport.setUpdateTime(new Date());
        subReportRepository.updateById(subReport);
    }

    /**
     * 更新子报告流程是否结束标志
     * @param processInfoCmd
     */
    public void updateProcessEnd(SubReportUpdateProcessInfoCmd processInfoCmd){
        SubReport subReport = subReportRepository.getByProcessId(processInfoCmd.getProcessInstanceId());
        if(subReport == null){
            log.warn("子报告不存在,processId:{}", processInfoCmd.getProcessInstanceId());
            throw new BusinessException("404", "子报告不存在");
        }
        subReport.setTaskId(processInfoCmd.getTaskId());
        subReport.setProcessEnd(1);
        subReport.setUpdateTime(new Date());
        subReportRepository.updateById(subReport);
    }
}
