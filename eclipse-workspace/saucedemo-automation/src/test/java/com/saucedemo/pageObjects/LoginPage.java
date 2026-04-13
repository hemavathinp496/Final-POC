package com.saucedemo.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // ===== LOCATORS =====
    // @FindBy connects this variable to the actual element on the page

    @FindBy(id = "user-name")
    WebElement txtUsername;

    @FindBy(id = "password")
    WebElement txtPassword;

    @FindBy(id = "login-button")
    WebElement btnLogin;

    // error message shown when login fails
    @FindBy(xpath = "//h3[@data-test='error']")
    WebElement lblErrMsg;

    // ===== CONSTRUCTOR =====
    public LoginPage(WebDriver driver) {
        super(driver); // calls BasePage constructor
    }

    // ===== ACTIONS =====

    // types username into the username field
    public void setUsername(String username) {
        txtUsername.clear();
        txtUsername.sendKeys(username);
    }

    // types password into the password field
    public void setPassword(String password) {
        txtPassword.clear();
        txtPassword.sendKeys(password);
    }

    // clicks the login button
    public void clickLogin() {
        btnLogin.click();
    }

    // returns the error message text when login fails
    public String getErrorMessage() {
        return lblErrMsg.getText();
    }
}