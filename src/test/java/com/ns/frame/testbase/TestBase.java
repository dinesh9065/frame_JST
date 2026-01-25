package com.ns.frame.testbase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.ns.frame.base.SeleniumBase;
import com.ns.frame.driver.DriverManager;
import com.ns.frame.pages.HomePage;
import com.ns.frame.pages.LoginPage;
import com.ns.frame.utils.ConfigReader;
import com.ns.frame.utils.EncryptionUtil;
import com.ns.frame.utils.LoggerUtil;

public class TestBase {
    protected  WebDriver driver;
    protected DriverManager dm;
    protected final int pageLoadTime = ConfigReader.getIntProperty("defaultTimeout");
    protected Logger log = LoggerUtil.getLogger(this.getClass());
    protected SeleniumBase sBase;
    protected LoginPage login;
    protected HomePage homePage;

    @BeforeClass(alwaysRun=true)
    public void setUp() throws Exception {
      log.info("Initializing the WebDriver and loading the URL");
      String browser = ConfigReader.getProperty("browser");
      dm = DriverManager.getInstance();
      dm.initDriver(browser);
      this.driver = dm.getDriver();
      loadURL();
    //  log.info("Navigated to URL");
    }

   
    public void loadURL () throws Exception {
        try{
        
        driver.get(ConfigReader.getProperty("baseUrl")); 
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTime));
        login = new LoginPage(driver);
        //login.button.waitForPageLoad();
        login.navigateToLoginPage();
        String username =  EncryptionUtil.decrypt(ConfigReader.getProperty("username"));
        String pwd = EncryptionUtil.decrypt(ConfigReader.getProperty("pwd"));
       homePage = login.loginToHomePage(username, pwd);
        } catch (Exception e){
            throw new Exception("Unable to load URL: " + ConfigReader.getProperty("baseUrl"));
        }
    }


    @AfterClass(alwaysRun=true)
    public void tearDown() {
        log.info("Closing browser");
        DriverManager.getInstance().quitDriver();
    }


    public String captureScreenshot(String testName){

          if (this.driver == null) {
        log.error("Driver is null, cannot capture screenshot");
        return null;
    }
    String className = this.getClass().getSimpleName();
      TakesScreenshot ts = (TakesScreenshot) this.driver;
      File srcFile = ts.getScreenshotAs(OutputType.FILE);
      log.info("Capturing screenshot STARTED");
      String baseDir = System.getProperty("user.dir")
            + File.separator + "screenshots" + File.separator + className+ File.separator + testName;
     String fileName = testName + "_" + System.currentTimeMillis() + ".png";
    String path = baseDir+ File.separator + fileName;

             try {
        // ✅ Create directory if it does not exist
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
     
        Files.copy(srcFile.toPath(), new File(path).toPath());
         log.info("Screenshot captured at: " + path);
      } catch (IOException e) {
        log.error("Failed to copy screenshot to " + path, e);   
      }
return path;
    }


  
}
