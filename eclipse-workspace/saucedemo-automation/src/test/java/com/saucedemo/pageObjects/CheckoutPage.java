package com.saucedemo.pageObjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    WebElement txtFirstName;

    @FindBy(id = "last-name")
    WebElement txtLastName;

    // zip / postal code field
    @FindBy(id = "postal-code")
    WebElement txtZipCode;

    // continue button goes to review page
    @FindBy(id = "continue")
    WebElement btnContinue;

    // constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void setFirstName(String firstName) {
        // wait for first name field to be visible
        // before trying to type into it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("first-name")
        ));
        txtFirstName.clear();
        txtFirstName.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        txtLastName.clear();
        txtLastName.sendKeys(lastName);
    }

    public void setZipCode(String zipCode) {
        txtZipCode.clear();
        txtZipCode.sendKeys(zipCode);
    }

    // clicks Continue to go to review page
    public void clickContinue() {
        btnContinue.click();
    }
}