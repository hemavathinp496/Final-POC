package com.saucedemo.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.pageObjects.HomePage;
import com.saucedemo.pageObjects.LoginPage;
import com.saucedemo.testBase.BaseClass;

public class TC_002_AddToCartTest extends BaseClass {

    @Test(priority = 1)
    public void verify_AddProductsToCart() throws InterruptedException {
        logger.info("Starting add to cart test");

        // Step 1: login first
        LoginPage lp = new LoginPage(driver);
        lp.setUsername(prop.getProperty("username"));
        lp.setPassword(prop.getProperty("password"));
        lp.clickLogin();
        Thread.sleep(2000); // watch login happen

        // Step 2: add Backpack to cart
        HomePage hp = new HomePage(driver);
        hp.clickAddBackpack();
        Thread.sleep(2000); // watch backpack added to cart

        // Step 3: add Bike Light to cart
        hp.clickAddBikeLight();
        Thread.sleep(2000); // watch bike light added to cart

        // Step 4: verify cart badge shows 2
        String cartCount = hp.getCartCount();
        Thread.sleep(2000); // watch cart badge update

        Assert.assertEquals(
            cartCount, "2",
            "Cart count should be 2 after adding 2 items"
        );
        logger.info("Cart badge shows: " + cartCount);
    }
}