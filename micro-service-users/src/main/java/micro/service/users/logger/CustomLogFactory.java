package micro.service.users.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.CustomLevelConfig;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;


/**
 * Created by Administrator on 2017/1/16.
 */
public class CustomLogFactory {
    private CustomLogFactory(){

    }

    public static void start(String logId){
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();

        Layout consoleLayout = PatternLayout.createLayout("Yggdrasil 1 %d{UNIX_MILLIS}{UTC} %level ${hostName} idcenter 1 %msg%n",
                null,config,null,null,true,false,null,null);
        Appender appender = ConsoleAppender.createAppender(consoleLayout,null,null,logId,false,false,false);
        appender.start();
        config.addAppender(appender);

        AppenderRef ref = AppenderRef.createAppenderRef(logId, null, null);
        AppenderRef[] refs = new AppenderRef[]{ref};
        LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.ALL,logId,null,refs,null,config,null);

        loggerConfig.addAppender(appender, null, null);
        config.addLogger(logId, loggerConfig);

        ctx.updateLoggers();
    }

    public static void stop(String logId) {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        config.getAppender( logId).stop();
        config.getLoggerConfig( logId).removeAppender( logId);
        config.removeLogger( logId);
        ctx.updateLoggers();
    }

    public static Logger createLogger(String logId) {
        start(logId);
        return LogManager.getLogger(logId);
    }
}
