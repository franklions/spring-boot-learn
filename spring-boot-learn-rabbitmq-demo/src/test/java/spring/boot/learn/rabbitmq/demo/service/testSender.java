package spring.boot.learn.rabbitmq.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/9/4
 * @since Jdk 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testSender {
    @Autowired
    Sender sender;

    @Test
    public void testSend1(){
        sender.send1();
    }

    @Test
    public void testSend2(){
        sender.send2();
    }
}
