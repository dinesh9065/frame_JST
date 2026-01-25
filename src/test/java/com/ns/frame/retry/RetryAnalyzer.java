package com.ns.frame.retry;

import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.ns.frame.utils.ConfigReader;
import com.ns.frame.utils.FlakyExceptionDetector;
import com.ns.frame.utils.LoggerUtil;

public class RetryAnalyzer implements IRetryAnalyzer{
    private final  Logger log = LoggerUtil.getLogger(this.getClass());
    private int retryCount = 0;
    private final int maxRetryCount =  ConfigReader.getIntProperty("retry.count");

    @Override
    public boolean retry(ITestResult result) {
        // TODO Auto-generated method stub
       log.info(" Into RetryAnalyzer Class => retry method");
        if (!ConfigReader.getBoolean("enable.retry")) {
            log.info("Retry is disabled in Properties file.");
    return false; // skip attaching RetryAnalyzer
}
        log.info("Retry is Enabled in Properties file.");
        Throwable failure = result.getThrowable();

        boolean isFlakyFailure =
                FlakyExceptionDetector.isFlaky(failure);
        log.info("isFlakyFailure value: " + isFlakyFailure);
        if (!isFlakyFailure) {
            log.info("Failure is not flaky. No retry will be attempted.");
            return false; // 
        }
     log.info("retryCount value: " + retryCount);
     log.info("maxRetryCount value: " + maxRetryCount);
     if(retryCount < maxRetryCount){
        log.info("RetryAnalyzer class => retryCount value Before Increment: " + retryCount);
         retryCount++;
          log.info("RetryAnalyzer class => retryCount value After Increment: " + retryCount);
          if (ConfigReader.getBoolean("enable.logging")) {
       log.info("Retrying test " + result.getName() +
                           " : attempt " + retryCount + "/" + maxRetryCount);
    }
log.info("Retry Analyzer class => retry method returning true");
          return true;
      }
      log.info("Retry Analyzer class => retry method returning false");
      return false; //Maximum retries reached
  }



}
