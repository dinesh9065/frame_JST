package com.ns.frame.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ns.frame.webpage.BaseClass;

public class LoginPage extends BaseClass {


    public LoginPage(WebDriver driver){

        super(driver);
    }

    @FindBy(id="LoginCredential_Email")
    private  WebElement usernameField;

    @FindBy(id="LoginCredential_Password")
    private  WebElement passwordField;

    @FindBy(xpath="//input[@value='Sign In']")
    private WebElement signInButton;

    @FindBy(xpath= "//div[@class='brand-bar-wrapper']//a[@href='/saml_login']")
    private WebElement homePageLink;

    public HomePage loginToHomePage(String username , String password){

        textField.enterTextInField(usernameField, username);
        textField.enterTextInField(passwordField, password);
        button.clickButton(signInButton);
        button.waitForPageLoad();
        return new HomePage(driver);
    }

 public void navigateToLoginPage(){

       button.click(homePageLink);
       button.waitForPageLoad();


    }


}
