package com.ns.frame.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;



public class LoggerUtil {

    private static final boolean LOGGING_ENABLED = ConfigReader.getBoolean("enable.logging");
    private static final ThreadLocal<Logger> logger = new ThreadLocal<>();
    private static final Level LOG_LEVEL;

static {
    String levelStr  = ConfigReader.getProperty("log.level");
      Level level = Level.toLevel(levelStr, Level.INFO); // default INFO
    LOG_LEVEL = level;
     Configurator.setRootLevel(LOG_LEVEL);
}

    
    public static Logger getLogger(Class<?> clazz) {
        if (logger.get() == null) {
            logger.set(LogManager.getLogger(clazz));
            
        }
        return logger.get();
    }

 public static void info(Class<?> clazz, String message) {
        if (LOGGING_ENABLED) {
            getLogger(clazz).info(message);
            AllureLogCollector.add(AllureLogCollector.Level.INFO, message);
        }
    }

     public static void warn(Class<?> clazz, String message) {
        if (LOGGING_ENABLED) {
            getLogger(clazz).warn(message);
            AllureLogCollector.add(AllureLogCollector.Level.WARN, message);
        }
    }

    // Error level logging
    public static void error(Class<?> clazz, String message) {
        if (LOGGING_ENABLED) {
            getLogger(clazz).error(message);
            AllureLogCollector.add(AllureLogCollector.Level.ERROR, message);
            // error with Throwable
            // AllureLogCollector.add("[ERROR] " + message + " | " + t.getMessage());
        }
    }

    // Error level with Throwable
    public static void error(Class<?> clazz, String message, Throwable t) {
        if (LOGGING_ENABLED) {
            getLogger(clazz).error(message, t);
        }
    }

    // Optional: Clear ThreadLocal to prevent memory leaks
    public static void clearLogger() {
        logger.remove();
    }



}
