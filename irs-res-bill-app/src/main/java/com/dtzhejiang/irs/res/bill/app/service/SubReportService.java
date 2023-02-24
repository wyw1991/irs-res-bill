package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportDTO;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.qry.ReportPageQry;
import com.dtzhejiang.irs.res.bill.app.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import com.dtzhejiang.irs.res.bill.infra.util.PageUtilPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class SubReportService {

    @Autowired
    private SubReportMapper mapper;

    public SubReportDTO getList(SubReportQry qry){
        SubReportDTO dto=new SubReportDTO();
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(!ObjectUtils.isEmpty(qry.getAllSubReportIds()), SubReport::getSubReportId, Arrays.asList(qry.getAllSubReportIds().split(",")));
        wrapper.eq(!ObjectUtils.isEmpty(qry.getSubType()), SubReport::getSubType,qry.getSubType());
        wrapper.orderBy(true,true, SubReport::getId);//按照类型正序
        List<SubReport> list=mapper.selectList(wrapper);
        dto.setSubReport(list);
        return dto;

    }


    public SubReportFailDTO failList(String allSubReportIds){
        SubReportFailDTO dto = new SubReportFailDTO();
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.in( SubReport::getSubReportId,Arrays.asList(allSubReportIds.split(",")));
        wrapper.ne(SubReport::getOperationResultsStatus, OperationResultsStatusEnum.SUCCESS);
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
    private SubReportDTO convert(List<SubReport> list,SubTypeEnum subType){
        SubReportDTO dto=new SubReportDTO();
        dto.setSubReport(list.stream().filter(f->f.getSubType().equals(subType)).collect(Collectors.toList()));
        return dto;
    }
    
}
