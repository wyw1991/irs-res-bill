package com.dtzhejiang.irs.res.bill.app.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dtzhejiang.irs.res.bill.common.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class HisIndicesDTO {


    private Long id;

    /**
     * 备注
     */
    private String remark;


}