package com.ns.frame.webpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ns.frame.base.SeleniumBase;

public class Button  extends SeleniumBase {

    public Button(WebDriver driver) {
        super(driver);
    }

    public void clickButton(WebElement element) {
        click(element);
    }

    public String getButtonText(WebElement element) {
        return getText(element);
    }

    public String getButtonAttribute(WebElement element, String attribute) {
        return getAttribute(element, attribute);
    }


    public void clickButtonUsingJS(WebElement element) {
        clickJs(element);
    }

    

    
}
