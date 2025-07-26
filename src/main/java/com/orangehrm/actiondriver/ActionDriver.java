package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import com.orangehrm.base.BaseClass;

public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    private static final Logger logger = LogManager.getLogger(ActionDriver.class);

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        this.actions = new Actions(driver);
        logger.info("WebDriver and wait initialized with timeout: " + explicitWait + " seconds");
    }

    public void click(By by) {
        try {
            Thread.sleep(1000);
            driver.findElement(by).click();
            logger.info("Clicked element: " + by);
        } catch (Exception e) {
            logger.error("Click failed on element: " + by + " | Error: " + e.getMessage());
        }
    }

    public void enterText(By by, String value) {
        try {
            waitForElementtoBeVisible(by);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
            logger.info("Entered text: '" + value + "' into element: " + by);
        } catch (Exception e) {
            logger.error("Failed to enter text into element: " + by + " | Error: " + e.getMessage());
        }
    }

    public String getText(By by) {
        try {
            waitForElementtoBeVisible(by);
            String text = driver.findElement(by).getText();
            logger.info("Fetched text: '" + text + "' from element: " + by);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + by + " | Error: " + e.getMessage());
            return "";
        }
    }

    public boolean compareText(By by, String expectedText) {
        try {
            waitForElementtoBeVisible(by);
            String actualText = driver.findElement(by).getText();
            if (expectedText.equals(actualText)) {
                logger.info("Text match: expected='" + expectedText + "', actual='" + actualText + "'");
                return true;
            } else {
                logger.warn("Text mismatch: expected='" + expectedText + "', actual='" + actualText + "'");
                return false;
            }
        } catch (Exception e) {
            logger.error("Text comparison failed: " + by + " | Error: " + e.getMessage());
            return false;
        }
    }

    public boolean isDisplayed(By by) {
        try {
            waitForElementtoBeVisible(by);
            boolean displayed = driver.findElement(by).isDisplayed();
            logger.info("Element visibility for " + by + ": " + displayed);
            return displayed;
        } catch (Exception e) {
            logger.error("Element not visible: " + by + " | Error: " + e.getMessage());
            return false;
        }
    }

    public void waitForPageLoad(int timeOutInSec) {
        try {
            wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(
                wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
            );
            logger.info("Page loaded completely.");
        } catch (Exception e) {
            logger.error("Page load timeout after " + timeOutInSec + " seconds | Error: " + e.getMessage());
        }
    }

    public void ScrolltoElement(By by) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(by);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element: " + by);
        } catch (Exception e) {
            logger.error("Scroll to element failed: " + by + " | Error: " + e.getMessage());
        }
    }

    public void waitForElementtoBeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
            logger.info("Element is clickable: " + by);
        } catch (Exception e) {
            logger.error("Element not clickable: " + by + " | Error: " + e.getMessage());
        }
    }

    public void waitForElementtoBeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            logger.info("Element is visible: " + by);
        } catch (Exception e) {
            logger.error("Element not visible: " + by + " | Error: " + e.getMessage());
        }
    }

    // Optional Utility Methods

    public String getAttribute(By by, String attribute) {
        try {
            String value = driver.findElement(by).getAttribute(attribute);
            logger.info("Attribute '" + attribute + "' value from " + by + ": " + value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get attribute '" + attribute + "' from element: " + by + " | Error: " + e.getMessage());
            return "";
        }
    }

    public void clear(By by) {
        try {
            driver.findElement(by).clear();
            logger.info("Cleared text from element: " + by);
        } catch (Exception e) {
            logger.error("Failed to clear element: " + by + " | Error: " + e.getMessage());
        }
    }

    public void selectByVisibleText(By by, String text) {
        try {
            Select select = new Select(driver.findElement(by));
            select.selectByVisibleText(text);
            logger.info("Selected '" + text + "' from dropdown: " + by);
        } catch (Exception e) {
            logger.error("Dropdown selection failed: " + by + " | Error: " + e.getMessage());
        }
    }

    public void hoverOverElement(By by) {
        try {
            WebElement element = driver.findElement(by);
            actions.moveToElement(element).perform();
            logger.info("Hovered over element: " + by);
        } catch (Exception e) {
            logger.error("Hover action failed: " + by + " | Error: " + e.getMessage());
        }
    }

    public void uploadFile(By by, String filePath) {
        try {
            driver.findElement(by).sendKeys(filePath);
            logger.info("Uploaded file from path: " + filePath + " to element: " + by);
        } catch (Exception e) {
            logger.error("File upload failed: " + by + " | Error: " + e.getMessage());
        }
    }
    
 // Add these methods with logger support:

    public void switchToFrame(By by) {
        try {
            WebElement frameElement = driver.findElement(by);
            driver.switchTo().frame(frameElement);
            logger.info("Switched to frame: " + by);
        } catch (Exception e) {
            logger.error("Frame switch failed for: " + by + " | Error: " + e.getMessage());
        }
    }

    public void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
            logger.info("Switched back to default content.");
        } catch (Exception e) {
            logger.error("Switch to default content failed | Error: " + e.getMessage());
        }
    }

    public void acceptAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            logger.info("Alert accepted.");
        } catch (Exception e) {
            logger.error("Accept alert failed | Error: " + e.getMessage());
        }
    }

    public void dismissAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            logger.info("Alert dismissed.");
        } catch (Exception e) {
            logger.error("Dismiss alert failed | Error: " + e.getMessage());
        }
    }

    public String getAlertText() {
        try {
            Alert alert = driver.switchTo().alert();
            String text = alert.getText();
            logger.info("Fetched alert text: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Get alert text failed | Error: " + e.getMessage());
            return "";
        }
    }
}
