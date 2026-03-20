import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ContactRemovalTests {

    private static WebDriver driver;


    @BeforeEach
    public void setUp() {
        //Если переменная не проинициализирована - выполнить код по инициализации
        if (driver == null) {
            driver = new FirefoxDriver();
            //Кода выполнение кода java будет завершаться, в самом конце нужно автоматически закрыть WebDriver
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get("http://localhost/addressbook/");
            driver.manage().window().setSize(new Dimension(1098, 1096));
            driver.findElement(By.name("user")).sendKeys("admin");
            driver.findElement(By.name("pass")).sendKeys("secret");
            driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
        }
    }


    @Test
    public void canRemoveContact() {
        if (!isElementPresent(By.xpath("//span[contains(text(), 'Number of results')]"))) {
            driver.findElement(By.linkText("home")).click();
        }
        //нужно добавить условие, если таблица контактов пустая, создай новый контакт
        driver.findElement(By.name("selected[]")).click();
        driver.findElement(By.name("delete")).click();
        driver.findElement(By.linkText("home page")).click();
        driver.findElement(By.linkText("Logout")).click();
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }
}
