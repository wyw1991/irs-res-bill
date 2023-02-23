package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import com.dtzhejiang.irs.res.bill.infra.util.PageUtilPlus;
import com.dtzhejiang.irs.res.bill.app.qry.ReportPageQry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Component
public class ReportService {

    @Autowired
    private ReportMapper mapper;
    @Autowired
    private PageUtilPlus pageUtil;
    public PageResponse<Report> page(ReportPageQry pageQry){
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(!ObjectUtils.isEmpty(pageQry.getKeyword()), Report::getName,pageQry.getKeyword());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getType()), Report::getType,pageQry.getType());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getStatus()), Report::getStatus,pageQry.getStatus());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getLevel()), Report::getLevel,pageQry.getLevel());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getApplicationStatus()),Report::getApplicationStatus,pageQry.getApplicationStatus());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getLinkProject()),Report::isLinkProject,pageQry.getLinkProject());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getField()),Report::getField,pageQry.getField());

        Page<Report > queryPage = new Page<>(pageQry.getPageIndex(),pageQry.getPageSize());
        Page<Report> page = mapper.selectPage(queryPage, wrapper);
        return PageResponse.of(page.getRecords(),page.getTotal(), page.getSize(), page.getCurrent());

    }







}
