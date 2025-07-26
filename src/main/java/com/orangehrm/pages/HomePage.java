package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

/**
 * Page Object Model for the Home Page (Post-login landing page) of OrangeHRM.
 */
public class HomePage {

    private ActionDriver actiondriver;

    // Locators for top menu items and logout
    private By adminTab = By.xpath("//li[1]//a[1]//span[1]");
    private By userDropdownTab = By.className("oxd-userdropdown-tab");
    private By logoutBtn = By.xpath("//a[text()='Logout']");

    /**
     * Constructor initializes the ActionDriver using BaseClass
     * @param driver the WebDriver instance
     */
    public HomePage(WebDriver driver) {
        this.actiondriver = BaseClass.getActionDriver();
    }

    /**
     * Verifies if the Admin tab is visible, indicating successful login
     * @return true if Admin tab is displayed
     */
    public boolean verifyIsAdminTabVisible() {
        return actiondriver.isDisplayed(adminTab);
    }

    /**
     * Logs out the user from the application using explicit waits
     */
    public void logout() {
        // Wait until user dropdown tab is clickable and click it
        actiondriver.waitForElementtoBeClickable(userDropdownTab);
        actiondriver.click(userDropdownTab);

        // Wait until logout button is clickable and click it
        actiondriver.waitForElementtoBeClickable(logoutBtn);
        actiondriver.click(logoutBtn);
    }
}
