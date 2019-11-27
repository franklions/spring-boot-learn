package learn.threadlocal;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-11-26
 * @since Jdk 1.8
 */
public class SessionServiceRunnable implements Runnable {

    private static final ThreadLocal<SessionData> sessionThreadLocal = new ThreadLocal<>();
    @Override
    public void run() {
        SessionData session = sessionThreadLocal.get();
        if(session == null){
            session = new SessionData();
            session.setId(Thread.currentThread().getId());
            session.setName(Thread.currentThread().getName());
            sessionThreadLocal.set(session);
        }
        System.out.println("CurrentThread:"+Thread.currentThread().getName() + " Session:"+session.toString());
    }
}
