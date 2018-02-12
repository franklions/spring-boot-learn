package spring.boot.learn.protobuf.demo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/2/9
 * @since Jdk 1.8
 */
@Component
public class Test {
    /**
     * 定时执行任务
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void cronProcess(){
        System.out.println("测试执行时间: "+ new Date(System.currentTimeMillis()));
    }

}
