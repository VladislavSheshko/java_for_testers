package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void canCreateContact() {
        app.contact().createContact(new ContactData("Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail"));
    }

    public static List<ContactData> contactProvider() {
        var result = new ArrayList<ContactData>();
        for(var firstname : List.of("", "contact firstname")){
            for(var lastname : List.of("", "contact lastname")){
                for(var address : List.of("", "contact address")){
                    for(var mobile : List.of("", "contact mobile")){
                        for(var work : List.of("", "contact work")){
                            for(var email : List.of("", "contact email")){
                                result.add(new ContactData(firstname, lastname, address, mobile, work, email));
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData(randomString(i * 10), randomString(i * 10), randomString(i * 10), randomString(i * 10), randomString(i * 10), randomString(i * 10)));
        }
        return result;
    }

    //В метод передается Объект
    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateMultipleContact(ContactData contact) {
        int contactCount = app.contact().getCount();
        System.out.println(contactCount);
        app.contact().createContact(contact);
        int newContactCount = app.contact().getCount();
        Assertions.assertEquals(contactCount + 1, newContactCount);
    }
}
