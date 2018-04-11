package spring.boot.learn.redis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/4/20
 * @since Jdk 1.8
 */
@Component
public class CatchService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate localRedisTemplate;

    private String name ;
    public CatchService() {
        this.name = UUID.randomUUID().toString();
    }

    public void setCatch(){
        stringRedisTemplate.opsForValue().set("test","test");
        stringRedisTemplate.convertAndSend("topicQueue","messageinfo");
        localRedisTemplate.opsForValue().set("abc","abc");
    }

    public void showName() {
        System.out.println(">>>>>>>>>>>>>>>>>>"+ this.name);
    }
}
