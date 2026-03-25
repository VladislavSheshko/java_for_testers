package tests;

import manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

    protected static ApplicationManager app;
    protected static WebDriver driver;

    /*
    @BeforeEach
    public void setUp() {
        if (app == null) {
            app = new ApplicationManager();
            app.init();
        }
    }

     */

    @BeforeEach
    public void setUp() {
        //Если переменная не проинициализирована - выполнить код по инициализации
        if (driver == null) {
            driver = new FirefoxDriver();
            //Кода выполнение кода java будет завершаться, в самом конце нужно автоматически закрыть WebDriver
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get("http://localhost/addressbook/");
            driver.manage().window().setSize(new Dimension(1098, 1096));
            driver.findElement(By.name("user")).click();
            driver.findElement(By.name("user")).sendKeys("admin");
            driver.findElement(By.name("pass")).sendKeys("secret");
            driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    protected void createContact(String firstname, String lastname, String address, String mobile, String work, String email) {
        driver.findElement(By.linkText("add new")).click();
        driver.findElement(By.name("firstname")).sendKeys(firstname);
        driver.findElement(By.name("lastname")).sendKeys(lastname);
        driver.findElement(By.name("address")).sendKeys(address);
        driver.findElement(By.name("mobile")).sendKeys(mobile);
        driver.findElement(By.name("work")).sendKeys(work);
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.cssSelector("input[value='Enter']")).click();
        driver.findElement(By.linkText("home page")).click();
    }

    protected void openContactCreation() {
        if (!isElementPresent(By.name("theform"))) {
            driver.findElement(By.linkText("add new")).click();
        }
    }

    protected void openHomePage() {
        if (!isElementPresent(By.xpath("//span[contains(text(), 'Number of results')]"))) {
            driver.findElement(By.linkText("home")).click();
        }
    }
}
