package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtzhejiang.irs.res.bill.app.dto.AppInfoDTO;
import com.dtzhejiang.irs.res.bill.app.dto.HisIndicesDTO;
import com.dtzhejiang.irs.res.bill.app.dto.ReportDTO;
import com.dtzhejiang.irs.res.bill.app.query.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.enums.*;
import com.dtzhejiang.irs.res.bill.common.util.ObjUtil;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.User;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import com.dtzhejiang.irs.res.bill.infra.repository.ReportRepository;
import com.dtzhejiang.irs.res.bill.infra.util.PageUtilPlus;
import com.dtzhejiang.irs.res.bill.app.query.qry.ReportPageQry;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;


@Component
@Lazy
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
    @Autowired
    private HisIndicesService hisIndicesService;

    @Autowired
    private  AppInfoService appInfoService;

    @Autowired
    private ProcessService processService;
    public PageResponse<Report> page(ReportPageQry pageQry){
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        //获取子报告对应的主报告ID
        List<Long> reportIdList=subReportService.getReportIdList(pageQry);
        //应用管理员列表特殊处理
        if(!ObjectUtils.isEmpty(pageQry.getBillPermission())&& pageQry.getBillPermission() == BillPermissionEnum.generate ){
            UserInfo userInfo = userGateway.getUserInfo();
            wrapper.eq(Report::getAppAdminId, userInfo.getUserName());
            if (Boolean.FALSE.equals(pageQry.getMyAudit())) {
                //待审核列表
                //wrapper.in(Report::getStatus,Arrays.asList(StatusEnum.UN_INIT,StatusEnum.INIT));
                wrapper.and(e->e.in(Report::getId,reportIdList).or().in(Report::getStatus,Arrays.asList(StatusEnum.UN_INIT,StatusEnum.INIT)));
            }else {
                //已审核列表
                wrapper.notIn(Report::getStatus,Arrays.asList(StatusEnum.UN_INIT,StatusEnum.INIT));
            }
        }else {
            wrapper.in(Report::getId, reportIdList);
            if (Boolean.FALSE.equals(pageQry.getMyAudit())) {
                //待审核列表
                wrapper.eq(Report::getStatus,StatusEnum.PROCESS);
            }
        }
        wrapper.eq(Report::getLevel,"省级");//暂时限制只返回省级数据
        wrapper.like(!ObjectUtils.isEmpty(pageQry.getKeyword()), Report::getName,pageQry.getKeyword());
        wrapper.apply(!ObjectUtils.isEmpty(pageQry.getField()),"FIND_IN_SET ('"+pageQry.getField()+"',field)");
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getType()), Report::getType,pageQry.getType());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getStatus()), Report::getStatus,pageQry.getStatus());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getApplicationStatus()), Report::getApplicationStatus,pageQry.getApplicationStatus());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getLinkProject()), Report::getLinkProject,pageQry.getLinkProject());
        wrapper.eq(Report::isNewReport,true);//以最新一条为准
        //wrapper.orderBy(true,false, Report::getId);//按照ID倒序
        wrapper.groupBy(Report::getApplicationId);
        Page<Report> queryPage = new Page<>(pageQry.getPageIndex(),pageQry.getPageSize());
        Page<Report> page = mapper.selectPage(queryPage, wrapper);
        return PageResponse.of(page.getRecords(),page.getTotal(), page.getSize(), page.getCurrent());
    }

    public List<Report> getList(ReportPageQry pageQry){
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getApplicationId,pageQry.getApplicationId());
        List<Report> list=mapper.selectList(wrapper);
        SubReportQry subReportQry = new SubReportQry();
        BeanUtils.copyProperties(pageQry, subReportQry);
        list.forEach(f-> {
                    subReportQry.setReportId(f.getId());
                    f.setFailNum(subReportService.failList(subReportQry).getFailNum());
                });
        return list;
    }

    /**
     * 查询报告详情
     * @return
     */
    public ReportDTO getDetail(ReportPageQry pageQry){
        ReportDTO detail=new ReportDTO();
        Report report=getReport(pageQry.getReportId());
        BeanUtils.copyProperties(report,detail);
        SubReportQry query = new SubReportQry();
        BeanUtils.copyProperties(pageQry,query);
        if(!report.isNewReport()){
            query.setMyAudit(true);
        }
        //应用管理员不过滤权限
        List<SubReport> list=new ArrayList<>();
        if(BillPermissionEnum.generate.equals(pageQry.getBillPermission())){
            list=subReportService.getList(report.getId());
        }else {
            list=subReportService.getSpecialSubList(query);
        }

        detail.setTypeMap(list.stream().collect(Collectors.toMap(SubReport ::getId,SubReport ::getSubType)));
        Set<SubStatusEnum> set=list.stream().map(SubReport::getSubStatus).collect(Collectors.toSet());
        //所有报告权限一致
        if (!pageQry.getMyAudit() && !CollectionUtils.isEmpty(set) && set.size() == 1 && (SubStatusEnum.unifyList.contains(set.iterator().next()) || SubStatusEnum.UN_RE_SUBMIT.equals(set.iterator().next())) ) {
            //放入审批按钮信息
            try {
                Operation operation= processService.getCurrentOperation(list.iterator().next().getProcessId()).getData();
                if (operation != null && !operation.getOptions().iterator().next().getRefFormKey().equals("submit_btn")) {
                    detail.setCanOperate(true);
                    detail.setOperationDTO(operation);
                }
            }catch (Exception e) {
            }
        }
        return detail;
    }


    /**
     * 生成报告、重新生成
     */
    @Transactional
    public void generateReport(Long reportId){
        Report report=getReport(reportId);
        if (report == null) {
            throw new BusinessException("reportId 有误");
        }
        if (!report.getLinkProject()) {
            throw new BusinessException("没有关联项目不可生成报告！");
        }
        //首次手动生成报告
        if(ObjectUtils.isEmpty(report.getVersion())){
            report.setVersion("1.0");
            report.setCreateTime(new Date());
            report.setStatus(StatusEnum.INIT);
            saveOrUpdate(report);
            subReportService.createSubReportAndHis(reportId);
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
            subReportService.reSubmit(report.getId(),reportId);
        }else {
            throw new BusinessException("报告状态是"+report.getStatus().getName()+",不能生成！");
        }
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
                subReportService.updateSubReport(entity.getId());
            }else if (entity.getLinkProject() && entity.getApplicationStatus().equals(ApplicationStatusEnum.TEST_RUN)) {
                //关联项目且 试运行的数据自动新增
                entity.setStatus(StatusEnum.INIT);
                entity.setVersion("1.0");//新数据默认为1.0
                saveOrUpdate(entity);
                subReportService.createSubReportAndHis(entity.getId());
            }
        }
        return saveOrUpdate(entity);
    }

    public Report  saveOrUpdate(Report report){
        report.setUpdateTime(new Date());
        reportRepository.saveOrUpdate(report);
        return report;
    }

    public Report getReport(Long reportId){
        if (reportId == null) {
            throw new BusinessException("reportId不能为空");
        }
        return reportRepository.getById(reportId);
    }


    public AppInfoDTO getPdf(Long reportId){
        AppInfoDTO dto = new AppInfoDTO();
        Report report=getReport(reportId);
        if (report == null || !report.getStatus().equals(StatusEnum.SUCCESS)) {
            throw new BusinessException("只有审批完成才可以出具");
        }
        List<SubReport> subList = subReportService.getList(reportId);
        AppInfo info = appInfoService.getAppInfo(report.getApplicationId());
        BeanUtils.copyProperties(report, info);
        List<HisIndices> list=new ArrayList<>();
        subList.forEach(f->{
            list.addAll(hisIndicesService.getList(f.getId()));
        });
        list.forEach(f->{
            ObjUtil.setValue(info,f.getOperationIndicesCode(),f.getOperationData());
        });
        dto.setAppInfo(info);
        dto.setMap(subList.stream().collect(Collectors.toMap(SubReport::getSubType,v->Optional.ofNullable(v.getRemark()).orElse(""))));
        return dto;
    }
}
