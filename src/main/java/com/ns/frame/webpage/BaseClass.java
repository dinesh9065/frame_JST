package com.ns.frame.webpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class BaseClass {

protected WebDriver driver;
protected Button button;
protected DropDown dropDown;
protected TextField textField;

  public  BaseClass(WebDriver driver){
        this.driver = driver;
         PageFactory.initElements(driver, this);
        this.textField = new TextField(driver);
        this.button = new Button(driver);  
        this.dropDown = new DropDown(driver);

    }
    
}
