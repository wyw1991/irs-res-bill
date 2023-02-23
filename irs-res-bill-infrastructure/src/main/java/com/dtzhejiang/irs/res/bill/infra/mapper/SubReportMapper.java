package com.dtzhejiang.irs.res.bill.infra.mapper;

import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.model.SubReportExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SubReportMapper {
    long countByExample(SubReportExample example);

    int deleteByExample(SubReportExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SubReport record);

    int insertSelective(SubReport record);

    List<SubReport> selectByExample(SubReportExample example);

    SubReport selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SubReport record, @Param("example") SubReportExample example);

    int updateByExample(@Param("record") SubReport record, @Param("example") SubReportExample example);

    int updateByPrimaryKeySelective(SubReport record);

    int updateByPrimaryKey(SubReport record);
}