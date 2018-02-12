package async.completable.future.demo.thread.demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/2/8
 * @since Jdk 1.8
 */
public class CacheHealthChecker extends BaseHealthChecker {
    public CacheHealthChecker (CountDownLatch latch)  {
        super("Cache Service", latch);
    }

    @Override
    public void verifyService()
    {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(12000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
