package com.ns.frame.driver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

    private static DriverManager instance; // Singleton instance
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>(); // Thread-safe WebDriver
     private static ThreadLocal<Set<Cookie>> sessionCookies = new ThreadLocal<>(); // thread-safe cookies
  
    private DriverManager() {} // private constructor

    // Thread-safe lazy singleton
    public static synchronized DriverManager getInstance() {
        if (instance == null) {
            instance = new DriverManager();
        }
        return instance;
    }

    public void initDriver(String browserName) {
        if (driver.get() == null) { // Thread-local ensures safety
          //  String tempDir = System.getProperty("java.io.tmpdir");
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
               //      String profilePath = tempDir+"ChromeProfile_" + Thread.currentThread().getName();
                 //   chromeOptions.addArguments("--user-data-dir=" + profilePath);
                    // 1. Disable download popups
Map<String, Object> prefs = new HashMap<>();
prefs.put("profile.default_content_settings.popups", 0); // disable popup
prefs.put("download.default_directory", "C:\\Downloads"); // change path as needed
prefs.put("download.prompt_for_download", false); // prevent download prompt
prefs.put("safebrowsing.enabled", true); // allow safe downloads
chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.addArguments("--start-maximized");
                    driver.set(new ChromeDriver(chromeOptions));
                     driver.get().manage().window().maximize();
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                     // Set thread-specific Edge profile
                //    String edgeProfilePath = tempDir + "EdgeProfile_" + Thread.currentThread().getName();
                  //  edgeOptions.addArguments("--user-data-dir=" + edgeProfilePath);
                    // Set preferences
Map<String, Object> edgePrefs = new HashMap<>();
edgePrefs.put("profile.default_content_settings.popups", 0);        // Disable popups
edgePrefs.put("download.default_directory", "C:\\Downloads");       // Set download folder
edgePrefs.put("download.prompt_for_download", false);               // Do not prompt
edgePrefs.put("safebrowsing.enabled", true);                        // Allow safe downloads


                    // Optional headless mode
                    // edgeOptions.addArguments("--headless");
                    edgeOptions.addArguments("--start-maximized");
                    edgeOptions.addArguments("--remote-allow-origins=*");
// Apply preferences to Edge
edgeOptions.setExperimentalOption("prefs", edgePrefs);
                    driver.set(new EdgeDriver(edgeOptions));
                    driver.get().manage().window().maximize();
                    break;

                default:
                    System.out.println("Invalid browser! Launching Chrome as default.");
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver());
                    driver.get().manage().window().maximize();
            }
         //   driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
       
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
             sessionCookies.remove(); // clean cookies as well
        }
    }

    // ------------------ Cookie Methods ------------------

    public void saveCookies() {
        Set<Cookie> cookies = driver.get().manage().getCookies();
        sessionCookies.set(cookies);
    }

    public void loadCookies(String url) {
        WebDriver driver = getDriver();
        driver.get(url); // open domain first
        Set<Cookie> cookies = sessionCookies.get();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
            driver.navigate().refresh();
        }
    }

    public Set<Cookie> getSessionCookies() {
        return sessionCookies.get();
    }    
}



