package java.async.completable.future.demo;

import java.async.completable.future.demo.service.Test1;
import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/13
 * @since Jdk 1.8
 */
public class AsyncDemoApplication {
    public static void main(String[] args) throws Exception {
        Test1.test();
        System.in.read();
    }
}
