package com.dtzhejiang.irs.res.bill.adapter.infrastructure.infrastructure.domain.web;

import com.dtzhejiang.irs.res.bill.app.job.AppInfoSyncTask;
import com.dtzhejiang.irs.res.bill.app.service.SubReportService;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@Transactional
public class SubReportTest {
    @Autowired
    private SubReportService reportService;

    @Autowired
    private SubReportMapper subReportMapper;
    @Autowired
    private  AppInfoSyncTask task;
    @Test
    public void testId(){
        Arrays.stream(SubTypeEnum.values()).forEach(f->  System.out.println(f.getName()));
    }
    @Test
    @Rollback(value = false)
    public void testProcess(){
        reportService.submitSubReport(2L);
    }


}
