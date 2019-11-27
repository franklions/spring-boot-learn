package learn.threadlocal;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-11-26
 * @since Jdk 1.8
 */
public class ThreadLocalLambdaDemo {

    /**
     * 运行入口
     *
     * @param args 运行参数
     */
    public static void main(String[] args) {
        safeDeposit();
        //notSafeDeposit();
    }

    /**
     * 线程安全的存款
     */
    private static void safeDeposit() {
        SafeBank bank = new SafeBank();
        Thread thread1 = new Thread(() -> bank.deposit(200), "张成瑶");
        Thread thread2 = new Thread(() -> bank.deposit(200), "马云");
        Thread thread3 = new Thread(() -> bank.deposit(500), "马化腾");
        thread1.start();
        thread2.start();
        thread3.start();
    }

    /**
     * 非线程安全的存款
     */
    private static void notSafeDeposit() {
        NotSafeBank bank = new NotSafeBank();
        Thread thread1 = new Thread(() -> bank.deposit(200), "张成瑶");
        Thread thread2 = new Thread(() -> bank.deposit(200), "马云");
        Thread thread3 = new Thread(() -> bank.deposit(500), "马化腾");
        thread1.start();
        thread2.start();
        thread3.start();
    }


}
