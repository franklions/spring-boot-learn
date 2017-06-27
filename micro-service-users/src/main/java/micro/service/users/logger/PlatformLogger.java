package micro.service.users.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/1/17.
 */
@Component
public class PlatformLogger implements IPlatformLogger {

    private final Logger log4j2Logger = LogManager.getLogger(getClass());

    @Override
    public void always(String message) {
        log4j2Logger.log(Level.forName("always",0),message);
    }

    @Override
    public void error(String message) {
        log4j2Logger.error(message);

    }

    @Override
    public void warn(String message) {
        log4j2Logger.warn(message);
    }

    @Override
    public void info(String message) {
        log4j2Logger.info(message);
    }

    @Override
    public void debug(String message) {
        log4j2Logger.debug(message);
    }

    @Override
    public void always(Object o) {
        log4j2Logger.log(Level.forName("always",0),o);
    }

    @Override
    public void error(Object o) {
        log4j2Logger.error(o);
    }

    @Override
    public void warn(Object o) {
        log4j2Logger.warn(o);
    }

    @Override
    public void info(Object o) {
        log4j2Logger.info(o);
    }

    @Override
    public void debug(Object o) {
        log4j2Logger.debug(o);
    }
}
