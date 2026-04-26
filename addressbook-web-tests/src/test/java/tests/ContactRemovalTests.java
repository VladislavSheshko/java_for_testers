package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        //если таблица контактов пустая, создай новый контакт
        if (!app.contact().isContactPresent()) {
            app.contact().createContact(new ContactData("", "Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail", null, "", "", ""));
        }
        List<ContactData> oldContacts = app.contact().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contact().removeContact(oldContacts.get(index));
        List<ContactData> newContacts = app.contact().getList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        Assertions.assertEquals(newContacts, expectedList);
    }

    //Сравнение списков через БД
    @Test
    public void canRemoveContactHbm() {
        //если таблица контактов пустая, создай новый контакт
        if (!app.contact().isContactPresent()) {
            app.contact().createContact(new ContactData("", "Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail", null, "", "", ""));
        }
        List<ContactData> oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contact().removeContact(oldContacts.get(index));
        List<ContactData> newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    public void canRemoveContactFromGroup() {
        //если таблица контактов пустая, создаём контакт через UI
        if (!app.contact().isContactPresent()) {
            app.contact().createContact(
                    new ContactData("", "Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail", "", "", "", "")
            );
        }
        //если групп в БД нет, создаём группу через Hibernate
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "Group name 1", "Group header 1", "Group footer 1"));
        }
        GroupData group = app.hbm().getGroupList().get(0);
        //если в группе ещё 0 контактов, сначала добавим один контакт в группу через UI
        var contactsInGroup = app.hbm().getContactsInGroup(group);
        if (contactsInGroup.isEmpty()) {
            List<ContactData> allContacts = app.contact().getList();
            if (allContacts.isEmpty()) {
                Assertions.fail("Нет контактов для добавления в группу");
            }
            var rnd = new Random();
            var contactToAdd = allContacts.get(rnd.nextInt(allContacts.size()));
            app.contact().addContactToGroup(contactToAdd);
        }
        //после добавления снова получаем список контактов в группе в БД
        List<ContactData> oldContactsInGroup = app.hbm().getContactsInGroup(group);
        Assertions.assertTrue(
                !oldContactsInGroup.isEmpty(),
                "Группа должна содержать хотя бы один контакт для удаления"
        );
        var rnd = new Random();
        var contactToRemove = oldContactsInGroup.get(rnd.nextInt(oldContactsInGroup.size()));
        app.contact().removeContactFromGroup(contactToRemove, group);
        var newContactsInGroup = app.hbm().getContactsInGroup(group);
        //проверяем, что в группе в БД стало на 1 контакт меньше
        Assertions.assertEquals(
                oldContactsInGroup.size() - 1,
                newContactsInGroup.size(),
                "Группа в БД должна содержать на 1 контакт меньше"
        );
    }
}
