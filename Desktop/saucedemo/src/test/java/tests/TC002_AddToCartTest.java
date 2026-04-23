package tests;

import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

public class TC002_AddToCartTest extends BaseTest {
    @Test
    public void addItemsToCartTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

       
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(prop.getProperty("username"));
        loginPage.enterPassword(prop.getProperty("password"));
        loginPage.clickLogin();

        wait.until(ExpectedConditions.urlContains("inventory"));

        HomePage homePage = new HomePage(driver);

        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("add-to-cart-sauce-labs-backpack")
        ));
        homePage.addBackpackToCart();

        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("add-to-cart-sauce-labs-bike-light")
        ));
        homePage.addBikeLightToCart();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[@id='shopping_cart_container']/a/span")
        ));

        String cartCount = homePage.getCartCount();
        Assert.assertEquals(cartCount, "2", "Cart should show 2 items");
    }
}