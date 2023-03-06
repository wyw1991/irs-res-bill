package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.infra.mapper.AppInfoMapper;
import com.dtzhejiang.irs.res.bill.infra.repository.AppInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AppInfoService {

    @Autowired
    private AppInfoMapper mapper;

    @Autowired
    private AppInfoRepository appInfoRepository;
    public AppInfo getAppInfo(String appId){
        if(ObjectUtils.isEmpty(appId)){
            return null;
        }
        LambdaQueryWrapper<AppInfo> wrap=new LambdaQueryWrapper();
        wrap.eq(AppInfo::getApplicationId,appId);
        return mapper.selectOne(wrap);
    }

}
