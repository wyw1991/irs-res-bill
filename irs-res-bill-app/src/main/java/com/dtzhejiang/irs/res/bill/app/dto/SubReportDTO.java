package com.dtzhejiang.irs.res.bill.app.dto;

import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
public class SubReportDTO {
    /**
     * 总数
     */
    private Integer totalNum;
    /**
     * 问题数
     */
    private Long failNum;

    /**
     * 每一类的子报告ID(每次提交审批生成)
     */
    private List<SubReport> subReport;

    public Integer getTotalNum() {
        if(!CollectionUtils.isEmpty(subReport)){
            return subReport.size();
        }
        return 0;
    }

    public Long getFailNum() {
        if(!CollectionUtils.isEmpty(subReport)){
            return subReport.stream().filter(f->!f.getOperationResultsStatus().equals(OperationResultsStatusEnum.SUCCESS)).count();
        }
        return 0L;
    }
}