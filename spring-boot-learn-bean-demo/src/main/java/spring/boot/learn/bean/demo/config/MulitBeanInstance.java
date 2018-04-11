package spring.boot.learn.bean.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import spring.boot.learn.bean.demo.component.TestBean1;
import spring.boot.learn.bean.demo.component.TestBean2;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/24
 * @since Jdk 1.8
 */
@Configuration
public class MulitBeanInstance {

    @Bean("test1")
    @ConditionalOnMissingBean(name="test1")
     TestBean1 testBean1(){
        TestBean1 t1 = new TestBean1("t1");
        t1.setParms("t1");
        return t1;
    }

    @Bean("test1_s")
    @ConditionalOnMissingBean(name="test1_s")
     TestBean1 testBean1_s(){
        TestBean1 t1 = new TestBean1("t1_s");
        t1.setParms("t1_s");
        return t1;
    }

    @Bean
     TestBean2 testBean2(@Qualifier("test1") TestBean1 testBean1){
        TestBean2 t2 = new TestBean2(testBean1);
        return t2;
    }
}
