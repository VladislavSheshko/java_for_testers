package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

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

    public void createContact(ContactData contact, GroupData group) {
        openContactCreation();
        initContactCreation();
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreation();
        returnToContactPage();
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
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
    public void removeContactFromGroup(ContactData contact, GroupData group) {
        openHomePage();
        var groupSelect = new Select(manager.driver.findElement(By.name("group")));
        groupSelect.selectByVisibleText(group.name());
        selectContact(contact);
        removeSelectedContactFromGroup();
        openHomePage();
    }

    private void removeSelectedContactFromGroup() {
        click(By.name("remove"));
    }

    private void removeSelectedContact() {
        click(By.name("delete"));
    }

    public void addContactToGroup(ContactData contact) {
        openHomePage();
        selectContact(contact);
        addContact();
        openHomePage();
    }

    private void addContact() {
        click(By.name("to_group"));
        var select = new Select(manager.driver.findElement(By.name("to_group")));
        select.selectByIndex(0);
        click(By.name("add"));
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

    // метод для работы с группами при добавлении контактов через UI
    public List<ContactData> getContactsInGroupFromUi(String groupName) {
        var select = new Select(manager.driver.findElement(By.name("group")));
        select.selectByVisibleText(groupName);
        // возвращаем список контактов, отфильтрованных по группе
        return getList();
    }
}
