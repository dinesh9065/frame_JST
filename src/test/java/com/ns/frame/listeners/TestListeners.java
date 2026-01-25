package com.ns.frame.listeners;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;

import com.ns.frame.driver.DriverManager;
import com.ns.frame.retry.RetryAnalyzer;
import com.ns.frame.testbase.TestBase;
import com.ns.frame.utils.AllureLogCollector;
import com.ns.frame.utils.ConfigReader;
import com.ns.frame.utils.LoggerUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;

public class TestListeners implements ITestListener{

    private final Logger log = LoggerUtil.getLogger(this.getClass());



 /**
     * Attach screenshot to Allure report
     */
    private void attachScreenshotToAllure(WebDriver driver) {
        log.info("Allure integration Step 1...");
        if (driver == null) return;
        try {
            log.info("Allure integration Step 2...");
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshot));
            log.info("Screenshot attached to Allure report.");
        } catch (Exception e) {
            log.error("Failed to attach screenshot to Allure: " + e.getMessage());
        }
    }

    /**
     * Attach failure reason / exception message to Allure report
     */
    private void attachFailureReason(Throwable throwable) {
        if (throwable == null) return;
        try {
             log.info("Allure integration Step 3...");
            String message = throwable.getMessage() != null ? throwable.getMessage() : throwable.toString();
            Allure.addAttachment("Failure Reason", message);
            log.info("Failure reason attached to Allure report.");

            
        } catch (Exception e) {
            log.error("Failed to attach failure reason to Allure: " + e.getMessage());
        }
    }

    @Override
    public void onTestStart(org.testng.ITestResult result) {
        log.info("Test Started Listener: " + result.getName());

           if (!result.getMethod().getEnabled()) return;

           // Skip if retry is active and not first run
           // Skip scheduling in Allure only if this is a retry
           RetryAnalyzer retry = (RetryAnalyzer) result.getMethod().getRetryAnalyzer(result);
    int currentInvocation = result.getMethod().getCurrentInvocationCount(); // 0 = first run
    log.info("Current invocation count: " + currentInvocation);
    log.info("On Start => retry: " + retry);
   // if (retry != null && retryCountRemaining(retry)) return;
 if (retry != null && currentInvocation > 0) {
        log.info("This is a retry execution, skipping Allure test creation.");
        return;
    }
        
     log.info("End of onTestStart listener");
    }

    @Override
    public void onTestSuccess(org.testng.ITestResult result) {
        log.info("Test Passed: " + result.getName());   
    log.info("End of onTestSuccess listener");
     attachLogsToAllure();
    }

    @Override
    public void onTestFailure(org.testng.ITestResult result) {
          log.info("Into onTestFailure listener: " + result.getName());
        
        String testName = result.getName();
        log.error("Test Failed: " + result.getName());
     /*  RetryAnalyzer retry =
        (RetryAnalyzer) result.getMethod()
                              .getRetryAnalyzer(result);
        boolean retryEnabled = ConfigReader.getBoolean("enable.retry");
         log.info("Retry enabled: " + retryEnabled);
         log.info("retry: " + retry);
         log.info("retryCountRemaining(retry): " + retryCountRemaining(retry)); 
         if (retryEnabled && retry != null && retryCountRemaining(retry)) {
        log.info("Retry pending, skipping screenshot");
         log.info("Retry attempt for test: " + testName + " will run again"); // <<--- add here
        log.info("Retry pending for test: " + testName + ", not creating a new Allure test");
       
        return;
    }*/
    
      // No retries left, capture screenshot
    log.error("Final failure for test: " + testName + ", capturing screenshot...");

        Object testClass = result.getInstance();
        if( testClass instanceof TestBase testBase){

            WebDriver driver =  DriverManager.getInstance().getDriver();
            log.info("Allure integration started...");
             // Attach screenshot to Allure
            attachScreenshotToAllure(driver);

             // Attach failure reason to Allure
            attachFailureReason(result.getThrowable());
            
            String screenshotPath = testBase.captureScreenshot(result.getName());
            log.error("Screenshot captured at: " + screenshotPath);
             // Attach screenshot path to ITestResult for reporting tools
            result.setAttribute("screenshotPath", screenshotPath);
            attachLogsToAllure();
        }
    }

    private boolean retryCountRemaining(RetryAnalyzer retry) {
    try {
        var field = RetryAnalyzer.class
                .getDeclaredField("retryCount");
        field.setAccessible(true);
        int current = (int) field.get(retry);
        log.info("current: " + current);
        log.info("ConfigReader.retry.count: " + ConfigReader.getIntProperty("retry.count"));
        return current < ConfigReader.getIntProperty("retry.count");
    } catch (Exception e) {
        return false;
    }
}
    @Override
    public void onTestSkipped(org.testng.ITestResult result) {  
        log.warn("Test Skipped: " + result.getName());
         attachLogsToAllure();
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        log.info("All tests finished in suite: " + context.getName());
         log.info("Test Execution Completed.");
    }   


private void attachLogsToAllure() {
    log.info("Into attachLogsToAllure method");
    String logs = AllureLogCollector.get();
    log.info("Collected Logs: " + logs);
    if (logs != null && !logs.isEmpty()) {
        log.info("Into Logs");
        Allure.addAttachment("Execution Logs", "text/plain", logs);
         
    }
    AllureLogCollector.clear();
     log.info("out of attachLogsToAllure method");
}



}
