package tests;

import common.CommonFunctions;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
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
                .withFirstname(CommonFunctions.randomString(10))
                .withLastname(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));
        app.contact().createContact(contact);
    }

    @Test
    public void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstname(CommonFunctions.randomString(10))
                .withLastname(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "Group name 1", "Group header 1", "Group footer 1"));
        }
        var group = app.hbm().getGroupList().get(0);
        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contact().createContact(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }

    //Проверка списков загружаемых из БД
    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateContactHbm(ContactData contact) {
        List<ContactData> oldContacts = app.hbm().getContactList();
        app.contact().createContact(contact);
        List<ContactData> newContacts = app.hbm().getContactList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        var maxId = newContacts.get(newContacts.size() - 1).id();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(maxId));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);    }

    //Заполняем только два поля при создании
    public static List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();
//        for (var firstname : List.of("", "contact firstname")) {
//            for (var lastname : List.of("", "contact lastname")) {
//
//                result.add(new ContactData()
//                        .withFirstname(firstname)
//                        .withLastname(lastname));
//            }
//        }
        var json = Files.readString(Paths.get("contacts.json"));
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(json, new TypeReference<List<ContactData>>(){});
        result.addAll(value);
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
