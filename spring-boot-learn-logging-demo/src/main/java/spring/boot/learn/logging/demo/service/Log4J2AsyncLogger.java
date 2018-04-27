package spring.boot.learn.logging.demo.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/27
 * @since Jdk 1.8
 */
public class Log4J2AsyncLogger {
    private static Logger logger = LogManager.getLogger();

    public void performSomeTask(){
        logger.debug("this is a debug message.");
    }
}
