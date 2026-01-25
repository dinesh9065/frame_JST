package com.ns.frame.utils;

import org.apache.logging.log4j.Logger;

public class AllureLogCollector {
    private static final ThreadLocal<StringBuilder> LOGS =
            ThreadLocal.withInitial(StringBuilder::new);

            public enum Level { INFO, ERROR, WARN, DEBUG }

    public static void add(Level level,String message) {
          LOGS.get().append("[").append(level).append("] ")
                  .append(message)
                  .append(System.lineSeparator());
    
    }

    public static String get() {
        System.out.println("Into Allure collector class => get method");
        return LOGS.get().toString();
    }

    public static void clear() {
        LOGS.remove();
    }
}
