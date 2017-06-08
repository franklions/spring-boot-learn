package spring.boot.learn.redis.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/1
 * @since Jdk 1.8
 */
@Configuration
public class RedisClusterConfig {
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(){
        Set<RedisNode> nodes = new LinkedHashSet<RedisNode>();
        nodes.add(new RedisNode("127.0.0.1", 1001));
        nodes.add(new RedisNode("127.0.0.1", 1002));
        nodes.add(new RedisNode("127.0.0.1", 1003));
        nodes.add(new RedisNode("127.0.0.1", 1004));
        nodes.add(new RedisNode("127.0.0.1", 1005));
        nodes.add(new RedisNode("127.0.0.1", 1006));
        RedisClusterConfiguration rcc=new RedisClusterConfiguration();
        rcc.setClusterNodes(nodes);
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory(rcc);

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
