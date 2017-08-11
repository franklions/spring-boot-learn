package junit4.learn.demo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/8/10
 * @since Jdk 1.8
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MethodOrderTest {

    @Test
    public void testA(){
        System.out.println("first");
    }

    @Test
    public void testB(){
        System.out.println("second");
    }

    @Test
    public void testC(){
        System.out.println("third");
    }
}
