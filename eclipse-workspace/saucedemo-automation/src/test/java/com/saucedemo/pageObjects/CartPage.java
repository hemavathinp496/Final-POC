package com.saucedemo.pageObjects;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    // ===== LOCATORS =====

    // all item names listed in the cart
    @FindBy(className = "inventory_item_name")
    List<WebElement> lblCartItemNames;

    // checkout button at bottom of cart
    @FindBy(id = "checkout")
    WebElement btnCheckout;

    // ===== CONSTRUCTOR =====
    public CartPage(WebDriver driver) {
        super(driver); // calls BasePage constructor
    }

    // ===== ACTIONS =====

    // returns true if the given product name is in the cart
    public boolean isItemInCart(String productName) {
        for (WebElement item : lblCartItemNames) {
            if (item.getText().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    // returns how many items are currently in cart
    public int getCartItemCount() {
        return lblCartItemNames.size();
    }

    // clicks the Checkout button
    public void clickCheckout() {
        btnCheckout.click();
    }
}