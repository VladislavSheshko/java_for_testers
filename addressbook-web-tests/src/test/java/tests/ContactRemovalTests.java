package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase {


    @Test
    public void canRemoveContact() {
        //если таблица контактов пустая, создай новый контакт
        if (!app.contact().isContactPresent()) {
            app.contact().createContact(new ContactData("Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail"));
        }
        app.contact().removeContact();
    }
}
