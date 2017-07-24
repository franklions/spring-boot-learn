package spring.boot.learn.bean.demo.component;

import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/24
 * @since Jdk 1.8
 */
public class TestBean1 {
    private  String parms;

    public String getParms() {
        return parms;
    }

    public void setParms(String parms) {
        this.parms = parms;
    }

    public TestBean1(String param) {
        this.parms = parms;
    }

    public  void print(){
        System.out.println("testbean1:"+this.parms);
    }

}
