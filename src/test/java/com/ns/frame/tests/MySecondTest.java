package com.ns.frame.tests;


import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ns.frame.pages.HomePage;
import com.ns.frame.pages.LoginPage;
import com.ns.frame.testbase.TestBase;
import com.ns.frame.utils.ConfigReader;
import com.ns.frame.utils.EncryptionUtil;
import com.ns.frame.utils.LoggerUtil;

public class MySecondTest extends TestBase {
   

    
    
    @Test(groups = {"smoke"})
    public void LoginToUI() {
        LoggerUtil.info(this.getClass(),"Testing LoginToUI test method started");
    //   driver.findElement(By.name("D")).sendKeys("Selenium");
        System.out.println("Title of the page: " + driver.getTitle());
         LoggerUtil.info(this.getClass(),"End of testing LoginToUI test method");
    }

//      @Test(enabled = false)
//     public void LoginSHRM1() {
//         LoggerUtil.info(this.getClass(),"Testing LoginSHRM1 test method started");
// //        driver.findElement(By.name("D")).sendKeys("Selenium");
//         System.out.println("Title of the page: " + driver.getTitle());
//         LoggerUtil.info(this.getClass(),"End of testing LoginSHRM1 test method");
//     }
}
