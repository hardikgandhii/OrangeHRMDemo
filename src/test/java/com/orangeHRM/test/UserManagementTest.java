package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.UserManagementPage;

public class UserManagementTest extends BaseClass {

    private LoginPage loginpage;
    private UserManagementPage userMgmtPage;

    @BeforeMethod
    public void setupTest() {
        loginpage = new LoginPage(getDriver());
        userMgmtPage = new UserManagementPage(getDriver());
    }

    @Test
    public void verifyNavigateToUserManagement() {
        loginpage.login("Admin", "admin123");
        userMgmtPage.goToUserManagement();
        Assert.assertTrue(userMgmtPage.isUserManagementPageLoaded(), "System Users header not found!");
    }
}
