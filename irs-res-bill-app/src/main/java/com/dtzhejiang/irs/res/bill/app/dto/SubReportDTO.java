package com.dtzhejiang.irs.res.bill.app.dto;

import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
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
    private Long failNum=0L;

    /**
     * 子报告信息(每次提交审批生成)
     */
    private SubReport subReport;


    private List<HisIndices> hisIndicesList;

    private OperationDTO operationDTO;
    public Integer getTotalNum() {
        if(!CollectionUtils.isEmpty(hisIndicesList)){
            return hisIndicesList.size();
        }
        return 0;
    }

    public Long getFailNum() {
        if(!CollectionUtils.isEmpty(hisIndicesList)){
            return hisIndicesList.stream().filter(f->!f.getOperationResultsStatus().equals(OperationResultsStatusEnum.SUCCESS)).count();
        }
        return 0L;
    }
}