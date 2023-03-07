package com.dtzhejiang.irs.res.bill.infra.gateway;

import com.dtzhejiang.irs.res.bill.common.constants.RedisKey;
import com.dtzhejiang.irs.res.bill.common.util.DateUtil;
import com.dtzhejiang.irs.res.bill.domain.subreport.SubReportNoGateway;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class SubReportNoGatewayImpl implements SubReportNoGateway {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getSubReportNo() {
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        RedisAtomicLong atomicLong = new RedisAtomicLong(RedisKey.SUB_REPORT_SEQUENCE_ID_KEY, redisTemplate.getConnectionFactory());
        atomicLong.expireAt(DateUtil.toDate(todayEnd));
        long generateId = atomicLong.addAndGet(1);
        // yyyyMMdd + 6位自增编码，支持1-999999
        return "SR-" + DateUtil.format(todayEnd, DateUtil.DEFAULT_DATE_PATTERN_WITHOUT_HYPHEN) + StringUtils.leftPad(String.valueOf(generateId), 6, "0");
    }
}
