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
        var contact = new ContactData("", "Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail");
        app.contact().createContact(contact);
        List<ContactData> newContacts = app.contact().getList();
        Assertions.assertEquals(oldContacts.size() + 1, newContacts.size());
    }

    //Заполняем только два поля при создании
    public static List<ContactData> contactProvider() {
        var result = new ArrayList<ContactData>();
        for(var firstname : List.of("", "contact firstname")){
            for(var lastname : List.of("", "contact lastname")){

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
