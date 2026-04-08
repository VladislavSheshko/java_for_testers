package manager;

import model.ContactData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

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

    public void removeContact(ContactData contact) {
        openHomePage();
        selectContact(contact);
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
        attach(By.name("photo"), contact.photo());
    }

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openHomePage();
        //selectContact(contact);
        initContactModification(contact);
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToContactPage();
    }

    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[name='selected[]'][value='%s']", contact.id())));
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

    private void initContactModification(ContactData contact) {
        click(By.xpath(String.format("//a[contains(@href,'edit.php?id=%s')]", contact.id())));
    }

    public List<ContactData> getList() {
        var contacts = new ArrayList<ContactData>();
        var rows = manager.driver.findElements(By.cssSelector("table#maintable tbody tr[name='entry']"));
        for (var row : rows) {
            var checkbox = row.findElement(By.name("selected[]"));
            var id = checkbox.getAttribute("value");

            // Вторая ячейка = Last name
            var lastname = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
            // Третья ячейка = First name
            var firstname = row.findElement(By.cssSelector("td:nth-child(3)")).getText();
            var contact = new ContactData()
                    .withId(id)
                    .withFirstname(firstname)
                    .withLastname(lastname);
            contacts.add(contact);
        }
        return contacts;
    }
}
