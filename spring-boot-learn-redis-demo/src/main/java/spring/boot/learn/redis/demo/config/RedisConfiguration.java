package spring.boot.learn.redis.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPoolConfig;
import spring.boot.learn.redis.demo.service.RedisMessageSubscriber;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/5/26
 * @since Jdk 1.8
 */
@Configuration
@EnableConfigurationProperties(CustomRedisProperties.class)
public class RedisConfiguration {

    @Autowired
    CustomRedisProperties redisProperties;

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
     RedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory(redisProperties.getPool());
        jedisConnectionFactory.setPort(redisProperties.getProperties().getPort());
        jedisConnectionFactory.setHostName(redisProperties.getProperties().getHost());
        return jedisConnectionFactory;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("topicQueue");
    }
}
