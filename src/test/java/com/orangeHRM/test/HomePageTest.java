package com.orangeHRM.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

/**
 * This class contains tests related to the Home Page of OrangeHRM
 * like checking if Admin tab is visible after login.
 */
public class HomePageTest extends BaseClass {

    private LoginPage loginpage;
    private HomePage homepage;

    /**
     * Initializes page objects before each test.
     */
    @BeforeMethod
    public void setupTest() {
        System.out.println("Setting up page objects for HomePageTest");
        loginpage = new LoginPage(getDriver());
        homepage = new HomePage(getDriver());
    }

    /**
     * Test to verify if Admin tab is visible after successful login.
     */
    @Test
    public void verifyAdminTabVisibleTest() {
        loginpage.login("Admin", "admin123");
        System.out.println("Login successful");

        // Assertion to verify Admin tab is displayed
        Assert.assertTrue(homepage.verifyIsAdminTabVisible(), "Admin tab is not visible");
        System.out.println("Admin tab is visible");
    }
}
