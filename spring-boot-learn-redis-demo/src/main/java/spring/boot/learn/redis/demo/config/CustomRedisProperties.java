package spring.boot.learn.redis.demo.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/5/27
 * @since Jdk 1.8
 */
@ConfigurationProperties("custom.redis")
public class CustomRedisProperties  {
    private JedisPoolConfig pool = new JedisPoolConfig() ;
    private RedisProperties properties = new RedisProperties();

    public JedisPoolConfig getPool() {
        return pool;
    }

    public void setPool(JedisPoolConfig pool) {
        this.pool = pool;
    }

    public RedisProperties getProperties() {
        return properties;
    }

    public void setProperties(RedisProperties properties) {
        this.properties = properties;
    }
}
