package com.ns.frame.utils;


import org.openqa.selenium.*;
import java.util.Arrays;
import java.util.List;

public class FlakyExceptionDetector {

    private static final List<Class<? extends Throwable>> FLAKY_EXCEPTIONS =
            Arrays.<Class<? extends Throwable>>asList(
                    TimeoutException.class,
                    StaleElementReferenceException.class,
                   // WebDriverException.class,
                    NoSuchSessionException.class
            );

    public static boolean isFlaky(Throwable throwable) {

        if (throwable == null){
            return false ;
        } 

        for (Class<? extends Throwable> ex : FLAKY_EXCEPTIONS) {
             System.out.println("In to Method isFlaky: ex: " + ex);
            if (ex.isAssignableFrom(throwable.getClass())) {
                 System.out.println("In to Method isFlaky: Flaky exception detected: " + throwable);
                return true;
            }
        }
    System.out.println("Checking nested cause for throwable: " + throwable);
     System.out.println("throwable.getCause() " + throwable.getCause());
        // Nested exception support
        return throwable.getCause() != null &&
               isFlaky(throwable.getCause());
    }
}
