package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseDO {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    protected Long id;
    /**
     * 创建时间
     */
    protected Date createTime;
    /**
     * 更新时间
     */
    protected Date updateTime;

}
