package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtzhejiang.irs.res.bill.app.dto.ReportDTO;
import com.dtzhejiang.irs.res.bill.app.query.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.enums.*;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import com.dtzhejiang.irs.res.bill.infra.repository.ReportRepository;
import com.dtzhejiang.irs.res.bill.infra.util.PageUtilPlus;
import com.dtzhejiang.irs.res.bill.app.query.qry.ReportPageQry;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class ReportService {

    @Autowired
    private ReportMapper mapper;
    @Autowired
    private PageUtilPlus pageUtil;
    @Autowired
    private SubReportService subReportService;
    @Autowired
    private UserGateway userGateway;
    @Autowired
    private  ReportRepository reportRepository;
    public PageResponse<Report> page(ReportPageQry pageQry){
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        UserInfo user = userGateway.getCurrentUser();
        //获取子报告对应的主报告ID
        List<Long> reportIdList=subReportService.getReportIdList(pageQry.getBillPermission(),pageQry.getMyAudit());
        //应用管理员列表特殊处理
        if(!ObjectUtils.isEmpty(pageQry.getBillPermission())&& pageQry.getBillPermission() == BillPermissionEnum.generate ){
            wrapper.eq(Report::getAppAdminId, user.getUserName());
            if (Boolean.FALSE.equals(pageQry.getMyAudit())) {
                //待审核列表
                wrapper.in(Report::getStatus,Arrays.asList(StatusEnum.UN_INIT,StatusEnum.INIT));
            }else {
                //已审核列表
                wrapper.notIn(Report::getStatus,Arrays.asList(StatusEnum.UN_INIT,StatusEnum.INIT));
            }
        }else {
            wrapper.in(Report::getId, reportIdList);
        }
        wrapper.like(!ObjectUtils.isEmpty(pageQry.getKeyword()), Report::getName,pageQry.getKeyword());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getType()), Report::getType,pageQry.getType());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getStatus()), Report::getStatus,pageQry.getStatus());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getLevel()), Report::getLevel,pageQry.getLevel());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getApplicationStatus()), Report::getApplicationStatus,pageQry.getApplicationStatus());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getLinkProject()), Report::isLinkProject,pageQry.getLinkProject());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getField()), Report::getField,pageQry.getField());
        wrapper.eq(Report::isNewReport,true);//以最新一条为准
        //wrapper.orderBy(true,false, Report::getId);//按照ID倒序
        wrapper.groupBy(Report::getApplicationId);
        Page<Report> queryPage = new Page<>(pageQry.getPageIndex(),pageQry.getPageSize());
        Page<Report> page = mapper.selectPage(queryPage, wrapper);
        return PageResponse.of(page.getRecords(),page.getTotal(), page.getSize(), page.getCurrent());
    }

    public List<Report> getList(String applicationId){
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getApplicationId,applicationId);
        List<Report> list=mapper.selectList(wrapper);
        list.forEach(f->f.setFailNum(subReportService.failList(new SubReportQry(f.getId())).getFailNum()));
        return list;
    }

    /**
     * 查询报告详情
     * @return
     */
    public ReportDTO getDetail(Long reportId){
        ReportDTO detail=new ReportDTO();
        Report report=getReport(reportId);
        BeanUtils.copyProperties(report,detail);
        List<SubReport> list=subReportService.getList(reportId);
        detail.setTypeList(list.stream().map(SubReport::getSubType).collect(Collectors.toList()));
        Set<SubStatusEnum> set=list.stream().map(SubReport::getSubStatus).collect(Collectors.toSet());
        //所有报告权限一致且在
        if (!CollectionUtils.isEmpty(set) &&set.size() == 1 && SubStatusEnum.unifyList.contains(set.iterator().next())) {
            detail.setCanOperate(true);
        }
        return detail;
    }


    /**
     * 生成报告、重新生成
     */
    public void generateReport(Long reportId){
        Report report=getReport(reportId);
        if (report == null) {
            throw new BusinessException("reportId 有误");
        }

        //首次手动生成报告
        if(ObjectUtils.isEmpty(report.getVersion())){
            report.setVersion("1.0");
            report.setCreateTime(new Date());
            saveOrUpdate(report);
            subReportService.createSubReport(reportId);
        }else if(StatusEnum.FAIL.equals(report.getStatus())) {
            //将此份报告更改为旧报告
            report.setNewReport(false);
            saveOrUpdate(report);
            //拒绝后重新生成报告
            report.setId(null);
            report.setStatus(StatusEnum.INIT);
            int newVersion=Integer.parseInt(report.getVersion().replace(".0",""))+1;
            report.setVersion(newVersion+".0");
            report.setNewReport(true);
            saveOrUpdate(report);
            subReportService.reSubmit(report,reportId);
        }else {
            throw new BusinessException("报告状态是"+report.getStatus().getName()+",不能生成！");
        }
        saveOrUpdate(report);
    }


    /**
     * 保存报告返回ID
     * @param entity
     * @return
     */
    public Report save(Report entity){
        if(ObjectUtils.isEmpty(entity.getId())) {
            //查询符合条件更新的数据放入ID
            LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Report::getApplicationId, entity.getApplicationId());
            wrapper.eq(Report::isNewReport, true);
            Report oldReport = mapper.selectOne(wrapper);
            if (oldReport != null) {
                //已出具/失败 的数据不更新
                if (Arrays.asList(StatusEnum.FAIL,StatusEnum.SUCCESS).contains(oldReport.getStatus())) {
                    return null;
                }
                entity.setId(oldReport.getId());
                entity.setVersion(oldReport.getVersion());
                entity.setStatus(oldReport.getStatus());
                saveOrUpdate(entity);
                subReportService.createSubReport(entity.getId());
            }else if (entity.isLinkProject() && entity.getApplicationStatus().equals(ApplicationStatusEnum.TEST_RUN)) {
                //关联项目且 试运行的数据自动新增
                entity.setStatus(StatusEnum.PROCESS);
                entity.setVersion("1.0");//新数据默认为1.0
                saveOrUpdate(entity);
                subReportService.createSubReport(entity.getId());
            }
        }
        return saveOrUpdate(entity);
    }

    public Report  saveOrUpdate(Report report){
        reportRepository.saveOrUpdate(report);
        return report;
    }

    public Report getReport(Long reportId){
        return reportRepository.getById(reportId);
    }

}
