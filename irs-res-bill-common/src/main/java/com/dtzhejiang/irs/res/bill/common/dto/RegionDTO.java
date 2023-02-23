package com.dtzhejiang.irs.res.bill.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegionDTO implements Serializable {
    private static final long serialVersionUID = -1388656628061776116L;

    /**
     * 区划码
     */
    private String regionCode;
    /**
     * 区划名
     */
    private String regionName;

}
