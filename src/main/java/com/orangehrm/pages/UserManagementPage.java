package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class UserManagementPage {

    private ActionDriver actiondriver;

    private By adminTab = By.xpath("//span[text()='Admin']");
    private By systemUsersHeader = By.xpath("//h5[text()='System Users']");
    private By addUserBtn = By.xpath("//button[normalize-space()='Add']");
    private By usernameField = By.xpath("//label[text()='Username']/following::input[1]");
    private By saveBtn = By.xpath("//button[normalize-space()='Save']");

    public UserManagementPage(WebDriver driver) {
        this.actiondriver = BaseClass.getActionDriver();
    }

    public void goToUserManagement() {
        actiondriver.click(adminTab);
    }

    public boolean isUserManagementPageLoaded() {
        return actiondriver.isDisplayed(systemUsersHeader);
    }

    public void clickAddUser() {
        actiondriver.click(addUserBtn);
    }

    public void enterUsername(String username) {
        actiondriver.enterText(usernameField, username);
    }

    public void clickSave() {
        actiondriver.click(saveBtn);
    }
}
