package ru.movie.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SeleniumLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "http://localhost:8080";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // без открытия браузера
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldLoginSuccessfully() {
        // Переходим на страницу логина
        driver.get(BASE_URL + "/login");

        // Вводим credentials
        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username"))
        );
        usernameInput.sendKeys(USERNAME);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);

        // Нажимаем кнопку входа
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        // Проверяем что перешли на страницу фильмов
        wait.until(ExpectedConditions.urlContains("/view/movies"));
        assertTrue(driver.getCurrentUrl().contains("/view/movies"));
        assertTrue(driver.getTitle().contains("Movies"));
    }

    @Test
    void shouldLogoutSuccessfully() {
        // Сначала логинимся
        driver.get(BASE_URL + "/login");

        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username"))
        );
        usernameInput.sendKeys(USERNAME);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/view/movies"));

        // Выходим
        driver.get(BASE_URL + "/logout");

        // Проверяем что перешли на страницу логина
        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    void shouldNotLoginWithInvalidCredentials() {
        driver.get(BASE_URL + "/login");

        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username"))
        );
        usernameInput.sendKeys("wronguser");
        driver.findElement(By.name("password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        // Проверяем что остались на странице логина с ошибкой
        wait.until(ExpectedConditions.urlContains("/login?error"));
        assertTrue(driver.getCurrentUrl().contains("error"));
    }
}