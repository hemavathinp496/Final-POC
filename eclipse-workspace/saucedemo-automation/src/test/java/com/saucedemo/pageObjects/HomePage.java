package com.saucedemo.pageObjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    // Add to cart button - Sauce Labs Backpack
    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    WebElement btnAddBackpack;

    // Add to cart button - Sauce Labs Bike Light
    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    WebElement btnAddBikeLight;

    // cart icon at top right
    @FindBy(className = "shopping_cart_link")
    WebElement lnkCart;

    // the number badge on cart icon e.g. "2"
    @FindBy(className = "shopping_cart_badge")
    WebElement lblCartBadge;

    // constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // clicks Add to cart for Backpack
    public void clickAddBackpack() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // wait for button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("add-to-cart-sauce-labs-backpack")
        )).click();
    }

    // clicks Add to cart for Bike Light
    public void clickAddBikeLight() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // wait for button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("add-to-cart-sauce-labs-bike-light")
        )).click();
    }

    // clicks the cart icon to go to cart page
    public void clickCartIcon() {
        lnkCart.click();
    }

    // returns cart count without waiting
    // items are already added before calling this
    public String getCartCount() {
        return lblCartBadge.getText();
    }
}