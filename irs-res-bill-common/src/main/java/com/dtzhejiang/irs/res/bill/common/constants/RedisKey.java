package com.dtzhejiang.irs.res.bill.common.constants;

/**
 * 缓存key
 * @Company: 数字浙江
 * @Author: zhangming
 * @Date 2023/3/7 19:55
 */
public interface RedisKey {
    /**
     * 缓存key前缀
     */
    String REDIS_KEY_PREFIX = "redis:irs-res-bill:";
    /**
     * 用于子报告编号
     */
    public static final String SUB_REPORT_SEQUENCE_ID_KEY = REDIS_KEY_PREFIX + "subReport:serialNumber";
}
