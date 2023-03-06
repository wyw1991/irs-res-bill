package com.dtzhejiang.irs.res.bill.adapter.infrastructure.infrastructure.domain.web;

import com.dtzhejiang.irs.res.bill.app.job.AppInfoSyncTask;
import com.dtzhejiang.irs.res.bill.app.service.ReportService;
import com.dtzhejiang.irs.res.bill.common.enums.ApplicationStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.FieldEnum;
import com.dtzhejiang.irs.res.bill.common.enums.StatusEnum;
import com.dtzhejiang.irs.res.bill.common.util.JsonUtil;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@Transactional
public class ReportTest {
    @Autowired
    private ReportService reportService;
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
}
