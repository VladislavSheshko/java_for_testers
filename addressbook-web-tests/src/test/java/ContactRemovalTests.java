import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import tests.TestBase;

public class ContactRemovalTests extends TestBase {




    @Test
    public void canRemoveContact() {
        openHomePage();
        //если таблица контактов пустая, создай новый контакт
        if (!isElementPresent(By.name("selected[]"))) {
            createContact("Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail");
        }
        driver.findElement(By.name("selected[]")).click();
        driver.findElement(By.name("delete")).click();
        driver.findElement(By.linkText("home page")).click();
    }

}
