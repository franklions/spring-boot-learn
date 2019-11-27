package learn.threadlocal;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-11-26
 * @since Jdk 1.8
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(new SessionServiceRunnable());
        Thread t2 = new Thread(new SessionServiceRunnable());
        Thread t3 = new Thread(new SessionServiceRunnable());
        Thread t4 = new Thread(new SessionServiceRunnable());

        t1.start();
        t2.start();
        t3.start();
        t4.start();


    }
}
