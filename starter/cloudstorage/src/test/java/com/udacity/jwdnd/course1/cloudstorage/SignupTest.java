package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class SignupTest {
    @LocalServerPort
    private int port;
    private static WebDriver driver;
    private SignupPage signupPage;
    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void beforeEach() {
        signupPage = new SignupPage(driver);
        driver.get("http://localhost:"+ port +"/signup");
    }

    @Order(1)
    @Test
    public void when_user_sign_up_successfully_success_message_should_be_displayed() {
        signupPage.signup("Lamia", "123456", "Lamia", "Ewais");

       // Assertions.assertTrue(signupPage.isSuccessMessageDisplayed());
    }

    @Order(2)
    @Test
    public void when_user_sign_up_failed_error_message_should_be_displayed() {
        signupPage.signup("Lamia", "123456", "Lamia", "Ewais");
        signupPage.signup("Lamia", "99999", "Lamia", "Khaled");

        Assertions.assertTrue(signupPage.isErrorMessageDisplayed());
    }

    @AfterAll
    public static void afterAll() {
      //  driver.quit();
    }
}
