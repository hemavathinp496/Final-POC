package com.saucedemo.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;

public class CheckoutReviewPage extends BasePage {

    // ===== LOCATORS =====

    // Finish button - completes the order
    @FindBy(id = "finish")
    WebElement btnFinish;

    // "Thank you for your order!" heading
    @FindBy(className = "complete-header")
    WebElement lblThankYou;

    // sub message below thank you heading
    @FindBy(className = "complete-text")
    WebElement lblSuccessMsg;

    // ===== CONSTRUCTOR =====
    public CheckoutReviewPage(WebDriver driver) {
        super(driver); // calls BasePage constructor
    }

    // ===== ACTIONS =====

    // clicks the Finish button to place order
    public void clickFinish() {
        btnFinish.click();
    }

    // returns "Thank you for your order!" text
    public String getThankYouMessage() {
        return lblThankYou.getText();
    }

    // returns the sub message text
    public String getSuccessMessage() {
        return lblSuccessMsg.getText();
    }

    // returns true if thank you header is displayed
    public boolean isOrderSuccessful() {
        return lblThankYou.isDisplayed();
    }
}