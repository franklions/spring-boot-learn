package spring.boot.learn.aop.demo.service;

import org.springframework.stereotype.Component;
import spring.boot.learn.aop.demo.aspect.LogEnable;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/16
 * @since Jdk 1.8
 */
@Component
public class AdviceService {

    public String manyAdvices(String param1,String param2){
        System.out.println("方法：manyAdvices");
        return param1 +"|"+param2;
    }

    public String springAdvices(String param1,String param2){
        System.out.println("方法：springAdvices");
        return param1 +"*"+param2;
    }
}
