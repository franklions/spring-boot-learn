package async.completable.future.demo;

import async.completable.future.demo.service.Test2;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/13
 * @since Jdk 1.8
 */
public class AsyncDemoApplication {
    public static void main(String[] args) throws Exception {
        Test2.test();
        System.in.read();
    }
}
