package com.example.studentmanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // key 用 String 序列化
        template.setKeySerializer(new StringRedisSerializer());

        // value 用 Jackson 序列化
        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.activateDefaultTyping(
                om.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );
        jacksonSerializer.setObjectMapper(om);

        template.setValueSerializer(jacksonSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSerializer);

        template.afterPropertiesSet();
        return template;
    }
}