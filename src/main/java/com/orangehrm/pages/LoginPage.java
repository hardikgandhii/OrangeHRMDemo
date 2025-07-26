package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

/**
 * Page Object Model for the Login Page of OrangeHRM.
 */
public class LoginPage {

    private ActionDriver actiondriver;

    // Locators for login fields and error message
    private By usernameField = By.name("username");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginBtn = By.cssSelector("button[type='submit']");
    private By errorMsg = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");

    /**
     * Constructor to initialize ActionDriver using BaseClass
     * @param driver the WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        this.actiondriver = BaseClass.getActionDriver();
    }

    /**
     * Logs in using given username and password
     * @param username user login name
     * @param password user password
     */
    public void login(String username, String password) {
        actiondriver.enterText(usernameField, username);
        actiondriver.enterText(passwordField, password);
        actiondriver.click(loginBtn);
    }

    /**
     * Checks if the error message is displayed
     * @return true if error message is visible
     */
    public boolean isErrorMsgDisplayed() {
        return actiondriver.isDisplayed(errorMsg);
    }

    /**
     * Gets the text of the error message
     * @return the error message string
     */
    public String getTextErrorMsgDisplayed() {
        return actiondriver.getText(errorMsg);
    }

    /**
     * Verifies that the displayed error message matches the expected one
     * @param expectedError the expected message text
     * @return true if they match, false otherwise
     */
    public boolean verifyErrorMsg(String expectedError) {
        return actiondriver.compareText(errorMsg, expectedError);
    }
}
