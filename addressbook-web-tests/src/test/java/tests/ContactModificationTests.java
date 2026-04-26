package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ContactModificationTests extends TestBase {

    @Test
    public void canModifyContact() {
        //если таблица контактов пустая, создай новый контакт
        if (!app.contact().isContactPresent()) {
            app.contact().createContact(new ContactData("", "Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail", "", ""));
        }
        List<ContactData> oldContacts = app.contact().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var contactToModify = oldContacts.get(index);
        var modifiedContact = new ContactData()
                .withId(contactToModify.id())
                .withFirstname("modified firstname")
                .withLastname(contactToModify.lastname());

        app.contact().modifyContact(contactToModify, modifiedContact);
        List<ContactData> newContacts = app.contact().getList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.set(index, modifiedContact);
        Comparator<ContactData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        newContacts.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    //Сравнение списков через БД
    @Test
    public void canModifyContactHbm() {
        //если таблица контактов пустая, создай новый контакт
        if (!app.contact().isContactPresent()) {
            app.contact().createContact(new ContactData("", "Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail", "", ""));
        }
        List<ContactData> oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var contactToModify = oldContacts.get(index);
        var modifiedContact = new ContactData()
                .withId(contactToModify.id())
                .withFirstname("modified firstname")
                .withLastname(contactToModify.lastname());

        app.contact().modifyContact(contactToModify, modifiedContact);
        List<ContactData> newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.set(index, modifiedContact);
        Comparator<ContactData> compareById = (o1, o2) ->
                Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        newContacts.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }
}
