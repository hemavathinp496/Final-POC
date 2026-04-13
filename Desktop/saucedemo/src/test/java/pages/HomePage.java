package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//*[@id='add-to-cart-sauce-labs-backpack']")
    WebElement btnAddBackpack;

  
    @FindBy(xpath = "//*[@id='add-to-cart-sauce-labs-bike-light']")
    WebElement btnAddBikeLight;

    
    @FindBy(xpath = "//*[@id='shopping_cart_container']/a")
    WebElement lnkCart;

    @FindBy(xpath = "//*[@id='shopping_cart_container']/a/span")
    WebElement lblCartBadge;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void addBackpackToCart() {
        btnAddBackpack.click();
    }

    public void addBikeLightToCart() {
        btnAddBikeLight.click();
    }

    public void clickCartIcon() {
        lnkCart.click();
    }

    public String getCartCount() {
        return lblCartBadge.getText();
    }
}