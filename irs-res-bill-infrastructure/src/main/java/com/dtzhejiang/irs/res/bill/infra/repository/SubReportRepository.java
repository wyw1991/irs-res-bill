package com.dtzhejiang.irs.res.bill.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import org.springframework.stereotype.Repository;


@Repository
public class SubReportRepository extends ServiceImpl<SubReportMapper, SubReport> {

    /**
     * 根据审核编号查询子报告
     * @param processId
     * @return
     */
    public SubReport getByApprovalId(String processId){
        LambdaQueryWrapper<SubReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubReport::getApprovalId, processId);
        return getOne(queryWrapper);
    }
}
