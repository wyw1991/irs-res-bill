package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.common.enums.SubTypeEnum;
import com.dtzhejiang.irs.res.bill.domain.model.IndexConfig;
import com.dtzhejiang.irs.res.bill.infra.mapper.IndexConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Component
public class IndexConfigService {

    @Autowired
    private IndexConfigMapper mapper;

    public List<IndexConfig> getList(){
        LambdaQueryWrapper<IndexConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IndexConfig::getDeleteFlag,0);
        return mapper.selectList(wrapper);
    }
    public Long getCount(){
        LambdaQueryWrapper<IndexConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IndexConfig::getDeleteFlag,0);
        return mapper.selectCount(wrapper);
    }


    public List<IndexConfig> getTypeList(SubTypeEnum type){
        LambdaQueryWrapper<IndexConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IndexConfig::getDeleteFlag,0);
        wrapper.eq(!ObjectUtils.isEmpty(type),IndexConfig::getType,type);
        return mapper.selectList(wrapper);

    }


    
}
