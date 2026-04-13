package com.saucedemo.testBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass {

    public WebDriver driver;
    public static final Logger logger = LogManager.getLogger(BaseClass.class);
    public Properties prop;

    @BeforeClass
    public void setup() throws IOException {

        // Step 1: load config.properties
        prop = new Properties();
        FileInputStream fis = new FileInputStream(
            System.getProperty("user.dir") +
            "/src/test/resources/config.properties"
        );
        prop.load(fis);
        logger.info("Config file loaded successfully");

        // Step 2: Chrome preferences to block ALL popups
        HashMap<String, Object> prefs = new HashMap<>();

        // completely disable password manager saving
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        // disable the "change your password" bubble
        prefs.put("profile.password_manager_leak_detection", false);

        // Step 3: apply prefs to ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        // additional arguments to suppress popups
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-infobars");

        // Step 4: launch Chrome with options
        driver = new ChromeDriver(options);
        logger.info("Chrome browser launched");

        // Step 5: basic browser setup
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(
                Integer.parseInt(prop.getProperty("implicitWait"))
            )
        );
        driver.manage().window().maximize();

        // Step 6: open application URL
        driver.get(prop.getProperty("appUrl"));
        logger.info("Navigated to: " + prop.getProperty("appUrl"));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }

    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(5);
    }

    public String randomAlphaNumeric() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}