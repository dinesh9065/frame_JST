package com.ns.frame.base;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ns.frame.utils.ConfigReader;

public class SeleniumBase{

protected WebDriver driver;
private final int waitTime = ConfigReader.getIntProperty("defaultTimeout");
private final int pageLoadTime = ConfigReader.getIntProperty("pageLoadTimeout");
public SeleniumBase(WebDriver driver){

    this.driver = driver;

}

public void click(WebElement element){
    waitForElementToBeClickable(element, waitTime);
    element.click();
}

public void click(WebElement element , int waitTimeInSeconds){
    waitForElementToBeClickable(element, waitTimeInSeconds);
    element.click();
}

public void enterText(WebElement element, String text){
    waitForElementToBeVisible(element, waitTime);
    element.clear();
    element.sendKeys(text);

}

public String getText(WebElement element){
    waitForElementToBeVisible(element, waitTime);
    return element.getText();
}

public void selectValueFromDropdownByValue(WebElement element , String value){
    waitForElementToBeVisible(element, waitTime);
    Select dropdown = new Select(element);
    dropdown.selectByValue(value);
}

public void selectValueFromDropdownByVisibleText(WebElement element , String visibleText){
    waitForElementToBeVisible(element, waitTime);
    Select dropdown = new Select(element);
    dropdown.selectByVisibleText(visibleText);
}

public void selectValueFromDropdownByIndex(WebElement element , int index){
    waitForElementToBeVisible(element, waitTime);
    Select dropdown = new Select(element);
    dropdown.selectByIndex(index);
}

public String getAttribute(WebElement element, String attribute){
    waitForElementToBeVisible(element, waitTime);
    return element.getAttribute(attribute);
}

public void clickJs(WebElement element){
    waitForElementToBeClickable(element, waitTime);
   JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", element);
}

public String getTextFromUsingJs(WebElement element){
    waitForElementToBeVisible(element, waitTime);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    return (String) js.executeScript("return arguments[0].textContent;", element);
}

public void scrollIntoViewJs(WebElement element){
    waitForElementToBeVisible(element, waitTime);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true);", element);
}

public void sendKeysJs(WebElement element, String text){
    waitForElementToBeVisible(element, waitTime);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].value='" + text + "';", element);
}

public void selectValueFromDropdownJs(WebElement element, String value){
    waitForElementToBeVisible(element, waitTime);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].value='" + value + "';", element);
}


public void  waitForElementToBeClickable(WebElement element, int timeoutInSeconds){
    new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
        .until(ExpectedConditions.elementToBeClickable(element));
}


public void waitForElementToBeVisible(WebElement element, int timeoutInSeconds){
    new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
        .until(ExpectedConditions.visibilityOf(element));
}

public void waitForPageLoad(int timeoutInSeconds){
    new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
        .until(webDriver -> ((JavascriptExecutor) webDriver)
        .executeScript("return document.readyState").equals("complete"));
}

public void waitForPageLoad(){
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTime));
}

}