package com.ns.frame.webpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ns.frame.base.SeleniumBase;

public class TextField extends SeleniumBase{

    public TextField(WebDriver driver) {
        super(driver);
    }

public void enterTextInField(WebElement element, String text) {
        enterText(element, text);
    }

    public String getTextFromField(WebElement element) {
        return getText(element);
    }

    public String getFieldAttribute(WebElement element, String attribute) {
        return getAttribute(element, attribute);
    }

    public void enterTextInFieldUsingJS(WebElement element, String text) {
        sendKeysJs(element, text);
    }

   public void getTextFromFieldUsingJS(WebElement element) {
       getTextFromUsingJs(element);
   }





}
