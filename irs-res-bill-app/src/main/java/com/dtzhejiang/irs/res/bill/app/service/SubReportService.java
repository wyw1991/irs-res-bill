package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.command.cmd.StartProcessCmd;
import com.dtzhejiang.irs.res.bill.app.command.cmd.SubReportSingleSubmitCmd;
import com.dtzhejiang.irs.res.bill.app.command.handler.ProcessCommandHandler;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportDTO;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.query.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import com.dtzhejiang.irs.res.bill.common.enums.*;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessInstance;
import com.dtzhejiang.irs.res.bill.domain.subreport.SubReportNoGateway;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.User;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserRole;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import com.dtzhejiang.irs.res.bill.infra.repository.ReportRepository;
import com.dtzhejiang.irs.res.bill.infra.repository.SubReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Lazy
public class SubReportService {

    @Autowired
    private SubReportMapper mapper;
    @Autowired
    private HisIndicesService indicesService;
    @Autowired
    private IndexConfigService configService;
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private SubReportNoGateway subReportNoGateway;
    @Autowired
    private ProcessCommandHandler processCommandHandler;
    @Autowired
    private UserGateway userGateway;

    @Autowired
    private ProcessService processService;
    @Autowired
    private SubReportRepository subReportRepository;

    public SubReportDTO getSubReportDTO(SubReportQry qry){
        SubReportDTO dto=new SubReportDTO();
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        if (qry.getReportId()== null) {
            throw new BusinessException("reportId不能为空！");
        }
        if(ObjectUtils.isEmpty(qry.getSubType())){
            throw new BusinessException("subType不能为空！");
        }
        wrapper.eq(SubReport::getSubType,qry.getSubType());
        wrapper.eq(SubReport::getReportId,qry.getReportId());
        wrapper.orderBy(true,true, SubReport::getId);//按照id正序
        SubReport subReport=mapper.selectOne(wrapper);
        if(subReport==null){
            throw new BusinessException("参数错误！");
        }
        dto.setSubReport(subReport);
        if(!qry.getMyAudit()){
            try{
                Operation operation= processService.getCurrentOperation(subReport.getProcessId()).getData();
                if (BillPermissionEnum.generate.equals(qry.getBillPermission())&&operation != null && !operation.getOptions().iterator().next().getRefFormKey().equals("submit_btn")) {
                    //待生成列表只有分步重新提交可以展示按钮
                    operation=null;
                }
                dto.setOperationDTO(operation);
            }catch (Exception e) {
            }
        }
        dto.setHisIndicesList(indicesService.getList(subReport.getId()));
        return dto;
    }

    /**
     * 主报告Id过滤子报告列表(特殊节点需要6个子报告状态一致才可统一审批)
     * @param qry
     * @return
     */
    public List<SubReport> getSpecialSubList (SubReportQry qry){
        if (qry.getReportId() == null) {
            throw new BusinessException("reportId 不能为空！");
        }
        //应用管理员不过滤子报告权限
        List<SubReport> list=getList(qry.getReportId());
        if(BillPermissionEnum.audit.equals(qry.getBillPermission()) && !CollectionUtils.isEmpty(list) && Boolean.FALSE.equals(qry.getMyAudit())){
            list=filterSubId(list);
        }
        return list;
    }

    private List<SubReport> filterSubId (List<SubReport> list){
        if(CollectionUtils.isEmpty(list)){
            return new ArrayList<>();
        }
        //统计同一个主报告下的子报告数量,过滤出子报告个数为6个的
        Map<Long,Long> map = list.stream().filter(f->SubStatusEnum.unifyList.contains(f.getSubStatus())).collect(Collectors.groupingBy(SubReport::getReportId,Collectors.counting()));
        List<Long>  removeIdList=map.entrySet().stream().filter(v->v.getValue()+getSuccessNum(v.getKey())!=6).map(Map.Entry::getKey).collect(Collectors.toList());
        List<SubReport>  removeList=list.stream().filter(f->removeIdList.contains(f.getReportId()) && SubStatusEnum.unifyList.contains(f.getSubStatus())).collect(Collectors.toList());
        list.removeAll(removeList);
        return list;
    }

    /**
     * 根据reportID查询6个子报告，不过滤权限
     * @param reportId
     * @return
     */
    public List<SubReport> getList (Long reportId){
        if (reportId == null) {
            throw new BusinessException("reportId 不能为空！");
        }
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq( SubReport::getReportId,reportId);
       return mapper.selectList(wrapper);
    }

    /**
     * 获取成功的子报告数量
     * @param reportId
     * @return
     */
   private Long getSuccessNum(Long reportId){
           if (reportId == null) {
               throw new BusinessException("reportId 不能为空！");
           }
           LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
           wrapper.eq( SubReport::getReportId,reportId);
           wrapper.eq( SubReport::getSubStatus,SubStatusEnum.SUCCESS);
        return mapper.selectCount(wrapper);
    }

