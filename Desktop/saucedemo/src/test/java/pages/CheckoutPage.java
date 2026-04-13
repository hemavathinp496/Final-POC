package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    @FindBy(xpath = "//*[@id='first-name']")
    WebElement txtFirstName;

    @FindBy(xpath = "//*[@id='last-name']")
    WebElement txtLastName;

    @FindBy(xpath = "//*[@id='postal-code']")
    WebElement txtZipCode;

    @FindBy(xpath = "//*[@id='continue']")
    WebElement btnContinue;

    // finish button on review page
    @FindBy(xpath = "//*[@id='finish']")
    WebElement btnFinish;

    // thank you message after order placed
    @FindBy(xpath = "//*[@id='checkout_complete_container']/h2")
    WebElement lblThankYou;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void enterFirstName(String firstName) {
        txtFirstName.clear();
        txtFirstName.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        txtLastName.clear();
        txtLastName.sendKeys(lastName);
    }

    public void enterZipCode(String zipCode) {
        txtZipCode.clear();
        txtZipCode.sendKeys(zipCode);
    }

    public void clickContinue() {
        btnContinue.click();
    }

    public void clickFinish() {
        btnFinish.click();
    }

    public String getThankYouMessage() {
        return lblThankYou.getText();
    }
}