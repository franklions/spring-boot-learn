package spring.boot.learn.logging.demo.service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/4/27
 * @since Jdk 1.8
 */
public class Log4j2AsyncLoggerTest {
    @Test
    public void performSomeTask() throws Exception {
        Log4J2AsyncLogger log4J2AsyncLogger=new Log4J2AsyncLogger();
        log4J2AsyncLogger.performSomeTask();
    }
}