package avicTests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.AssertJUnit.*;

public class AvicTestsSet {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void amountOfGoodsInCart() {
        driver.findElement(By.xpath("//input[@placeholder='Поиск ']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Поиск ']")).sendKeys("iPhone 12 mini");
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElements(By.xpath("//div[@class='container-main']")).get(0).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[@class='main-btn main-btn--green main-btn--large']")).click();
        driver.findElement(By.xpath("//button[@class='fancybox-button fancybox-close-small']")).click();
        WebElement cartWithAmount = driver.findElement(
            By.xpath("//div[contains(@class,'header-bottom')]//div[contains(@class, 'active-cart-item')]"));
        assertEquals("1", cartWithAmount.getText());
    }

    @Test(priority = 2)
    public void checkThatEveryTileHasBuyButton() {
        List<WebElement> buyButton = new ArrayList<>();
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        driver.findElement(By.xpath("//li[@class='parent js_sidebar-item']//a[@href='https://avic.ua/telefonyi-i-aksessuaryi']")).click();
        driver.findElement(By.xpath("//div[@class='brand-box__title']//a[@href='https://avic.ua/smartfonyi']")).click();
        wait1.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='item-prod col-lg-3']")));
        buyButton.addAll(driver.findElements(By.xpath("//a[@class='prod-cart__buy']")));
        for (WebElement element : buyButton) {
            assertNotNull(element);
        }
    }

    @Test(priority = 3)
    public void checkProductsFoundByQuery() {
        driver.findElement(xpath("//input[@placeholder='Поиск ']")).click();
        driver.findElement(xpath("//input[@placeholder='Поиск ']")).sendKeys("Samsung");
        driver.findElement(xpath("//button[@class='button-reset search-btn']")).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(xpath("//div[@class='prod-cart__descr']")));
        List<WebElement> testingItems = new ArrayList<>();
        testingItems.addAll(driver.findElements(xpath("//div[@class='prod-cart__descr']")));
        for (WebElement element: testingItems) {
            String description = element.getText();
            assertTrue(description.contains("Samsung"));
        }

    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
