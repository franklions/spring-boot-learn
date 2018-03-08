package spring.boot.learn.redis.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/1
 * @since Jdk 1.8
 */
@Configuration
public class RedisSinglePointConfig {
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setHostName("192.168.12.76");
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
