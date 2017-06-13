package spring.boot.learn.filter.demo.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/13
 * @since Jdk 1.8
 */
@RestController
public class DemoController {

    @PostMapping("/demo/{title}")
    public String demo(@PathVariable String title, @RequestBody Object body){
        return  "<"+title + ">test success!";
    }
}
