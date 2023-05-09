package com.dtzhejiang.irs.res.bill.adapter.infrastructure.infrastructure.domain.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtzhejiang.irs.res.bill.app.dto.AppInfoDTO;
import com.dtzhejiang.irs.res.bill.app.job.AppInfoSyncTask;
import com.dtzhejiang.irs.res.bill.app.query.qry.DemandPageQry;
import com.dtzhejiang.irs.res.bill.app.service.DemandService;
import com.dtzhejiang.irs.res.bill.app.service.ReportService;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.enums.ApplicationStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.StatusEnum;
import com.dtzhejiang.irs.res.bill.common.util.JsonUtil;
import com.dtzhejiang.irs.res.bill.domain.model.Demand;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.irs.res.bill.infra.mapper.DemandMapper;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class DemandTest {
    @Autowired
    private DemandService demandService;
    @Autowired
    private DemandMapper    mapper;

    @Test
    public void testList(){
        DemandPageQry pageQry=new DemandPageQry();
        pageQry.setMyAudit(false);
        pageQry.setMyOwn(true);
        Set<String> roleCodes=new HashSet<>();
        roleCodes.add("a");
        roleCodes.add("b");
        roleCodes.add("c");

        LambdaQueryWrapper<Demand> wrapper = new LambdaQueryWrapper<>();
        UserInfo userInfo =UserInfo.builder().userName("2").roleCodes(roleCodes).addressCode("330001").build();
        if (Boolean.TRUE.equals(pageQry.getMyOwn())) {
            //我发起的
            wrapper.eq(Demand::getUserId, userInfo.getUserName());
        }else  if (Boolean.TRUE.equals(pageQry.getMyAudit())) {
            //已审核列表
            wrapper.apply("FIND_IN_SET ('"+userInfo.getUserName()+"',history_handler)");
        } else {
            //待审核
            wrapper.in(Demand::getCurrentRole, userInfo.getRoleCodes());
            //区划码为市级能看到区县的
            String regionCode = userInfo.getAddressCode();
            if(regionCode.startsWith("00", 4)){
                if(regionCode.startsWith("00", 2)){
                    wrapper.likeRight(Demand::getRegionCode, regionCode.substring(0,2));
                }else {
                    wrapper.likeRight(Demand::getRegionCode, regionCode.substring(0,4));
                }
            }else {
                wrapper.eq(Demand::getRegionCode, regionCode);
            }
        }
        wrapper.like(!ObjectUtils.isEmpty(pageQry.getName()), Demand::getName,pageQry.getName());
        wrapper.like(!ObjectUtils.isEmpty(pageQry.getDescribe()), Demand::getDescription,pageQry.getDescribe());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getType()), Demand::getType,pageQry.getType());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getStatus()), Demand::getStatus,pageQry.getStatus());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getOrgCode()), Demand::getOrgCode,pageQry.getOrgCode());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getStartTime()), Demand::getCreateTime,pageQry.getStartTime());
        wrapper.orderBy(true,false, Demand::getId);//按照ID倒序
        List<Demand> list = mapper.selectList(wrapper);

        System.out.println(list);
    }
}
