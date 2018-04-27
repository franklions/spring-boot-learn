package spring.boot.learn.logging.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.async.AsyncLogger;
import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.apache.logging.log4j.core.util.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/27
 * @since Jdk 1.8
 */
@SpringBootApplication
public class LogDemoApplication {

   private static   Logger logger = LogManager.getLogger(LogDemoApplication.class);

   private static AsyncLogger asyncLogger = (AsyncLogger) LogManager.getLogger(LogDemoApplication.class);

    public static void main(String[] args) {
        System.setProperty(Constants.LOG4J_CONTEXT_SELECTOR,
                AsyncLoggerContextSelector.class.getName());SpringApplication.run(LogDemoApplication.class,args);
        logger.info("test");
        asyncLogger.info("async test");
    }
}
