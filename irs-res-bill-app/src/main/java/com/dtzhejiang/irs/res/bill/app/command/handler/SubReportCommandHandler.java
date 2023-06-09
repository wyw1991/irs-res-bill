package com.dtzhejiang.irs.res.bill.app.command.handler;

import com.dtzhejiang.irs.res.bill.app.command.cmd.SubReportUpdateProcessInfoCmd;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.repository.SubReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class SubReportCommandHandler {

    @Autowired
    private SubReportRepository subReportRepository;

    public static final String FINAL_AUDIT = "FINAL_AUDIT";

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
        // 指定分类，更新remark
        if(FINAL_AUDIT.equals(processInfoCmd.getTaskCategory())){
            subReport.setRemark(processInfoCmd.getRemark());
        }
        subReport.setTaskName(processInfoCmd.getTaskName());
        subReport.setCurrentHandler(StringUtils.isNotBlank(processInfoCmd.getAssignee()) ? processInfoCmd.getAssignee() : "");
        subReport.setCurrentRole(StringUtils.isNotBlank(processInfoCmd.getRole()) ? processInfoCmd.getRole() : "");
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
        subReport.setSubStatus(SubStatusEnum.fromCode(processInfoCmd.getStatus()));
        subReport.setTaskId("");
        subReport.setTaskName("");
        subReport.setCurrentHandler("");
        subReport.setCurrentRole("");
        subReport.setProcessEnd(1);
        subReport.setUpdateTime(new Date());
        subReportRepository.updateById(subReport);
    }
}
