package com.saucedemo.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    // ===== WebDriver instance shared with all page classes =====
    public WebDriver driver;

    // ===================================================
    // Constructor - every page class calls super(driver)
    // PageFactory.initElements() connects all @FindBy
    // annotations to actual browser elements
    // ===================================================
    public BasePage(WebDriver driver) {
        this.driver = driver;

        // this line makes @FindBy work in child page classes
        PageFactory.initElements(driver, this);
    }
}