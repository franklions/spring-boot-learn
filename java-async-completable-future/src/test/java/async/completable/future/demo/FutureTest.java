package async.completable.future.demo;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/8/15
 * @since Jdk 1.8
 */
public class FutureTest {

    private static final Logger logger = Logger.getLogger("test");

    @Test
    public void testAsyncFuture() throws IOException {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);

                    logger.info("开始删除城市信息...");

                    Thread.sleep(2000);
                    logger.info("开始插入城市信息....");
                    Thread.sleep(1000);
                    logger.info("城市信息处理完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        logger.info("城市信息处理程序已经运行。");
        logger.info("此时可以返回结果了。");
        System.in.read();
    }
}
