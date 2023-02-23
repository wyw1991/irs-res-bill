package com.dtzhejiang.irs.res.bill.infra.mapper;

import com.dtzhejiang.irs.res.bill.infra.model.Report;
import com.dtzhejiang.irs.res.bill.infra.model.ReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportMapper {
    long countByExample(ReportExample example);

    int deleteByExample(ReportExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Report record);

    int insertSelective(Report record);

    List<Report> selectByExample(ReportExample example);

    Report selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Report record, @Param("example") ReportExample example);

    int updateByExample(@Param("record") Report record, @Param("example") ReportExample example);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);
}