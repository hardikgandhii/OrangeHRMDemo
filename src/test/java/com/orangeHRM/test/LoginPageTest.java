package com.orangeHRM.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

/**
 * This class contains tests related to the Login Page of OrangeHRM.
 */
public class LoginPageTest extends BaseClass {

    private LoginPage loginpage;
    private HomePage homepage;

    /**
     * Initializes page objects before each test.
     */
    @BeforeMethod
    public void setupTest() {
        System.out.println("Setting up page objects for LoginPageTest");
        loginpage = new LoginPage(getDriver());
        homepage = new HomePage(getDriver());
    }

    /**
     * Test to verify successful login functionality.
     */
    @Test
    public void verifySuccessfulLoginTest() throws Exception {
        System.out.println("Starting valid login test");
        loginpage.login("Admin", "admin123");

        // Assertion to verify Admin tab is displayed after login
        Assert.assertTrue(homepage.verifyIsAdminTabVisible(), "Admin tab should be visible after login");

        homepage.logout();
        System.out.println("Logout successful");
    }

    /**
     * Test to verify login fails with invalid credentials and error message is shown.
     */
    @Test
    public void verifyInvalidLoginTest() {
        System.out.println("Starting invalid login test");
        loginpage.login("Admin", "admin12345");

        String expectedErrorMsg = "Invalid credentials";

        // Assertion to verify correct error message
        Assert.assertTrue(loginpage.verifyErrorMsg(expectedErrorMsg), "Test Failed: Error message mismatch");
    }
}
