package micro.service.users.logger;

/**
 * Created by Administrator on 2017/1/17.
 */
public interface IPlatformLogger {
    void always(String message);
    void error(String message);
    void warn(String message);
    void info(String message);
    void debug(String message);

    void always(Object o);
    void error(Object o);
    void warn(Object o);
    void info(Object o);
    void debug(Object o);
}
