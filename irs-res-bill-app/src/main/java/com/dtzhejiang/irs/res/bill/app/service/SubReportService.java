package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.command.cmd.StartProcessCmd;
import com.dtzhejiang.irs.res.bill.app.command.cmd.SubReportSingleSubmitCmd;
import com.dtzhejiang.irs.res.bill.app.command.handler.ProcessCommandHandler;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportDTO;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.query.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.enums.*;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.subreport.SubReportNoGateway;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import com.dtzhejiang.irs.res.bill.infra.repository.SubReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class SubReportService {

    @Autowired
    private SubReportMapper mapper;
    @Autowired
    private HisIndicesService indicesService;
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private SubReportNoGateway subReportNoGateway;
    @Autowired
    private ProcessCommandHandler processCommandHandler;
    @Autowired
    private UserGateway userGateway;

    @Autowired
    private SubReportRepository subReportRepository;

    public SubReportDTO getSubReportDTO(SubReportQry qry){
        SubReportDTO dto=new SubReportDTO();
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        if (qry.getReportId()== null) {
            throw new BusinessException("reportId不能为空！");
        }
        wrapper.eq(!ObjectUtils.isEmpty(qry.getSubType()), SubReport::getSubType,qry.getSubType());
        wrapper.eq(!ObjectUtils.isEmpty(qry.getReportId()), SubReport::getReportId,qry.getReportId());
        //默认需要进行权限控制
        UserInfo userInfo=userGateway.getCurrentUser();
        List<SubTypeEnum> typeList=getSubReportPermissionList(userInfo.getRoleCodes());
        wrapper.in(!ObjectUtils.isEmpty(typeList),SubReport::getSubType,typeList);
        wrapper.orderBy(true,true, SubReport::getId);//按照id正序
        SubReport subReport=mapper.selectOne(wrapper);
        dto.setSubReport(subReport);
        dto.setHisIndicesList(indicesService.getList(subReport.getId()));
        return dto;
    }
    public List<SubTypeEnum> getSubReportPermissionList(Set<String> set){
        //根据角色列表查询对应的子报告权限
        //todo -------------------------------------------------
        List<SubTypeEnum> list=new ArrayList<>();
        list.add(SubTypeEnum.BASIC_FACILITIES);
        list.add(SubTypeEnum.BUSINESS_APPLICATION);
        list.add(SubTypeEnum.APPLICATION_SUPPORT);
        list.add(SubTypeEnum.OPERATION);
        list.add(SubTypeEnum.DATA_RESOURCES);
        list.add(SubTypeEnum.NETWORK_SECURITY);
        return list;
    }

    public List<SubReport> getList (SubReportQry qry){
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(qry.getSubType()), SubReport::getSubType,qry.getSubType());
        wrapper.eq(!ObjectUtils.isEmpty(qry.getReportId()), SubReport::getReportId,qry.getReportId());
        //默认需要进行权限控制
        UserInfo userInfo=userGateway.getCurrentUser();
        List<SubTypeEnum> typeList=getSubReportPermissionList(userInfo.getRoleCodes());
        wrapper.in(!ObjectUtils.isEmpty(typeList),SubReport::getSubType,typeList);

        if(qry.getMyAudit()){
            //已审核列表
            wrapper.like(SubReport::getHistoryHandler,"<"+qry.getBillPermission()+">_"+userInfo.getUserName());
        }else{
            //待审核列表
            wrapper.in(SubReport::getCurrentRole,userInfo.getPermissionList(qry.getBillPermission()));
        }
        wrapper.orderBy(true,false, SubReport::getUpdateTime);//按照更新时间倒序
        return mapper.selectList(wrapper);
    }

    public SubReportFailDTO failList(SubReportQry qry){
        SubReportFailDTO dto = new SubReportFailDTO();
        List<SubReport> list=getList(qry);
        dto.setAPPLICATION_SUPPORT(convert(list,SubTypeEnum.APPLICATION_SUPPORT));
        dto.setOPERATION(convert(list,SubTypeEnum.OPERATION));
        dto.setBUSINESS_APPLICATION(convert(list,SubTypeEnum.BASIC_FACILITIES));
        dto.setDATA_RESOURCES(convert(list,SubTypeEnum.DATA_RESOURCES));
        dto.setBUSINESS_APPLICATION(convert(list,SubTypeEnum.BUSINESS_APPLICATION));
        dto.setNETWORK_SECURITY(convert(list,SubTypeEnum.NETWORK_SECURITY));
        return dto;
    }

    /**
     * 查询符合当前角色权限列表的主报告IDlist
     * @param billPermission
     * @param myAudit
     * @return
     */
    public List<Long> getReportIdList(BillPermissionEnum billPermission, Boolean myAudit){
        SubReportQry qry = new SubReportQry();
        qry.setBillPermission(billPermission);
        qry.setMyAudit(myAudit);
        List<SubReport> list=getList(qry);
        List<Long> idList=new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            UserInfo userInfo=userGateway.getCurrentUser();
            List<SubTypeEnum> typeList=getSubReportPermissionList(userInfo.getRoleCodes());
            //在 报告生成，合规确认,合规审核，报告出具 4个列表 需要6个子报告一起操作
            if(Arrays.asList(BillPermissionEnum.valid_confirm,BillPermissionEnum.valid_pass,BillPermissionEnum.pass,BillPermissionEnum.generate).contains(qry.getBillPermission()) ){
                //统计同一个主报告下的子报告数量,过滤出子报告个数为6个的
                Map<Long,Long> map = list.stream().collect(Collectors.groupingBy(SubReport::getReportId,Collectors.counting()));
                idList=map.entrySet().stream().filter(v->v.getValue()==6).map(m->m.getKey()).collect(Collectors.toList());
            }else {
                idList=list.stream().filter(f->typeList.contains(f.getSubType())).map(SubReport::getReportId).distinct().collect(Collectors.toList());
            }
        }
        return idList;
    }

    /**
     * 重新提交
     * @param newReport
     * @param oldReportId
     */
    public void reSubmit(Report newReport,Long oldReportId){
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(oldReportId), SubReport::getReportId,oldReportId);
        wrapper.ne(SubReport::getSubStatus, OperationResultsStatusEnum.SUCCESS);
        List<SubReport> list=mapper.selectList(wrapper);
        //已审核的保持不变
        list.stream().filter(f->SubStatusEnum.APPROVED.equals(f.getSubStatus())).forEach(v->{
            List<HisIndices> hisList= indicesService.getList(v.getId());
            v.setId(null);
            v.setReportId(newReport.getId());
            Long subId=saveOrUpdate(v);//新增一条数据
            hisList.forEach(m->{
                m.setId(null);
                m.setSubReportId(subId);
                indicesService.saveOrUpdate(m);//新增一条数据
            });
        });
        //没有审核通过的根据最新数据生成
        list.stream().filter(f->!SubStatusEnum.APPROVED.equals(f.getSubStatus())).forEach(v->{
            createSubReport(newReport.getId());
        });
    }

    /**
     * 提交流程
     * @param reportId
     */
    public void submitSubReport(Long reportId){
        SubReportQry qry=new SubReportQry();
        qry.setReportId(reportId);
        List<SubReport> list=getList(qry);
        list.forEach(subReport -> {
            StartProcessCmd cmd = StartProcessCmd.builder()
                    .processKey(subReport.getSubType().getCode().toLowerCase(Locale.ROOT) + "-process")
                    .businessKey(subReport.getSubNo())
                    .build();
            processCommandHandler.start(cmd);
        });
    }

    /**
     * 详情页提交
     */
    public void submitSingleSubReport(SubReportSingleSubmitCmd cmd){
        SubReport subReport = subReportRepository.getById(cmd.getSubReportId());
        if(subReport == null){
            throw new BusinessException("404", "子报告不存在");
        }
        processCommandHandler.complete(subReport.getTaskId(), cmd.getVariable());
    }


    /**
     * 创建子报告
     * @param reportId
     */
    public void createSubReport(Long reportId){
        Report report=reportService.getReport(reportId);
        AppInfo info=appInfoService.getAppInfo(report.getApplicationId());
        Arrays.stream(SubTypeEnum.values()).forEach(f->{
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
        reportService.saveOrUpdate(report);
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
        dto.setHisIndicesList(subReport==null?new ArrayList<>():indicesService.getList(subReport.getId(),false));
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
                if (SubStatusEnum.APPROVED.equals(oldSubReport.getSubStatus())) {
                    return null;
                }
                entity.setId(oldSubReport.getId());
            }
        }else {
            entity.setSubStatus(SubStatusEnum.UN_SUBMIT);
        }
        subReportRepository.saveOrUpdate(entity);
        return entity.getId();
    }

    public Long saveOrUpdate(SubReport entity){
        subReportRepository.saveOrUpdate(entity);
        return entity.getId();
    }

}
