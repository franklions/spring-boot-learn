package spring.boot.learn.docker.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/3/1
 * @since Jdk 1.8
 */
@Controller
public class ValueController {

    @ResponseBody
    @RequestMapping(value = "/")
    String home(){
        return "Hello Docker world";
    }
}
