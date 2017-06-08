package spring.boot.learn.redis.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/1
 * @since Jdk 1.8
 */
public class RedisSentinelConfig {
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(){
        Set<String> sentinels=new HashSet<>();
        sentinels.add("127.0.0.1:20001");
        sentinels.add("127.0.0.1:20002");
        sentinels.add("127.0.0.1:20003");
        RedisSentinelConfiguration rsc=new RedisSentinelConfiguration("iotmaster",sentinels);


        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory(rsc);

        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate getStringRedisTemplate(){
        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate(getJedisConnectionFactory());
        return stringRedisTemplate;
    }

    @Bean
    public RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }
}
