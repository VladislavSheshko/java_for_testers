package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void canCreateContact() {
        app.contact().createContact(new ContactData("Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail"));
    }

    @Test
    public void canCreateContactWithEmptyName() {
        app.contact().createContact(new ContactData());
    }

    @Test
    public void canCreateContactWithNameOnly() {
        app.contact().createContact(new ContactData().withFirstname("Новое имя"));
    }
}
