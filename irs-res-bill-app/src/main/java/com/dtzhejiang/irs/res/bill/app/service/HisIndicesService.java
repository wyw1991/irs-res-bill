package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportDTO;
import com.dtzhejiang.irs.res.bill.app.dto.SubReportFailDTO;
import com.dtzhejiang.irs.res.bill.app.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.common.util.ObjUtil;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.IndexConfig;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.infra.mapper.HisIndicesMapper;
import com.dtzhejiang.irs.res.bill.infra.mapper.SubReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class HisIndicesService {

    @Autowired
    private HisIndicesMapper mapper;
    @Autowired
    private IndexConfigService indexConfigService;
    public List<HisIndices> getList(Long subReportId,Boolean success){
        if(subReportId==null){
            return new ArrayList<>();
        }
        LambdaQueryWrapper<HisIndices> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(subReportId), HisIndices::getSubReportId,subReportId);
        wrapper.ne(!success, HisIndices::getOperationResultsStatus, OperationResultsStatusEnum.SUCCESS);
        wrapper.orderBy(true,true, HisIndices::getId);//按照类型正序
        return mapper.selectList(wrapper);

    }

    public void creatHisIndices(List<SubReport> subReportList, AppInfo appInfo){
        List<IndexConfig> listConfig=indexConfigService.getList();
        listConfig.forEach(f->{
            HisIndices hisIndices=new HisIndices();
            String fileName=f.getIndexCode();
            Object obj= ObjUtil.getValue(appInfo,fileName);
            hisIndices.setOperationIndices(f.getIndexName());
            hisIndices.setNormalValue(f.getNormalValue());
            hisIndices.setOperationData(obj+"");
            setResult(obj,f.getCheckRule(),hisIndices);
        });



    }


    public  void setResult(Object obj,String elStr,HisIndices hisIndices){

        Object result= ObjUtil.getElResult(obj,elStr);
        if (result != null) {

        }

    }

    
}