    public List<SubReport> getList (SubReportQry qry){
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        UserInfo userInfo = userGateway.getUserInfo();
        if (Boolean.TRUE.equals(qry.getMyAudit())) {
            //已审核列表
            if(BillPermissionEnum.audit.equals(qry.getBillPermission())){
                wrapper.apply("FIND_IN_SET ("+userInfo.getUserName()+",history_handler)");
            }
        } else {
            //应用管理员列表特殊处理
            // 待提交(重新提交特殊处理)
            if(BillPermissionEnum.generate.equals(qry.getBillPermission())  ){
                wrapper.eq(SubReport::getCurrentHandler, userInfo.getUserName()).eq(SubReport::getSubStatus,SubStatusEnum.UN_RE_SUBMIT);
            }else{
                //待审核
                wrapper.in(SubReport::getCurrentRole, userInfo.getRoleCodes());
            }
        }
        wrapper.eq(!ObjectUtils.isEmpty(qry.getSubType()), SubReport::getSubType,qry.getSubType());
        wrapper.eq(!ObjectUtils.isEmpty(qry.getReportId()), SubReport::getReportId,qry.getReportId());
        wrapper.orderBy(true,false, SubReport::getUpdateTime);//按照更新时间倒序
        return mapper.selectList(wrapper);
    }



    public SubReportFailDTO failList(SubReportQry qry){
        Report report= reportRepository.getById(qry.getReportId());
        if(!report.isNewReport() ){
            qry.setMyAudit(true);
        }
        SubReportFailDTO dto = new SubReportFailDTO();
        //应用管理员不过滤子报告权限
        List<SubReport> list=getSpecialSubList(qry);
        dto.setApplication_support(convert(list,SubTypeEnum.APPLICATION_SUPPORT));
        dto.setOperation(convert(list,SubTypeEnum.OPERATION));
        dto.setBasic_facilities(convert(list,SubTypeEnum.BASIC_FACILITIES));
        dto.setData_resources(convert(list,SubTypeEnum.DATA_RESOURCES));
        dto.setBusiness_application(convert(list,SubTypeEnum.BUSINESS_APPLICATION));
        dto.setNetwork_security(convert(list,SubTypeEnum.NETWORK_SECURITY));
        return dto;
    }

    /**
     * 查询符合当前角色权限列表的主报告IDlist
     * @param pageQuery
     * @return
     */
    public List<Long> getReportIdList(PageQuery pageQuery){
        SubReportQry qry = new SubReportQry();
        qry.setBillPermission(pageQuery.getBillPermission());
        qry.setMyAudit(pageQuery.getMyAudit());
        List<SubReport> list=getList(qry);
        //待审核列表特殊处理 在 合规确认,合规审核，报告出具 3个状态 需要6个子报告一起操作
        if (!pageQuery.getMyAudit() && BillPermissionEnum.audit.equals(pageQuery.getBillPermission()) ) {
            //统计同一个主报告下的子报告数量,过滤出子报告个数不为6个的
            filterSubId(list);
        }
        List<Long> idList=list.stream().map(SubReport::getReportId).distinct().collect(Collectors.toList());
        return  CollectionUtils.isEmpty(idList)?Arrays.asList(0L):idList;
    }




