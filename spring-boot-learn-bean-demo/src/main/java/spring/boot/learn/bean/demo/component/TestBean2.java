package spring.boot.learn.bean.demo.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/24
 * @since Jdk 1.8
 */
public class TestBean2 {

    private TestBean1 testBean1;

    public TestBean2( TestBean1 testBean1) {
            this.testBean1 = testBean1;
    }

    public void print(){
        this.testBean1.print();
    }
}
