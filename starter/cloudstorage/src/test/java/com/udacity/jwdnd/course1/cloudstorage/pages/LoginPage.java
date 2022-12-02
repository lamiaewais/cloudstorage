package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(id = "sign_up")
    private WebElement signup;

    @FindBy(id = "logout-msg")
    private WebElement logoutMessage;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        loginButton.click();
    }

    public boolean isSuccessMessageDisplayed() {
        return successMessage.isDisplayed();
    }

    public void navigateToSignupPage() {
        signup.click();
    }

    public boolean isLogoutMessageDisplayed() {
        return logoutMessage.isDisplayed();
    }
}
