package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openHomePage() {
        if (!manager.isElementPresent(By.xpath("//span[contains(text(), 'Number of results')]"))) {
            click(By.linkText("home"));
        }
    }

    public void openContactCreation() {
        if (!manager.isElementPresent(By.name("theform"))) {
            initContactCreation();
        }
    }

    public boolean isContactPresent() {
        openHomePage();
        return manager.isElementPresent(By.name("selected[]"));
    }

    public void createContact(ContactData contact) {
        openContactCreation();
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        returnToContactPage();
    }

    private void submitContactCreation() {
        manager.driver.findElement(By.cssSelector("input[value='Enter']")).click();
    }

    private void initContactCreation() {
        manager.driver.findElement(By.linkText("add new")).click();
    }

    public void removeContact() {
        openHomePage();
        selectContact();
        removeSelectedContact();
        returnToContactPage();
    }

    private void removeSelectedContact() {
        click(By.name("delete"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstname());
        type(By.name("lastname"), contact.lastname());
        type(By.name("address"), contact.address());
        type(By.name("mobile"), contact.mobile());
        type(By.name("work"), contact.work());
        type(By.name("email"), contact.email());
    }

    public void modifyContact(ContactData modifiedContact) {
        openHomePage();
        selectContact();
        initContactModification();
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToContactPage();
    }

    private void selectContact() {
        click(By.name("selected[]"));
    }

    public int getCount() {
        openHomePage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    private void returnToContactPage() {
        manager.driver.findElement(By.linkText("home page")).click();
    }

    private void submitContactModification() {
        click(By.xpath("//input[@name='update']"));
    }

    private void initContactModification() {
        click(By.xpath("//a[contains(@href,'edit.php?id=')]"));
    }
}
