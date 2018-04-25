package spring.boot.learn.aop.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.learn.aop.demo.aspect.LogEnable;
import spring.boot.learn.aop.demo.aspect.ParamaterIgnore;
import spring.boot.learn.aop.demo.service.AdviceService;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/16
 * @since Jdk 1.8
 */
@RestController
public class ValueController {

    @Autowired
    AdviceService adviceService;

    @LogEnable(moduleName = "数值管理模块",option = "获取取方法")
    @GetMapping(value = "/value/{key}")
    public String getValue(@PathVariable("key") String key, @ParamaterIgnore String result){
        return adviceService.springAdvices("Hello ",key);
    }
}
