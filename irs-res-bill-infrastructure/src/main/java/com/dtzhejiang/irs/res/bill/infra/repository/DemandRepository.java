package com.dtzhejiang.irs.res.bill.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dtzhejiang.irs.res.bill.common.enums.StatusEnum;
import com.dtzhejiang.irs.res.bill.domain.model.Demand;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.infra.mapper.DemandMapper;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import org.springframework.stereotype.Repository;


@Repository
public class DemandRepository extends ServiceImpl<DemandMapper, Demand> {


}
