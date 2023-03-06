package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportDTO;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.StatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import com.dtzhejiang.irs.res.bill.infra.repository.ReportRepository;
import com.dtzhejiang.irs.res.bill.infra.repository.SubReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private SubReportRepository subReportRepository;

    public SubReportDTO getSubReportDTO(SubReportQry qry){
        SubReportDTO dto=new SubReportDTO();
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(qry.getSubType()), SubReport::getSubType,qry.getSubType());
        wrapper.eq(!ObjectUtils.isEmpty(qry.getReportId()), SubReport::getReportId,qry.getReportId());
        wrapper.orderBy(true,true, SubReport::getId);//按照id正序
        SubReport subReport=mapper.selectOne(wrapper);
        dto.setSubReport(subReport);
        dto.setHisIndicesList(indicesService.getList(subReport.getId()));
        return dto;
    }

    public List<SubReport> getList (Long reportId,String subType){
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(subType), SubReport::getSubType,subType);
        wrapper.eq(!ObjectUtils.isEmpty(reportId), SubReport::getReportId,reportId);
        wrapper.orderBy(true,true, SubReport::getId);//按照id正序
        return mapper.selectList(wrapper);
    }



    public SubReportFailDTO failList(Long reportId){
        SubReportFailDTO dto = new SubReportFailDTO();
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(reportId), SubReport::getReportId,reportId);
        //wrapper.ne(SubReport::getOperationResultsStatus, OperationResultsStatusEnum.SUCCESS);
        List<SubReport> list=mapper.selectList(wrapper);
        dto.setApplicationSupport(convert(list,SubTypeEnum.APPLICATION_SUPPORT));
        dto.setOperation(convert(list,SubTypeEnum.OPERATION));
        dto.setBasicFacilities(convert(list,SubTypeEnum.BASIC_FACILITIES));
        dto.setDataResources(convert(list,SubTypeEnum.DATA_RESOURCES));
        dto.setBusinessApplication(convert(list,SubTypeEnum.BUSINESS_APPLICATION));
        dto.setNetworkSecurity(convert(list,SubTypeEnum.NETWORK_SECURITY));
        return dto;
    }

    /**
     * 重新提交
     * @param newReport
     * @param oldReportId
     */
    public void reSubmit(Report newReport,Long oldReportId){
        AppInfo info=appInfoService.getAppInfo(newReport.getApplicationId());
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
        List<SubReport> list=getList(reportId,null);
        //todo 提交流程
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
            subReport.setSubType(f);
            subReport.setName(f.getName());
            subReport.setSubStatus(SubStatusEnum.UN_SUBMIT);
            save(subReport);
            indicesService.saveHisIndices(subReport.getId(),f,info);
        });
        //更新不合格数
        Long failNum=failList(reportId).getFailNum();
        report.setFailNum(failNum);
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
