package com.dtzhejiang.irs.res.bill.infra.config;

import com.dtzhejiang.usercenter.client.*;
import com.dtzhejiang.usercenter.client.utils.RpcSignUtils;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.slf4j.Slf4jLogger;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.net.ssl.SSLContext;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * FeignClientConfig
 * 描述：用户中心服务RPC配置
 *
 * @Company: 数字浙江
 * @Author: zhangming
 * @Date 2021/6/21 16:50
 */
@Configuration
@Import(FeignClientsConfiguration.class)
@ConfigurationProperties("feign")
public class FeignClientConfig {

    @Value("${forest.variables.casBaseUrl}")
    private String userClientHost;
    @Value("${user-center.auth.ak}")
    private String appKey;
    @Value("${user-center.auth.sk}")
    private String appScret;

    @Bean
    public RequestInterceptor headerInterceptor() {
        return requestTemplate -> {
            Map<String,String> headers = RpcSignUtils.gererateSignHeaders(appKey,appScret);
            headers.keySet().forEach(key -> requestTemplate.header(key, headers.get(key)));
        };
    }

    @Bean
    public UserClient userClient(Contract contract, Decoder decoder, Encoder encoder) {
        return buildFeignBuilder(contract, decoder, encoder).target(UserClient.class, userClientHost);
    }

    @Bean
    public RoleClient roleClient(Contract contract, Decoder decoder, Encoder encoder) {
        return buildFeignBuilder(contract, decoder, encoder).target(RoleClient.class, userClientHost);
    }

    private Feign.Builder buildFeignBuilder(Contract contract, Decoder decoder, Encoder encoder) {
        Client client;
        try {
            SSLContext context = new SSLContextBuilder()
                    .loadTrustMaterial(null, (chain, authType) -> true)
                    .build();
            client = new Client.Default(context.getSocketFactory(), new NoopHostnameVerifier());
        } catch (Exception e) {
            client = new Client.Default(null, null);
        }
        Request.Options customOptions = new Request.Options(10, TimeUnit.SECONDS, 30, TimeUnit.SECONDS, false);
        return Feign.builder().options(customOptions).client(client).requestInterceptor(headerInterceptor()).contract(contract).encoder(encoder).decoder(decoder).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL);
    }

}

