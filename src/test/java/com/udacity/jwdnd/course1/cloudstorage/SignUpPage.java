package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage
{
    @FindBy(id = "inputFirstName")
    private WebElement firstNameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastNameInput;

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "signUpButton")
    private WebElement submitButton;

    public SignUpPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    public void signUpUser(String first, String last, String username, String password)
    {
        firstNameInput.sendKeys(first);
        lastNameInput.sendKeys(last);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

        submitButton.click();
    }
}
