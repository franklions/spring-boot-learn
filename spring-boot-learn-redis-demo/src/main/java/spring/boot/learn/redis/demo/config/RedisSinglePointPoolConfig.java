package spring.boot.learn.redis.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/1
 * @since Jdk 1.8
 */
@Configuration
public class RedisSinglePointPoolConfig {
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(){
        JedisPoolConfig jpc=new JedisPoolConfig();
        jpc.setMaxTotal(10);
        jpc.setMaxIdle(10);
        jpc.setMaxWaitMillis(1*1000);
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory(jpc);
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setHostName("127.0.0.1");
        return jedisConnectionFactory;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate(){
        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate(getJedisConnectionFactory());
        return stringRedisTemplate;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }
}
