package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void canCreateContact() {
        List<ContactData> oldContacts = app.contact().getList();
        var contact = new ContactData("", "Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail", "");
        app.contact().createContact(contact);
        List<ContactData> newContacts = app.contact().getList();
        // Проверяем, что размер списка увеличился на 1
        Assertions.assertEquals(oldContacts.size() + 1, newContacts.size());

        // Ищем новый контакт только по имени и фамилии
        ContactData newContact = null;
        for (var c : newContacts) {
            if (c.firstname().equals(contact.firstname())
                    && c.lastname().equals(contact.lastname())) {
                newContact = c;
                break;
            }
        }
        if (newContact == null) {
            throw new AssertionError("Не найден контакт с именем " + contact.firstname() + " " + contact.lastname());
        }
        // Проверяем, что имя и фамилия совпадают с теми, что передавали
        Assertions.assertEquals(contact.firstname(), newContact.firstname());
        Assertions.assertEquals(contact.lastname(), newContact.lastname());
    }

    @Test
    public void canCreateWithPhotoContact() {
        var contact = new ContactData()
                .withFirstname(randomString(10))
                .withLastname(randomString(10))
                .withPhoto("src/test/resources/images/avatar.png");
        app.contact().createContact(contact);
    }

    //Заполняем только два поля при создании
    public static List<ContactData> contactProvider() {
        var result = new ArrayList<ContactData>();
        for (var firstname : List.of("", "contact firstname")) {
            for (var lastname : List.of("", "contact lastname")) {

                result.add(new ContactData()
                        .withFirstname(firstname)
                        .withLastname(lastname));
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstname(randomString(i * 10))
                    .withLastname(randomString(i * 10)));
        }
        return result;
    }

    //В метод передается Объект
    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContact(ContactData contact) {
        List<ContactData> oldContacts = app.contact().getList();
        app.contact().createContact(contact);
        List<ContactData> newContacts = app.contact().getList();
        Assertions.assertEquals(oldContacts.size() + 1, newContacts.size());
    }
}

/*
шесть полей данных

public static List<ContactData> contactProvider() {
    var result = new ArrayList<ContactData>();
    for(var firstname : List.of("", "contact firstname")){
        for(var lastname : List.of("", "contact lastname")){
            for(var address : List.of("", "contact address")){
                for(var mobile : List.of("", "contact mobile")){
                    for(var work : List.of("", "contact work")){
                        for(var email : List.of("", "contact email")){
                            result.add(new ContactData()
                                    .withFirstname(firstname)
                                    .withLastname(lastname)
                                    .withAddress(address)
                                    .withMobile(mobile)
                                    .withWork(work)
                                    .withEmail(email));
                        }
                    }
                }
            }
        }
    }
    for (int i = 0; i < 5; i++) {
        result.add(new ContactData()
                .withFirstname(randomString(i * 10))
                .withLastname(randomString(i * 10))
                .withAddress(randomString(i * 10))
                .withMobile(randomString(i * 10))
                .withWork(randomString(i * 10))
                .withEmail(randomString(i * 10)));
    }
    return result;
}
 */
