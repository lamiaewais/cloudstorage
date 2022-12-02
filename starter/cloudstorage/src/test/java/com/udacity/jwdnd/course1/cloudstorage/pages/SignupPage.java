package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "inputFirstName")
    private WebElement firstnameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastnameInput;

    @FindBy(id = "buttonSignUp")
    private WebElement signupButton;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id = "login")
    private WebElement login;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String username, String password, String firstname, String lastname) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        firstnameInput.sendKeys(firstname);
        lastnameInput.sendKeys(lastname);
        signupButton.click();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public void navigateToLoginPage() {
        login.click();
    }
}
