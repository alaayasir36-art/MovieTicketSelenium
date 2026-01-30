package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TicketCalculatorSeleniumTest {

    private WebDriver driver;

    private void slowDown() {
        try {
            Thread.sleep(700); // 1.5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    
    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");       // run without GUI
        options.addArguments("--disable-gpu");    // recommended
        options.addArguments("--no-sandbox");     // required in CI
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        File html = new File("web/index.html");
        driver.get(html.toURI().toString());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private String calculateForAge(int age) {
        WebElement ageInput = driver.findElement(By.id("age"));
        WebElement button = driver.findElement(By.id("calculate"));

        ageInput.clear();
        slowDown();
        ageInput.sendKeys(String.valueOf(age));
        slowDown();
        button.click();
        slowDown();

        return driver.findElement(By.id("result")).getText();
    }

    @Test
    void testChildPrice() {
        assertEquals("Price: $5", calculateForAge(10));
    }

    @Test
    void testAdultPrice() {
        assertEquals("Price: $10", calculateForAge(30));
    }

    @Test
    void testSeniorPrice() {
        assertEquals("Price: $6", calculateForAge(65));
    }

    @Test
    void testNegativeAge() {
        assertEquals("Invalid age", calculateForAge(-1));
    }
}