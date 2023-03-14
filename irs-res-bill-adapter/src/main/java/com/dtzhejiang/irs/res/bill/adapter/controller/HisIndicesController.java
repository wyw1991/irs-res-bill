package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.irs.res.bill.app.dto.HisIndicesDTO;
import com.dtzhejiang.irs.res.bill.app.service.HisIndicesService;
import com.dtzhejiang.irs.res.bill.common.dto.SingleResponse;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 历史参数
 */
@RestController
@RequestMapping("/indices")
public class HisIndicesController {
    @Autowired
    private HisIndicesService hisIndicesService;

    /**
     * 保存接口
     * @param dto 备注
     */
    @PostMapping("/save")
    public SingleResponse save(@RequestBody HisIndicesDTO dto) {
        HisIndices entity=new HisIndices();
        BeanUtils.copyProperties(dto,entity);
        return SingleResponse.of(hisIndicesService.save(entity));
    }

    /**
     * 批量保存接口
     * @param list
     */
    @PostMapping("/saveList")
    public SingleResponse save(@RequestBody List<HisIndicesDTO> list) {
        list.forEach(f->{
            HisIndices entity=new HisIndices();
            BeanUtils.copyProperties(f,entity);
            hisIndicesService.save(entity);
        });
        return SingleResponse.buildSuccess();
    }


}
