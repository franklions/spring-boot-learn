package spring.boot.learn.redis.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    /**
     * 使用Springboot的默认配置
     */
    @Autowired
    @Qualifier("redisTemplate")
     RedisTemplate localRedisTemplate;

    /**
     * 为了使用spring默认配置不能将自己的Factory注册成bean
     * @return
     */
    public JedisConnectionFactory getJedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setHostName("192.168.12.76");
        return jedisConnectionFactory;
    }

    /**
     * 自定义的StringRedisTemplate
     * @return
     */
    @Bean
    public StringRedisTemplate getStringRedisTemplate(){
        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate(getJedisConnectionFactory());
        return stringRedisTemplate;
    }

    /**
     * 自定义的RedisTemplateBean
     * @return
     */
    @Bean(name="getRedisTemplate")
    public RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }
}
