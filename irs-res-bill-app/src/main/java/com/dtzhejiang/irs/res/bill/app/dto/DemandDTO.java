package com.dtzhejiang.irs.res.bill.app.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dtzhejiang.irs.res.bill.common.enums.DemandStatusEnum;
import com.dtzhejiang.irs.res.bill.common.enums.DemandTypeEnum;
import com.dtzhejiang.irs.res.bill.domain.model.Demand;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.Operation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DemandDTO extends Demand {
    /**
     * 是否可操作 true 是 false 否
     */
    private Boolean canOperate=false;

    private Operation operationDTO;


}