package com.dtzhejiang.irs.res.bill.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dtzhejiang.irs.res.bill.domain.model.Report;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.model.SubReportExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SubReportMapper extends BaseMapper<Report> {

}