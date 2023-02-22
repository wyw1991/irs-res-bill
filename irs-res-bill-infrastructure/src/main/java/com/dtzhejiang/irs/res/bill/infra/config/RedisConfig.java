package com.dtzhejiang.irs.res.bill.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 * @Company: 数字浙江
 * @Author: zhangming
 * @Date 2021/6/29 19:35
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        RedisSerializer<Object> valueSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        return template;
    }
}
