import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AddNewContactTests {

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
            driver.findElement(By.name("user")).click();
            driver.findElement(By.name("user")).sendKeys("admin");
            driver.findElement(By.name("pass")).sendKeys("secret");
            driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
        }
    }

    @Test
    public void canAddNewContact() {

        driver.findElement(By.linkText("add new")).click();
        driver.findElement(By.name("firstname")).sendKeys("Шешко");
        driver.findElement(By.name("middlename")).sendKeys("103");
        driver.findElement(By.name("lastname")).sendKeys("Шешко");
        driver.findElement(By.name("nickname")).sendKeys("В_Ш");
        driver.findElement(By.name("company")).sendKeys("Перфоманс");
        driver.findElement(By.name("address")).sendKeys("Челябинск");
        driver.findElement(By.name("mobile")).sendKeys("+7");
        driver.findElement(By.name("work")).sendKeys("QA");
        driver.findElement(By.name("email")).sendKeys("1@mail");
        {
            WebElement dropdown = driver.findElement(By.name("bday"));
            dropdown.findElement(By.xpath("//option[. = '29']")).click();
        }
        {
            WebElement dropdown = driver.findElement(By.name("bmonth"));
            dropdown.findElement(By.xpath("//option[. = 'August']")).click();
        }
        driver.findElement(By.name("byear")).sendKeys("1995");
        driver.findElement(By.cssSelector("input[value='Enter']")).click();
        driver.findElement(By.linkText("home page")).click();
        driver.findElement(By.linkText("Logout")).click();
    }
}
