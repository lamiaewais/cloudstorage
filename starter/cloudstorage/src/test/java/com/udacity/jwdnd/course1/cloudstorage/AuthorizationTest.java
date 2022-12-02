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
public class AuthorizationTest {
    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static WebDriverWait webDriverWait;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private HomePage homePage;

    private static final String username = "lamiaewais";
    private static final String password = "111111";
    private static final String firstname = "Lamia";
    private static final String lastname = "ewais";

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 40);
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/home");
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        homePage = new HomePage(driver);
    }

    @Order(1)
    @Test
    public void anUnAuthorizedUserShouldAccessLoginAndSignupPage() {
        webDriverWait.until(driver1 -> driver1.getCurrentUrl().equals("http://localhost:" + port + "/login"));
        Assertions.assertEquals(driver.getTitle(), "Login");
        loginPage.navigateToSignupPage();
        Assertions.assertEquals(driver.getTitle(), "Sign Up");
    }

    @Order(3)
    @Test
    public void testUserSignUpLoginHomePageAccessLogOutHomePageRestrict() {
        webDriverWait.until(driver1 -> driver1.getCurrentUrl().equals("http://localhost:" + port + "/login"));
        loginPage.navigateToSignupPage();
        signupPage.signup(username, password, firstname, lastname);
        webDriverWait.until(driver1 -> driver1.getCurrentUrl().equals("http://localhost:" + port + "/login"));
        Assertions.assertTrue(loginPage.isSuccessMessageDisplayed());
        loginPage.login(username, password);
        webDriverWait.until(driver1 -> driver1.getCurrentUrl().equals("http://localhost:" + port + "/home"));
        Assertions.assertEquals(driver.getTitle(),"Home");
        homePage.logout();
        webDriverWait.until(driver1 -> driver1.getCurrentUrl().equals("http://localhost:" + port + "/login?logout"));
        Assertions.assertTrue(loginPage.isLogoutMessageDisplayed());
        driver.get("http://localhost:" + port + "/home");
        webDriverWait.until(driver1 -> driver1.getCurrentUrl().equals("http://localhost:" + port + "/login"));
        Assertions.assertEquals(driver.getTitle(), "Login");
    }

    @AfterAll
    private static void afterAll() {
        driver.quit();
    }
}
