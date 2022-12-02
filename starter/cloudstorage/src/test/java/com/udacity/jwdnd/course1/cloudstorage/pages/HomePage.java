package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.Optional;

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
    private List<WebElement> notesTitlesRows;

    @FindBy(tagName = "td")
    private List<WebElement> notesDescriptionsRows;

    @FindBy(id = "noteControl")
    private List<WebElement> noteControlField;

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
        for (WebElement titleElement: notesTitlesRows) {
            if (titleElement.getText().trim().equalsIgnoreCase(title)) {
                for (WebElement descriptionElement: notesDescriptionsRows) {
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
}
