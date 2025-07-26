package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.testng.annotations.*;

import com.orangehrm.actiondriver.ActionDriver;

public class BaseClass {

    protected static Properties prop;
    protected static WebDriver driver;
    private static ActionDriver actionDriver;

    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    /**
     * Load configuration from config.properties before the suite starts.
     */
    @BeforeSuite
    public void loadConfig() throws IOException {
        prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            prop.load(fis);
            logger.info("Configuration loaded successfully.");
        } catch (IOException e) {
            logger.error("Failed to load config.properties: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Launches the browser as per the 'browser' property from config file.
     */
    public void launchBrowser() {
        String browser = prop.getProperty("browser");

        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                logger.error("Browser not supported: " + browser);
                throw new IllegalArgumentException("Browser not supported");
        }
        logger.info("Launched browser: " + browser);
    }

    /**
     * Configures browser settings like implicit wait, window size, and opens URL.
     */
    private void configureBrowser() {
        try {
            int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            driver.manage().window().maximize();
            driver.get(prop.getProperty("url"));

            logger.info("Configured browser with implicitWait=" + implicitWait + "s and opened URL: " + prop.getProperty("url"));
        } catch (Exception e) {
            logger.error("Browser configuration failed: " + e.getMessage());
        }
    }

    /**
     * Provides the global WebDriver instance.
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            logger.error("WebDriver is not initialized!");
            throw new IllegalStateException("WebDriver is not initialized");
        }
        return driver;
    }

    /**
     * Provides the singleton ActionDriver instance.
     */
    public static ActionDriver getActionDriver() {
        if (actionDriver == null) {
            logger.error("ActionDriver is not initialized!");
            throw new IllegalStateException("ActionDriver is not initialized");
        }
        return actionDriver;
    }

    /**
     * Initializes browser and ActionDriver before each test method.
     */
    @BeforeMethod
    public void setup() throws IOException {
        logger.info("🔧 Test setup started...");
        launchBrowser();
        configureBrowser();
        staticWait(2);

        if (actionDriver == null) {
            actionDriver = new ActionDriver(driver);
            logger.info("ActionDriver initialized.");
        }
    }

    /**
     * Quits WebDriver and resets objects after each test method.
     */
    @AfterMethod
    public void teardown() {
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully.");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver: " + e.getMessage());
            }
        }

        // Reset shared objects
        driver = null;
        actionDriver = null;
        logger.info("Test cleanup complete.");
    }

    /**
     * Returns loaded configuration properties.
     */
    public static Properties getProp() {
        return prop;
    }

    /**
     * Custom static wait using LockSupport (better than Thread.sleep).
     */
    public void staticWait(int seconds) {
        logger.debug("Static wait for " + seconds + " seconds");
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }
}
