package spring.boot.learn.redis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/3/7
 * @since Jdk 1.8
 */
@Component
public class LuaScriptService {


    @Autowired
    StringRedisTemplate redisTemplate;


    private RedisScript<Boolean> checkAndSetScript(){
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>();
        redisScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("lua-scripts/checkandset.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    public Boolean checkAndSet(String expectedValue, String newValue) {
        return (Boolean)redisTemplate.execute(checkAndSetScript(),
                Collections.singletonList("appid_fakesmarthub001"), expectedValue, newValue);
    }

    private RedisScript<ArrayList> rateLimitApiCallScript(){
        DefaultRedisScript<ArrayList> redisScript = new DefaultRedisScript<ArrayList>();
        redisScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("lua-scripts/rate_limit_api_call.lua")));
        redisScript.setResultType(ArrayList.class);
        return redisScript;
    }

    public void handleExpiration(String key){
       ArrayList<Integer> retval =  redisTemplate.execute(rateLimitApiCallScript(),
                Collections.singletonList(key), "60","10");
    }

    public  String getUUID(){
       return  UUID.randomUUID().toString().replaceAll("-","");
    }

    public void handleExpirationByCommand(String key) {
        Long current = redisTemplate.boundValueOps(key).increment(1L);
        Long remTime = redisTemplate.getExpire(key);
        if(remTime ==null || remTime <1) {
            redisTemplate.expire(key,60, TimeUnit.SECONDS);
        }
        Long retval =Math.max(-1,10 -current);
    }
}
