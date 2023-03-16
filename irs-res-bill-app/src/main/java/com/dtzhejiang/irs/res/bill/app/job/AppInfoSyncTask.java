package com.dtzhejiang.irs.res.bill.app.job;

import com.dtzhejiang.irs.res.bill.app.service.AppInfoService;
import com.dtzhejiang.irs.res.bill.app.service.IndexConfigService;
import com.dtzhejiang.irs.res.bill.app.service.ReportService;
import com.dtzhejiang.irs.res.bill.app.service.SubReportService;
import com.dtzhejiang.irs.res.bill.common.enums.StatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.common.util.ObjUtil;
import com.dtzhejiang.irs.res.bill.domain.model.*;
import com.dtzhejiang.irs.res.bill.infra.repository.AppInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 大脑组件同步任务
 */
@Slf4j
@Component
public class AppInfoSyncTask {

    @Autowired
    private AppInfoRepository appInfoRepository;

    @Autowired
    private IndexConfigService indexConfigService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private SubReportService subReportService;
    /**
     * 每天凌晨1点执行
     */
    //@Scheduled(cron = "0 0 1 * * ?")
    public void execute() {
      List<AppInfo> list=appInfoRepository.list();
      list.forEach(v->reportService.save(convertToReport(v)));
    }


    private Report convertToReport(AppInfo appInfo){
        Report report= Report.builder().build();
        BeanUtils.copyProperties(appInfo, report);
        report.setId(null);
        report.setNewReport(true);
        report.setCreateTime(null);
        report.setStatus(StatusEnum.UN_INIT);
        return report;
    }




}
