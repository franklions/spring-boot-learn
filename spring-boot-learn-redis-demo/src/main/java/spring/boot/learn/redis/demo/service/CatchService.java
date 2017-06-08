package spring.boot.learn.redis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CatchService {

    private StringRedisTemplate redisTemplate;


    private String name ;
    public CatchService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.name = UUID.randomUUID().toString();
    }

    public void setCatch(){
        redisTemplate.opsForValue().set("test","test");
        redisTemplate.convertAndSend("topicQueue","messageinfo");
    }

    public void showName() {
        System.out.println(">>>>>>>>>>>>>>>>>>"+ this.name);
    }
}
