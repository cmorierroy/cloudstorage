package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage
{
    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id="addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id="note-title")
    private WebElement noteTitleInput;

    @FindBy(id="noteTitle")
    private WebElement noteTitleText;

    @FindBy(id="note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id="noteDescription")
    private WebElement noteDescriptionText;

    @FindBy(id="noteSubmitButton")
    private WebElement noteSubmitButton;

    @FindBy(id="credential-url")
    private WebElement credentialUrlInput;

    @FindBy(id="credentialUrl")
    private WebElement credentialUrlText;

    @FindBy(id="credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id="credentialUsername")
    private WebElement credentialUsernameText;

    @FindBy(id="credential-password")
    private WebElement credentialPasswordInput;

    @FindBy(id="credentialPassword")
    private WebElement credentialPasswordText;

    @FindBy(id="credentialSubmitButton")
    private WebElement credentialSubmitButton;

    public HomePage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    public void logout()
    {
        logoutButton.click();
    }

    //NOTES
    public void goToNotesTab()
    {
        navNotesTab.click();
    }
    public void inputNoteInfo(String title, String description)
    {
        noteTitleInput.clear();
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(description);

        noteSubmitButton.click();
    }

    public String noteTitleText()
    {
        return noteTitleText.getText();
    }

    public String noteDescriptionText()
    {
        return noteDescriptionText.getText();
    }

    //CREDENTIALS
    public void inputCredentialInfo(String url, String username, String password)
    {
        credentialUrlInput.clear();
        credentialUrlInput.sendKeys(url);
        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(username);
        credentialPasswordInput.clear();
        credentialPasswordInput.sendKeys(password);

        credentialSubmitButton.click();
    }

    public void goToCredentialsTab()
    {
        navCredentialsTab.click();
    }

    public String credentialUrlText()
    {
        return credentialUrlText.getText();
    }

    public String credentialUsernameText()
    {
        return credentialUsernameText.getText();
    }

    public String credentialPasswordText()
    {
        return credentialPasswordText.getText();
    }

    public String credentialPasswordInputText() { return credentialPasswordInput.getAttribute("value");}

}
