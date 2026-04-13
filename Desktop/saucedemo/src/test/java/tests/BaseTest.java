package tests;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
public class BaseTest {
    public WebDriver driver;
    public Properties prop;
    @BeforeMethod
    public void setUp() throws IOException {
        
        prop = new Properties();
        FileInputStream fis = new FileInputStream(
            System.getProperty("user.dir") + "/src/test/resources/config.properties"
        );
        prop.load(fis);
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-default-apps");
        
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.setExperimentalOption("excludeSwitches",
            new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Integer.parseInt(prop.getProperty("waitTime")))
        );
        
        driver.get(prop.getProperty("appUrl"));
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}