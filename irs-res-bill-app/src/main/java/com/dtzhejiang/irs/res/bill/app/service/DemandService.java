package com.dtzhejiang.irs.res.bill.app.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtzhejiang.irs.res.bill.app.dto.*;
import com.dtzhejiang.irs.res.bill.app.query.qry.DemandPageQry;
import com.dtzhejiang.irs.res.bill.app.query.qry.ReportPageQry;
import com.dtzhejiang.irs.res.bill.app.query.qry.SubReportQry;
import com.dtzhejiang.irs.res.bill.common.dto.PageQuery;
import com.dtzhejiang.irs.res.bill.common.dto.PageResponse;
import com.dtzhejiang.irs.res.bill.common.enums.*;
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
        switch (pageQry.getTableType()){
            case 1:  //我发起的
                wrapper.eq(Demand::getUserId, userInfo.getUserName());
                break;
            case 2 : //待审核
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
                break;
            case 3 : //我审核的
                wrapper.apply("FIND_IN_SET ('"+userInfo.getUserName()+"',history_handler)");
                break;
            case 4 ://需求池
                wrapper.eq(Demand::getStatus, DemandStatusEnum.SUCCESS);
                break;
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
     *  获取未关联指标的需求列表
     */
    public Map<Long,String> getUnBandQuotaList(){
        LambdaQueryWrapper<Demand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Demand::getStatus, DemandStatusEnum.SUCCESS);
        wrapper.eq(Demand::getQuotaId, null);
        List<Demand> demands = mapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(demands)){
            return Collections.emptyMap();
        }
        return demands.stream().collect(Collectors.toMap(Demand::getId,Demand::getName));
    }

    /**
     * 查询需求详情
     * @return
     */
    public DemandDTO getDetail(DemandPageQry pageQry){
        Demand demand = mapper.selectById(pageQry.getId());
        if (demand == null) {
            throw new BusinessException("demandId 有误");
        }
        DemandDTO demandDTO = new DemandDTO();
        BeanUtils.copyProperties(demand,demandDTO);
        //放入审批按钮信息
        try {
            Operation operation= processService.getCurrentOperation(demand.getProcessId()).getData();
            if (operation != null && !operation.getOptions().iterator().next().getRefFormKey().equals("submit_btn")) {
                demandDTO.setCanOperate(true);
                demandDTO.setOperationDTO(operation);
            }
        }catch (Exception e) {
        }
        return demandDTO;
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
