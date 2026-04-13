package com.saucedemo.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.pageObjects.CartPage;
import com.saucedemo.pageObjects.CheckoutPage;
import com.saucedemo.pageObjects.CheckoutReviewPage;
import com.saucedemo.pageObjects.HomePage;
import com.saucedemo.pageObjects.LoginPage;
import com.saucedemo.testBase.BaseClass;

public class TC_004_EndToEndTest extends BaseClass {

    @Test
    public void verify_CompletePurchaseFlow() throws InterruptedException {
        logger.info("=== Starting End to End Test ===");

        // Step 1: login
        logger.info("Step 1: Logging in");
        LoginPage lp = new LoginPage(driver);
        lp.setUsername(prop.getProperty("username"));
        lp.setPassword(prop.getProperty("password"));
        lp.clickLogin();
        Thread.sleep(2000); // wait for inventory page to load
        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "Login failed"
        );

        // Step 2: add Backpack to cart
        logger.info("Step 2: Adding Backpack to cart");
        HomePage hp = new HomePage(driver);
        hp.clickAddBackpack();
        Thread.sleep(1000); // wait for cart to update

        // Step 3: add Bike Light to cart
        logger.info("Step 3: Adding Bike Light to cart");
        hp.clickAddBikeLight();
        Thread.sleep(1000); // wait for cart to update

        // verify cart badge shows 2
        Assert.assertEquals(hp.getCartCount(), "2",
            "Cart count should be 2");

        // Step 4: go to cart
        logger.info("Step 4: Going to cart");
        hp.clickCartIcon();
        Thread.sleep(2000); // wait for cart page to load

        // Step 5: verify items and click checkout
        logger.info("Step 5: Clicking checkout");
        CartPage cp = new CartPage(driver);
        Assert.assertEquals(cp.getCartItemCount(), 2,
            "Should have 2 items in cart");
        cp.clickCheckout();
        Thread.sleep(3000); // wait for checkout page to fully load

        // Step 6: fill checkout info
        logger.info("Step 6: Filling checkout info");
        CheckoutPage chp = new CheckoutPage(driver);
        chp.setFirstName(prop.getProperty("firstName"));
        Thread.sleep(500);
        chp.setLastName(prop.getProperty("lastName"));
        Thread.sleep(500);
        chp.setZipCode(prop.getProperty("zipCode"));
        Thread.sleep(1000);
        chp.clickContinue();
        Thread.sleep(3000); // wait for review page to fully load

        // Step 7: click finish
        logger.info("Step 7: Clicking finish");
        CheckoutReviewPage crp = new CheckoutReviewPage(driver);
        crp.clickFinish();
        Thread.sleep(3000); // wait for thank you page to load

        // Step 8: verify thank you message
        logger.info("Step 8: Verifying order confirmation");
        Assert.assertEquals(
            crp.getThankYouMessage(),
            "Thank you for your order!",
            "Thank you message does not match"
        );
        logger.info("=== End to End Test PASSED ===");
    }
}