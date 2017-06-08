package spring.boot.learn.bean.demo.service;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/4/20
 * @since Jdk 1.8
 */
public class CatchService {

    private RedisTemplate redisTemplate;

    private String name ;
    public CatchService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.name = UUID.randomUUID().toString();
    }

    public void setCatch(){
        redisTemplate.opsForValue().set("test","test");
    }

    public void showName() {
        System.out.println(">>>>>>>>>>>>>>>>>>"+ this.name);
    }
}