    /**
     * 重新提交
     * @param newReportId
     * @param oldReportId
     */
    public void reSubmit(Long newReportId,Long oldReportId){
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(oldReportId), SubReport::getReportId,oldReportId);
        List<SubReport> list=mapper.selectList(wrapper);
        //已审核的保持不变
        list.stream().filter(f->SubStatusEnum.SUCCESS.equals(f.getSubStatus())).forEach(v->{
            List<HisIndices> hisList= indicesService.getList(v.getId());
            v.setId(null);
            v.setReportId(newReportId);
            Long subId=saveOrUpdate(v);//新增一条复制数据
            hisList.forEach(m->{
                m.setId(null);
                m.setSubReportId(subId);
                indicesService.saveOrUpdate(m);//新增一条复制数据
            });
        });
        //没有审核通过的根据最新数据生成
        list.stream().filter(f->!SubStatusEnum.SUCCESS.equals(f.getSubStatus())).forEach(v-> createSubReportAndHis(newReportId,v.getSubType()));
    }

    /**
     * 提交流程O
     * @param reportId
     */
    @Transactional
    public void submitSubReport(Long reportId){
        Report report=reportRepository.getById(reportId);
        if (report == null || report.getStatus()!=StatusEnum.INIT ) {
            throw new BusinessException("当前状态不可提交报告！");
        }
        List<SubReport> list = getList(reportId);
        //审核成功的不需要再次申请
        list.stream().filter(f->f.getSubStatus()!=SubStatusEnum.SUCCESS).forEach(subReport -> {
            StartProcessCmd cmd = StartProcessCmd.builder()
                    .processKey(subReport.getSubType().getCode().toLowerCase(Locale.ROOT) + "-process")
                    .businessKey(subReport.getSubNo())
                    .build();
            ProcessInstance start = processCommandHandler.start(cmd);
            subReport.setProcessId(start.getProcessId());
            subReport.setSubStatus(SubStatusEnum.fromCode(start.getStatus()));
            saveOrUpdate(subReport);
        });
        //更新主报告状态
        reportRepository.updateStatus(reportId,StatusEnum.PROCESS);
    }

    /**
     * 详情页单个提交
     */
    public void apartSubmit(SubReportSingleSubmitCmd cmd){
        SubReport subReport = subReportRepository.getById(cmd.getSubReportId());
        if(subReport == null){
            throw new BusinessException("404", "子报告不存在");
        }
        processCommandHandler.complete(subReport.getTaskId(), cmd.getVariables());
    }


    /**
     * 创建子报告和指标
     * @param reportId
     */
    @Transactional
    public void createSubReportAndHis(Long reportId,SubTypeEnum typeEnum){
        Report report=reportRepository.getById(reportId);
        AppInfo info=appInfoService.getAppInfo(report.getApplicationId());
        SubTypeEnum[] list=typeEnum == null ? SubTypeEnum.values():new SubTypeEnum[] {typeEnum};
        Arrays.stream(list).forEach(f->{
            SubReport subReport=new SubReport();
            subReport.setReportId(reportId);
            // 新增编号
            subReport.setSubNo(subReportNoGateway.getSubReportNo());
            subReport.setSubType(f);
            subReport.setName(f.getName());
            subReport.setSubStatus(SubStatusEnum.UN_SUBMIT);
            save(subReport);
            indicesService.saveHisIndices(subReport.getId(),f,info);
        });
    }



    /**
     * 更新子报告下的指标(同步接口使用)
     * @param reportId
     */
    public void updateSubReport(Long reportId){
        Report report=reportRepository.getById(reportId);
        AppInfo info=appInfoService.getAppInfo(report.getApplicationId());
        List<SubReport> list=getList(reportId);
        list.forEach(f->{
            indicesService.saveHisIndices(f.getId(),f.getSubType(),info);
        });
    }

    /**
     * dto转换增加统计数
     * @param list
     * @param subType
     * @return
     */
    private SubReportDTO convert(List<SubReport> list, SubTypeEnum subType){
        SubReportDTO dto=new SubReportDTO();

        SubReport subReport=list.stream().filter(f->f.getSubType().equals(subType)).findFirst().orElse(null);
        dto.setSubReport(subReport);
        dto.setHisIndicesList(subReport == null ? new ArrayList<>() : indicesService.getList(subReport.getId(), false));
        if (subReport != null) {
            List<HisIndices> allList = indicesService.getList(subReport.getId(), null);
            dto.setTotalNum(CollectionUtils.isEmpty(allList) ? 0 : allList.size());
        }
        return dto;
    }

    /**
     * dtoList转换(增加his数据)
     * @param list
     * @return
     */
    public List<SubReportDTO> convertToList(List<SubReport> list){
        List<SubReportDTO> dtoList=new ArrayList<>();
           list.forEach(f->{
               SubReportDTO dto= new SubReportDTO();
               dto.setSubReport(f);
               dto.setHisIndicesList(indicesService.getList(f.getId()));
               dtoList.add(dto);
           });
        return dtoList;
    }
    /**
     * 保存子报告返回ID
     * @param entity
     * @return
     */
    public Long save(SubReport entity){
        if(ObjectUtils.isEmpty(entity.getId())){
            //查询符合条件更新的数据放入ID
            LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SubReport::getReportId,entity.getReportId());
            wrapper.eq(SubReport::getSubType,entity.getSubType());
            SubReport oldSubReport=mapper.selectOne(wrapper);
            if (oldSubReport != null) {
                //已审核的数据不能修改
                if (SubStatusEnum.isApproved(oldSubReport.getSubStatus())) {
                    return null;
                }
                entity.setId(oldSubReport.getId());
            }
        }
        return saveOrUpdate(entity);
    }
    @Transactional
    public Long saveOrUpdate(SubReport entity){
        if(entity.getId()==null){
            entity.setCreateTime(new Date());
        }
        entity.setUpdateTime(new Date());
        subReportRepository.saveOrUpdate(entity);
        return entity.getId();
    }

}
