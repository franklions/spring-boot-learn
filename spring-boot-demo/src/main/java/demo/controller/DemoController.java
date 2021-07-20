package demo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/5/26
 * @since Jdk 1.8
 */
@RestController
public class DemoController {

    @RequestMapping("/api/currentUser")
    public String test(){
        System.out.println("getuser");
        return "SUCCESS";
    }
}
