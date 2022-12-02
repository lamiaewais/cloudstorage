package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.*;

public class HomePage {
    @FindBy(id = "logoutButton")
    private WebElement logoutElement;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteElementButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "save-note-button")
    private WebElement noteSaveButton;

    @FindBy(id = "noteModal")
    private WebElement noteModalDialog;

    @FindBy(tagName = "th")
    private List<WebElement> notesTitlesCol;

    @FindBy(tagName = "td")
    private List<WebElement> notesDescriptionsCol;

    @FindBy(id = "noteControl")
    private List<WebElement> noteControlField;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialTab;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credentialModal")
    private WebElement credentialModalDialog;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "save-credential-button")
    private WebElement saveCredentialButton;

    @FindBy(id = "credential-row")
    private List<WebElement> credentialRows;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutElement.click();
    }

    public void clickOnNoteTab() {
        navNotesTab.click();
    }

    public void clickOnAddNoteButton() {
        addNoteElementButton.click();
    }

    public boolean isNoteButtonDisplayed() {
        return addNoteElementButton.isDisplayed();
    }

    public boolean isNoteModeDialogDisplayed() {
        return noteModalDialog.isDisplayed();
    }

    public void addNewNote(String title, String description) {
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.sendKeys(description);
        noteSaveButton.click();
    }

    public boolean isNoteDisplayed(String title, String description) {
        for (WebElement titleElement: notesTitlesCol) {
            if (titleElement.getText().trim().equalsIgnoreCase(title)) {
                for (WebElement descriptionElement: notesDescriptionsCol) {
                    if (descriptionElement.getText().trim().equalsIgnoreCase(description)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void clickOnEditButtonOfFirstNote() {
       Optional<WebElement> firstNoteField =  noteControlField
               .stream()
               .findFirst();

       if (firstNoteField.isPresent()) {
           WebElement editButton = firstNoteField.get().findElement(By.tagName("button"));
           editButton.click();
       }
    }

    public void clickOnDeletedButtonOfFirstNote() {
        Optional<WebElement> firstNoteField =  noteControlField
                .stream()
                .findFirst();

        if (firstNoteField.isPresent()) {
            WebElement deletedButton = firstNoteField.get().findElement(By.tagName("a"));
            deletedButton.click();
        }
    }

    public void updateNote(String title, String description) {
        noteTitleInput.clear();
        noteTitleInput.sendKeys(title);
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(description);
        noteSaveButton.click();
    }

    public void clickOnNavCredentialTab() {
        navCredentialTab.click();
    }

    public void clickOnAddNewCredentialButton() {
        addCredentialButton.click();
    }

    public boolean isAddNewCredentialButtonDisplayed() {
        return addCredentialButton.isDisplayed();
    }

    public boolean isCredentialModalDialogDisplayed() {
        return credentialModalDialog.isDisplayed();
    }

    public boolean isCredentialPasswordUnEncrypted(String plainPasswordTest) {
        return credentialPassword.getAttribute("value").trim().equalsIgnoreCase(plainPasswordTest);
    }

    public void addCredential(String url, String username, String password) {
        credentialUrlInput.sendKeys(url);
        credentialUsernameInput.sendKeys(username);
        credentialPassword.sendKeys(password);
        saveCredentialButton.click();
    }

    public int getDisplayedRowsSize() {
         return credentialRows.size();
    }

    public List<String> getDisplayedEncryptedCredentialPasswords() {
        List<String> passwords = new ArrayList<>(Collections.emptyList());

        for (WebElement credentialRow : credentialRows) {
            List<WebElement> col = credentialRow.findElements(By.tagName("td"));
            for (int j = 0; j < col.size(); j++) {
                if (j == 2) {
                    passwords.add(col.get(j).getText());
                }
            }
        }


        return passwords;
    }

    public void clickEditOnFirsCredential() {
        credentialRows.get(0).findElement(By.tagName("td")).findElement(By.tagName("button")).click();
    }

    public void clickDeleteFirsCredential() {
        credentialRows.get(0).findElement(By.tagName("td")).findElement(By.tagName("a")).click();
    }

    public void updateCredential(Credential credential) {
        credentialUrlInput.clear();
        credentialUrlInput.sendKeys(credential.getUrl());
        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(credential.getUsername());
        credentialPassword.clear();
        credentialPassword.sendKeys(credential.getPassword());
        saveCredentialButton.click();
    }

    public boolean isCredentialChangesDisplayed(Credential credential) {
        WebElement credentialRow = credentialRows.get(0);
        String firstRowUsername = credentialRow.findElements(By.tagName("td")).get(1).getText();
        String firstRowUrl = credentialRow.findElements(By.tagName("th")).get(0).getText();

        if (firstRowUrl.isBlank() || firstRowUsername.isBlank()) return false;

        return Objects.equals(firstRowUrl, credential.getUrl()) &&
                Objects.equals(firstRowUsername, credential.getUsername());
    }

}
