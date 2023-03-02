package com.dtzhejiang.irs.res.bill.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.infra.mapper.AppInfoMapper;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import org.springframework.stereotype.Repository;


@Repository
public class AppInfoRepository extends ServiceImpl<AppInfoMapper, AppInfo> {


}
