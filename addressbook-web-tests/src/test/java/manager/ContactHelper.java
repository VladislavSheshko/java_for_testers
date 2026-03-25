package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openHomePage() {
        if (!manager.isElementPresent(By.xpath("//span[contains(text(), 'Number of results')]"))) {
            manager.driver.findElement(By.linkText("home")).click();
        }
    }

    public void openContactCreation() {
        if (!manager.isElementPresent(By.name("theform"))) {
            manager.driver.findElement(By.linkText("add new")).click();
        }
    }

    public boolean isContactPresent() {
        openHomePage();
        return manager.isElementPresent(By.name("selected[]"));
    }

    public void createContact(ContactData contact) {
        openContactCreation();
        manager.driver.findElement(By.linkText("add new")).click();
        manager.driver.findElement(By.name("firstname")).sendKeys(contact.firstname());
        manager.driver.findElement(By.name("lastname")).sendKeys(contact.lastname());
        manager.driver.findElement(By.name("address")).sendKeys(contact.address());
        manager.driver.findElement(By.name("mobile")).sendKeys(contact.mobile());
        manager.driver.findElement(By.name("work")).sendKeys(contact.work());
        manager.driver.findElement(By.name("email")).sendKeys(contact.email());
        manager.driver.findElement(By.cssSelector("input[value='Enter']")).click();
        manager.driver.findElement(By.linkText("home page")).click();
    }

    public void removeContact() {
        openHomePage();
        manager.driver.findElement(By.name("selected[]")).click();
        manager.driver.findElement(By.name("delete")).click();
        manager.driver.findElement(By.linkText("home page")).click();
    }
}
