package com.dtzhejiang.irs.res.bill.adapter.infrastructure.infrastructure.domain.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.job.AppInfoSyncTask;
import com.dtzhejiang.irs.res.bill.app.service.ReportService;
import com.dtzhejiang.irs.res.bill.common.enums.ApplicationStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.FieldEnum;
import com.dtzhejiang.irs.res.bill.common.enums.StatusEnum;
import com.dtzhejiang.irs.res.bill.common.util.JsonUtil;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.irs.res.bill.infra.gateway.UserGatewayImpl;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class ReportTest {
    @Autowired
    private ReportService reportService;

    @Autowired
    private SubReportMapper subReportMapper;
    @Autowired
    private  AppInfoSyncTask task;
    @Test
    public void testId(){
        Report report=new Report();
        report.setStatus(StatusEnum.PROCESS);
        report.setName("测试组件");
        report.setApplicationId("111");
        report.setApplicationStatus(ApplicationStatusEnum.TEST_RUN);
        report.setField(FieldEnum.CULTURE);
        reportService.saveOrUpdate(report);
        System.out.println(JsonUtil.toJsonString(report));
    }

    @Test
    @Rollback(value = false)
    public void createReport(){
        task.execute();
    }

    @Test
    public void testVersion(){
        String a="1.0";
        int b=Integer.parseInt(a.replace(".0",""));
        System.out.println( b);
    }

    @Test
    public void testApply(){
        String name="common_user";
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.apply("", name);
        System.out.println( );
    }
    @Test
    public void testList(){
        Set<String> roleCodes=new HashSet<>();
        roleCodes.add("a");
        roleCodes.add("b");
        roleCodes.add("c");

        List<String> list= roleCodes.stream().map(s -> "<"+"per"+">-"+s).collect(Collectors.toList());;
        Boolean a=list.contains("<per>-a");
        System.out.println( list.toString());
        System.out.println( a);
    }
}
