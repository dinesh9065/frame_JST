package com.ns.frame.webpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ns.frame.base.SeleniumBase;

public class DropDown extends SeleniumBase {

    public DropDown(WebDriver driver) {
        super(driver);
    }

    public void selectByValue(WebElement element, String value) {
        selectValueFromDropdownByValue(element, value);
    }

    public void selectByVisibleText(WebElement element, String visibleText) {
        selectValueFromDropdownByVisibleText(element, visibleText);
    }

    public void selectByIndex(WebElement element, int index) {
        selectValueFromDropdownByIndex(element, index);
    }
    
}
