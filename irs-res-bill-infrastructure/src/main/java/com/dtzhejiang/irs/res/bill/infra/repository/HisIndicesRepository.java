package com.dtzhejiang.irs.res.bill.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.infra.mapper.AppInfoMapper;
import com.dtzhejiang.irs.res.bill.infra.mapper.HisIndicesMapper;
import org.springframework.stereotype.Repository;


@Repository
public class HisIndicesRepository extends ServiceImpl<HisIndicesMapper, HisIndices> {


}
