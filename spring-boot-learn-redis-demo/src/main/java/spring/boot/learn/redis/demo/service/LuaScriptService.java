package spring.boot.learn.redis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.Collections;

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
        System.out.println( redisScript.getSha1());
        return redisScript;
    }

    public Boolean checkAndSet(String expectedValue, String newValue) {
        return (Boolean)redisTemplate.execute(checkAndSetScript(),
                Collections.singletonList("appid_fakesmarthub001"), expectedValue, newValue);
    }
}
