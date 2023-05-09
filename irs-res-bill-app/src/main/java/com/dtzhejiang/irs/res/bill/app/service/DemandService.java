package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtzhejiang.irs.res.bill.app.dto.AppAndReportDTO;
import com.dtzhejiang.irs.res.bill.app.dto.AppInfoDTO;
import com.dtzhejiang.irs.res.bill.app.dto.OssDTO;
import com.dtzhejiang.irs.res.bill.app.dto.ReportDTO;
import com.dtzhejiang.irs.res.bill.app.query.qry.DemandPageQry;
import com.dtzhejiang.irs.res.bill.app.query.qry.ReportPageQry;
import com.dtzhejiang.irs.res.bill.app.query.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.enums.ApplicationStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.BillPermissionEnum;
import com.dtzhejiang.irs.res.bill.common.enums.StatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.SubStatusEnum;
import com.dtzhejiang.irs.res.bill.common.util.ObjUtil;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.*;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import com.dtzhejiang.irs.res.bill.infra.mapper.DemandMapper;
import com.dtzhejiang.irs.res.bill.infra.mapper.ReportMapper;
import com.dtzhejiang.irs.res.bill.infra.repository.DemandRepository;
import com.dtzhejiang.irs.res.bill.infra.repository.ReportRepository;
import com.dtzhejiang.irs.res.bill.infra.util.PageUtilPlus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;


@Component
@Lazy
public class DemandService {

    @Autowired
    private DemandMapper mapper;
    @Autowired
    private PageUtilPlus pageUtil;
    @Autowired
    private UserGateway userGateway;
    @Autowired
    private DemandRepository demandRepository;
    @Autowired
    private HisIndicesService hisIndicesService;
    @Autowired
    private ProcessService processService;

    public PageResponse<Demand> page(DemandPageQry pageQry){
        LambdaQueryWrapper<Demand> wrapper = new LambdaQueryWrapper<>();
        UserInfo userInfo = userGateway.getUserInfoAndOrgId();
        if (Boolean.TRUE.equals(pageQry.getMyOwn())) {
            //我发起的
            wrapper.eq(Demand::getUserId, userInfo.getUserName());
        }else  if (Boolean.TRUE.equals(pageQry.getMyAudit())) {
            //已审核列表
             wrapper.apply("FIND_IN_SET ('"+userInfo.getUserName()+"',history_handler)");
        } else {
            //待审核
            wrapper.in(Demand::getCurrentRole, userInfo.getRoleCodes());
            //区划码为区县级能看到自己的，市级能看到区县的,省级能看到所有的
            String regionCode = userInfo.getAddressCode();
            if(regionCode.startsWith("00", 4)){
                if(regionCode.startsWith("00", 2)){
                    wrapper.likeRight(Demand::getRegionCode, regionCode.substring(0,2));
                }else {
                    wrapper.likeRight(Demand::getRegionCode, regionCode.substring(0,4));
                }
            }else {
                wrapper.eq(Demand::getRegionCode, regionCode);
            }
        }
        wrapper.like(!ObjectUtils.isEmpty(pageQry.getName()), Demand::getName,pageQry.getName());
        wrapper.like(!ObjectUtils.isEmpty(pageQry.getDescribe()), Demand::getDescription,pageQry.getDescribe());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getType()), Demand::getType,pageQry.getType());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getStatus()), Demand::getStatus,pageQry.getStatus());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getOrgCode()), Demand::getOrgCode,pageQry.getOrgCode());
        wrapper.eq(!ObjectUtils.isEmpty(pageQry.getStartTime()), Demand::getCreateTime,pageQry.getStartTime());
        wrapper.orderBy(true,false, Demand::getId);//按照ID倒序
        Page<Demand> queryPage = new Page<>(pageQry.getPageIndex(),pageQry.getPageSize());
        Page<Demand> page = mapper.selectPage(queryPage, wrapper);
        return PageResponse.of(page.getRecords(),page.getTotal(), page.getSize(), page.getCurrent());
    }


    /**
     * 查询需求详情
     * @return
     */
    public Demand getDetail(DemandPageQry pageQry){
        Demand detail=new Demand();
        Demand demand = mapper.selectById(pageQry.getId());
        if (demand == null) {
            throw new BusinessException("demandId 有误");
        }
        return detail;
    }

    /**
     * 保存需求
     * @param demand
     * @return
     */
    public Demand  saveOrUpdate(Demand demand){
        demand.setUpdateTime(new Date());
        demandRepository.saveOrUpdate(demand);
        return demand;
    }
    /**
     * 新增需求
     * @param demand
     * @return
     */
    public Demand  insert(Demand demand){
        demand.setId(null);

        saveOrUpdate(demand);
        return demand;
    }

    /**
     * 更新需求
     * @param oldDemandId
     * @param newDemandId
     * @param quotaId
     * @return
     */
    public Boolean updateQuotaId(String oldDemandId,String newDemandId,Long quotaId){
        Demand demand = mapper.selectById(oldDemandId);
        if (demand == null) {
            throw new BusinessException("demandId 有误");
        }
        demand.setQuotaId(quotaId);
        saveOrUpdate(demand);
        Demand newDemand = mapper.selectById(newDemandId);
        if (newDemand == null) {
            throw new BusinessException("demandId 有误");
        }
        if(newDemand.getQuotaId()!=null){
            throw new BusinessException("该需求已经关联指标");
        }
        newDemand.setQuotaId(quotaId);
        saveOrUpdate(newDemand);
        return true;
    }




}
