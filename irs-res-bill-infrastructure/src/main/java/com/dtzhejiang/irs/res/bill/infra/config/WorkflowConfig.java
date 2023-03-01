package com.dtzhejiang.irs.res.bill.infra.config;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("workflow")
public class WorkflowConfig {

    private String apiUrl;

    private String secretKey;

    private String ak;

    private String sk;

    public String getToken() {
        Long timestamp = System.currentTimeMillis();
        String sign = DigestUtils.md5Hex(String.format("%s.%s", sk, timestamp));
        return String.format("%s.%s.%s", ak, timestamp, sign);
    }

}
