package com.dtzhejiang.irs.res.bill.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import org.springframework.stereotype.Repository;


@Repository
public class ReportRepository extends ServiceImpl<ReportMapper, Report> {


}
