package com.ns.frame.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.ns.frame.annotations.FlakyTest;
import com.ns.frame.retry.RetryAnalyzer;

public class RetryTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {

        if (testMethod != null && testMethod.isAnnotationPresent(FlakyTest.class)) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        } else {
            // If you want all tests to use retry as default
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}
