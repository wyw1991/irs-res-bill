package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.domain.model.IndexConfig;
import com.dtzhejiang.irs.res.bill.infra.mapper.IndexConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class IndexConfigService {

    @Autowired
    private IndexConfigMapper mapper;

    public List<IndexConfig> getList(){
        LambdaQueryWrapper<IndexConfig> wrapper = new LambdaQueryWrapper<>();
        return mapper.selectList(wrapper);

    }


    
}
