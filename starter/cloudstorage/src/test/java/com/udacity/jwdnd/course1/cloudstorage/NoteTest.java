package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTest {
    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static WebDriverWait webDriverWait;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private HomePage  homePage;


    private static final String username  = "lamiaewais";
    private static final String password  = "111111";
    private static final String firstname = "Lamia";
    private static final String lastname  = "ewais";
    private static final String noteTitle =  "Note Title";
    private static final String noteDescription =  "Note Description";
    private static final String updatedNoteTitle =  "Updated Note Title";
    private static final String updatedNoteDescription =  "Updated Note Description";

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 40);
    }

    @BeforeEach
    public void beforeEach() {
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        homePage = new HomePage(driver);
    }

    private void signUpAndLogin() {
        driver.get("http://localhost:" + port + "/signup");
        webDriverWait.until(_driver -> _driver.getCurrentUrl().equals("http://localhost:" + port + "/signup"));
        signupPage.signup(username, password, firstname, lastname);
        webDriverWait.until(_driver -> _driver.getCurrentUrl().equals("http://localhost:" + port + "/login"));
        loginPage.login(username, password);
    }

    @Order(1)
    @Test
    public void testNoteCreationAndVerifiesItIsDisplayed() {
        signUpAndLogin();
        webDriverWait.until(_driver -> _driver.getCurrentUrl().equals("http://localhost:" + port + "/home"));
        homePage.clickOnNoteTab();
        webDriverWait.until(_driver -> homePage.isNoteButtonDisplayed());
        homePage.clickOnAddNoteButton();
        webDriverWait.until(_driver -> homePage.isNoteModeDialogDisplayed());
        homePage.addNewNote(noteTitle, noteDescription);
        webDriverWait.until(_driver -> homePage.isNoteDisplayed(noteTitle, noteDescription));
    }

    @Order(2)
    @Test
    public void testUpdateNoteAndVerifiesTheChangesItIsDisplayed() {
        webDriverWait.until(_driver -> _driver.getCurrentUrl().equals("http://localhost:" + port + "/home/note"));
        homePage.clickOnNoteTab();
        webDriverWait.until(_driver -> homePage.isNoteButtonDisplayed());
        homePage.clickOnAddNoteButton();
        webDriverWait.until(_driver -> homePage.isNoteModeDialogDisplayed());
        homePage.addNewNote(noteTitle, noteDescription);
        webDriverWait.until(_driver -> homePage.isNoteDisplayed(noteTitle, noteDescription));
        homePage.clickOnEditButtonOfFirstNote();
        webDriverWait.until(_driver -> homePage.isNoteModeDialogDisplayed());
        homePage.updateNote(updatedNoteTitle, updatedNoteDescription);
        webDriverWait.until(_driver -> !homePage.isNoteModeDialogDisplayed());
        Assertions.assertTrue(homePage.isNoteDisplayed(updatedNoteTitle, updatedNoteDescription));
    }

    @Order(3)
    @Test
    public void testDeleteNoteAndVerifiesTheNoteIsNoLongerDisplayed() {
        homePage.clickOnNoteTab();
        webDriverWait.until(_driver -> homePage.isNoteButtonDisplayed());
        homePage.clickOnAddNoteButton();
        webDriverWait.until(_driver -> homePage.isNoteModeDialogDisplayed());
        homePage.addNewNote(noteTitle, noteDescription);
        webDriverWait.until(_driver -> homePage.isNoteDisplayed(noteTitle, noteDescription));
        homePage.clickOnDeletedButtonOfFirstNote();
        Assertions.assertFalse(homePage.isNoteDisplayed(updatedNoteTitle, updatedNoteDescription));
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }
}
