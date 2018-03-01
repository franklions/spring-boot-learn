package async.completable.future.demo.thread;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/2/8
 * @since Jdk 1.8
 */
public class ExecutorServiceTest {
    static  ExecutorService pool = Executors.newFixedThreadPool(5,
            (Runnable r)->{
                Thread t = new Thread(r);
                t.setDaemon(true);      //设置守护线程   慎用不是正确关闭线程的方法
                return t;
            });
//static  ExecutorService pool = Executors.newFixedThreadPool(5);
    public static void main(String[] args) {


        final long waitTime = 60 * 1000;
        final long awaitTime = 20 * 1000;

        Runnable task1 = new Runnable(){
            public void run(){
                try {
                    System.out.println("task1 start");
                    Thread.sleep(waitTime);
                    System.out.println("task1 end");
                } catch (InterruptedException e) {
                    System.out.println("task1 interrupted: " + e);
                }
            }
        };



        Runnable task2 = new Runnable(){
            public void run(){
                try {
                    System.out.println("  task2 start");
                    Thread.sleep(1000);
                    System.out.println("  task2 end");
                } catch (InterruptedException e) {
                    System.out.println("task2 interrupted: " + e);
                }
            }
        };
        // 让学生解答某个很难的问题
//        pool.execute(task1);
        CompletableFuture.runAsync(task1,pool).exceptionally((t)->{t.printStackTrace();return null;});
        // 生学生解答很多问题
        for(int i=0; i<1000; ++i){
//            pool.execute(task2);
            CompletableFuture.runAsync(task2,pool).exceptionally((t)->{t.printStackTrace();return null;});
        }

        try {
            // 向学生传达“问题解答完毕后请举手示意！”
            System.out.println(">>>>>>>>>>> pre shutdown >>>>>>>>>>>>>");
            pool.shutdown();

            // 向学生传达“XX分之内解答不完的问题全部带回去作为课后作业！”后老师等待学生答题
            // (所有的任务都结束的时候，返回TRUE)
            System.out.println(">>>>>>>>>>>await>>>>>>>>>>>>>");
            if(!pool.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted: " + e);
            pool.shutdownNow();
        }

        System.out.println(" main end");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
