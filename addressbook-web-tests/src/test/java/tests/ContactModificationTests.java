package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactModificationTests extends TestBase {

    @Test
    public void canModifyContact() {
        //если таблица контактов пустая, создай новый контакт
        if (!app.contact().isContactPresent()) {
            app.contact().createContact(new ContactData("Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail"));
        }
        app.contact().modifyContact(new ContactData().withFirstname("modified firstname"));
    }
}
