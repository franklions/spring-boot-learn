package spring.boot.learn.aop.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/19
 * @since Jdk 1.8
 */
@RestController
public class TestInterceptorController {

    @GetMapping("/inter")
    public String getInterceptorName(){
        return "Test interceptor";
    }
}
