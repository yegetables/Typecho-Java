package com.yegetables.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@PropertySource(value = "classpath:config.properties")
public class RedisConfig {

    @Value("${redis.host}")
    private String hostName;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.pool.maxIdle}")
    private int maxIdle;

    @Value("${redis.pool.minIdle}")
    private int minIdle;

    @Value("${redis.pool.maxWaitMillis}")
    private int maxWaitMillis;

    @Value("${redis.pool.maxTotal}")
    private int maxTotal;

    //    @Bean
    //    public JedisConnectionFactory redisConnectionFactory() {
    //
    //        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(hostName, port);
    //        config.setPassword(password);
    //
    //        GenericObjectPoolConfig genericObjectpoolConfig = new GenericObjectPoolConfig();
    //        genericObjectpoolConfig.setMaxIdle(maxIdle);
    //        genericObjectpoolConfig.setMinIdle(minIdle);
    //        genericObjectpoolConfig.setMaxWait(Duration.ofSeconds(3));
    //        genericObjectpoolConfig.setMaxTotal(maxTotal);
    //        genericObjectpoolConfig.setMinIdle(minIdle);
    //
    //        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().poolConfig(genericObjectpoolConfig).build();
    //        return new JedisConnectionFactory(config, jedisClientConfiguration);
    //    }
    //

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setMaxTotal(maxTotal);

        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofSeconds(10)).shutdownTimeout(Duration.ZERO).poolConfig(poolConfig).build();

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
        lettuceConnectionFactory.setShareNativeConnection(false);

        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置键（key）的序列化采用StringRedisSerializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        //        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new selfFastJsonRedisSerializer();

        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;

        //        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        //        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteClassName);
        //        fastJsonRedisSerializer.setFastJsonConfig(fastJsonConfig);
        //        // 全局开启AutoType，不建议使用
        //        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        //        // 建议使用这种方式，小范围指定白名单
        //        ParserConfig.getGlobalInstance().addAccept("com.doodl6.");
        //

        //        // 设置值（value）的序列化采用FastJsonRedisSerializer
        //        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        //        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

    }

}

