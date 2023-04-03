package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.common.enums.OperationResultsStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.common.util.ObjUtil;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.IndexConfig;
import com.dtzhejiang.irs.res.bill.infra.mapper.HisIndicesMapper;
import com.dtzhejiang.irs.res.bill.infra.repository.HisIndicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class HisIndicesService {

    @Autowired
    private HisIndicesMapper mapper;

    @Autowired
    private HisIndicesRepository hisIndicesRepository;
    @Autowired
    private IndexConfigService indexConfigService;

    public List<HisIndices> getList(Long subReportId){
        return getList(subReportId,null);
    }
    public List<HisIndices> getList(Long subReportId,Boolean operationSuccess){
        if(subReportId==null){
            return new ArrayList<>();
        }
        LambdaQueryWrapper<HisIndices> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(subReportId), HisIndices::getSubReportId,subReportId);
        wrapper.ne(!ObjectUtils.isEmpty(operationSuccess) && !operationSuccess, HisIndices::getOperationResultsStatus, OperationResultsStatusEnum.SUCCESS);
        wrapper.orderBy(true,true, HisIndices::getId);//按照类型正序
        return mapper.selectList(wrapper);
    }

    public void saveHisIndices(Long subId,SubTypeEnum subType, AppInfo appInfo){
        List<IndexConfig> listConfig=indexConfigService.getList();
        listConfig.stream().filter(v->v.getType().equals(subType)).forEach(f->{
            HisIndices hisIndices=new HisIndices();
            String fileName=f.getIndexCode();
            Object obj= ObjUtil.getValue(appInfo,fileName);
            setResult(obj,f,hisIndices);
            hisIndices.setOperationIndices(f.getIndexName());
            hisIndices.setOperationIndicesCode(f.getIndexCode());
            hisIndices.setNormalValue(f.getNormalValue());
            hisIndices.setIndexStatusType(f.getIndexStatusType());
            if(obj!=null){
               obj=obj+(!"是/否".equals(f.getIndexUnit()) ?f.getIndexUnit():"");
            }else {
                obj="";
            }
            hisIndices.setOperationData(obj+"");
            hisIndices.setSubReportId(subId);
            save(hisIndices);
        });
    }


    public  void setResult(Object obj,IndexConfig config,HisIndices hisIndices){
        Object result= ObjUtil.getElResult(obj,config.getCheckRule(),config.getIndexUnit());
        hisIndices.setOperationResultsStatus(OperationResultsStatusEnum.SUCCESS);
        hisIndices.setOperationResults("/");
        if(ObjectUtils.isEmpty(obj) || ("boolean".equals(config.getIndexType()) && Boolean.FALSE.equals(result))){
            hisIndices.setOperationResults(!ObjectUtils.isEmpty(config.getWarnTips())?config.getWarnTips(): config.getErrorTips());
            hisIndices.setOperationResultsStatus(!ObjectUtils.isEmpty(config.getWarnTips())?OperationResultsStatusEnum.WARN:OperationResultsStatusEnum.ERROR);
        }
    }

    /**
     * 保存子报告返回ID
     * @param entity
     * @return
     */
    public Long save(HisIndices entity){
        if(ObjectUtils.isEmpty(entity.getId())){
            //查询符合条件更新的数据放入ID
            LambdaQueryWrapper<HisIndices> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(HisIndices::getSubReportId,entity.getSubReportId());
            wrapper.eq(HisIndices::getOperationIndices,entity.getOperationIndices());
            HisIndices oldSubReport=mapper.selectOne(wrapper);
            if (oldSubReport != null) {
                entity.setId(oldSubReport.getId());
            }
        }
        saveOrUpdate(entity);
        return entity.getId();
    }

    public Long saveOrUpdate(HisIndices entity){
        entity.setUpdateTime(new Date());
        hisIndicesRepository.saveOrUpdate(entity);
        return entity.getId();
    }
}
