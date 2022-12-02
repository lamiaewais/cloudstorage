package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTest {
    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static WebDriverWait webDriverWait;
    private static SignupPage signupPage;
    private static LoginPage loginPage;
    private static HomePage homePage;

    private static final String username  = "lamiaewais";
    private static final String password  = "111111";
    private static final String firstname = "Lamia";
    private static final String lastname  = "ewais";
    private static final List<Credential> credentials = List.of(
            new Credential(null, "www.url1.com", "lamia1", null, "11111", null),
            new Credential(null, "www.url2.com", "lamia2", null, "22222", null),
            new Credential(null, "www.url3.com", "lamia3", null, "33333", null),
            new Credential(null, "www.url4.com", "lamia4", null, "44444", null)
    );

    private static final Credential newCredential  = new Credential(
            null, "www.new.com",
            "newUsername", null,
            "newPassword", null
    );
    private static final List<String> unEncryptedPasswords = credentials
            .stream().flatMap(c -> Stream.of(c.getPassword()))
            .collect(Collectors.toList());

    @BeforeAll
    public static void  beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 40);
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);

    }

    private void signupAnLogin() {
        driver.get("http://localhost:" + port + "/signup");
        webDriverWait.until(_driver -> _driver.getCurrentUrl().contains("signup"));
        signupPage.signup(username, password, firstname, lastname);
        webDriverWait.until(_driver -> _driver.getCurrentUrl().contains("login"));
        Assertions.assertTrue(loginPage.isSuccessMessageDisplayed());
        loginPage.login(username, password);
        webDriverWait.until(_driver -> _driver.getCurrentUrl().contains("home"));
    }

    @Order(1)
    @Test
    public void testCreateMultipleCredentialsAndVerifiesThatTheDisplayedPasswordsAreEncrypted() {
        signupAnLogin();
        homePage.clickOnNavCredentialTab();

        for (Credential credential : credentials) {
            webDriverWait.until(_driver -> homePage.isAddNewCredentialButtonDisplayed());
            homePage.clickOnAddNewCredentialButton();
            webDriverWait.until(_driver -> homePage.isCredentialModalDialogDisplayed());
            homePage.addCredential(credential.getUrl(), credential.getUsername(), credential.getPassword());
        }

        webDriverWait.until(_driver -> !homePage.isCredentialModalDialogDisplayed());

        Assertions.assertEquals(credentials.size(), homePage.getDisplayedRowsSize());
        Assertions.assertFalse(
                unEncryptedPasswords
                        .stream()
                        .anyMatch(unEncryptedPassword -> homePage.getDisplayedEncryptedCredentialPasswords()
                                .contains(unEncryptedPassword))
        );
    }

    @Order(2)
    @Test
    public void testEditExistingCredentialAnVerifiesThatTheChangedAreDisplayedAndTheUnEncryptedPasswordIsDisplayed() {
        homePage.clickEditOnFirsCredential();
        webDriverWait.until(_driver -> homePage.isCredentialModalDialogDisplayed());
        Assertions.assertTrue(homePage.isCredentialPasswordUnEncrypted(credentials.get(0).getPassword()));
        homePage.updateCredential(newCredential);
        webDriverWait.until(_driver -> !homePage.isCredentialModalDialogDisplayed());
        webDriverWait.until(_driver -> homePage.isCredentialChangesDisplayed(newCredential));
    }

    @Order(3)
    @Test
    public void testDeleteExistingCredentialAnVerifiesThatTheChangedAreDisplayed() {
        homePage.clickDeleteFirsCredential();
        webDriverWait.until(driver -> homePage.getDisplayedRowsSize() < credentials.size());
        Assertions.assertEquals(3, homePage.getDisplayedRowsSize());
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }
}
