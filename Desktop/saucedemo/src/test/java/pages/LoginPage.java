package pages;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//*[@id='user-name']")
    WebElement txtUsername;

    @FindBy(xpath = "//*[@id='password']")
    WebElement txtPassword;

    @FindBy(xpath = "//*[@id='login-button']")
    WebElement btnLogin;

    @FindBy(xpath = "//h3[@data-test='error']")
    WebElement lblError;

    public LoginPage(WebDriver driver) {
        super(driver); 
    }

    public void enterUsername(String username) {
        txtUsername.clear();
        txtUsername.sendKeys(username);
    }

    public void enterPassword(String password) {
        txtPassword.clear();
        txtPassword.sendKeys(password);
    }

    public void clickLogin() {
        btnLogin.click();
    }

    public String getErrorMessage() {
        return lblError.getText();
    }
}