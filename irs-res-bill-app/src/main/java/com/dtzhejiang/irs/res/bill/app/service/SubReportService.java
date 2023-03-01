package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportDTO;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    public SubReportDTO getList(SubReportQry qry,Boolean success){
        SubReportDTO dto=new SubReportDTO();
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(qry.getSubType()), SubReport::getSubType,qry.getSubType());
        wrapper.eq(!ObjectUtils.isEmpty(qry.getReportId()), SubReport::getReportId,qry.getReportId());
        wrapper.orderBy(true,true, SubReport::getId);//按照id正序
        List<SubReport> list=mapper.selectList(wrapper);
        SubReport subReport=mapper.selectOne(wrapper);
        dto.setSubReport(subReport);
        dto.setHisIndicesList(indicesService.getList(subReport.getId(),success));
        return dto;

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
    
}
