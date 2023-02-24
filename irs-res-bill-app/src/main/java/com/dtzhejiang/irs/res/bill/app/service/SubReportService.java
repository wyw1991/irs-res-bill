package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.qry.ReportPageQry;
import com.dtzhejiang.irs.res.bill.app.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import com.dtzhejiang.irs.res.bill.infra.util.PageUtilPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class SubReportService {

    @Autowired
    private SubReportMapper mapper;

    public List<SubReport> getList(SubReportQry qry){
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(!ObjectUtils.isEmpty(qry.getAllSubIds()), SubReport::getId, Arrays.asList(qry.getAllSubIds().split(",")));
        wrapper.eq(!ObjectUtils.isEmpty(qry.getSubType()), SubReport::getSubType,qry.getSubType());
        wrapper.orderBy(true,true, SubReport::getId);//按照类型正序
        return mapper.selectList(wrapper);

    }


    public SubReportFailDTO failList(String allSubIds){
        SubReportFailDTO dto = new SubReportFailDTO();
        LambdaQueryWrapper<SubReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.in( SubReport::getId,Arrays.asList(allSubIds.split(",")));
        wrapper.ne(SubReport::getOperationResultsStatus, OperationResultsStatusEnum.SUCCESS);
        List<SubReport> list=mapper.selectList(wrapper);
        dto.setApplicationSupport(list.stream().filter(f->f.getSubType().equals(SubTypeEnum.APPLICATION_SUPPORT)).collect(Collectors.toList()));
        dto.setOperation(list.stream().filter(f->f.getSubType().equals(SubTypeEnum.OPERATION)).collect(Collectors.toList()));
        dto.setBasicFacilities(list.stream().filter(f->f.getSubType().equals(SubTypeEnum.BASIC_FACILITIES)).collect(Collectors.toList()));
        dto.setDataResources(list.stream().filter(f->f.getSubType().equals(SubTypeEnum.DATA_RESOURCES)).collect(Collectors.toList()));
        dto.setBusinessApplication(list.stream().filter(f->f.getSubType().equals(SubTypeEnum.BUSINESS_APPLICATION)).collect(Collectors.toList()));
        return dto;
    }

    
}
