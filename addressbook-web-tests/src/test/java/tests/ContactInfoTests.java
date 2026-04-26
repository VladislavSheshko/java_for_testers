package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoTests extends TestBase {


    // проверяет правильность представления телефонов
    // на главной страницы для 1(первого попавшегося контакта)
    @Test
    void testPhones() {
        var contacts = app.hbm().getContactList();
        var contact = contacts.get(0);
        var phones = app.contact().getPhones(contact);
        var expected = Stream.of(contact.home(), contact.mobile(), contact.work())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expected, phones);
    }

    //Проверяет контакты уже по всем имеющимся
    @Test
    void testPhonesAll() {
        var contacts = app.hbm().getContactList();
        var phones = app.contact().getPhones();
        for (var contact : contacts) {
            var expected = Stream.of(contact.home(), contact.mobile(), contact.work())
                    .filter(s -> s != null && !"".equals(s))
                    .collect(Collectors.joining("\n"));
            Assertions.assertEquals(expected, phones.get(contact.id()));
        }
    }

    //Делает то же самое, что testPhonesAll, только здесь
    //список полученный из БД тоже преобразовываем в словарь
    //и будет сравнивать ни каждый по отдельности, а просто сравнение двух словарей
    //реализвано с применением функционального программирования
    @Test
    void testPhonesAll2() {
        var contacts = app.hbm().getContactList();
        var expected = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
                Stream.of(contact.home(), contact.mobile(), contact.work())
                        .filter(s -> s != null && !"".equals(s))
                        .collect(Collectors.joining("\n"))
        ));
        var phones = app.contact().getPhones();
        Assertions.assertEquals(expected, phones);
    }

    // проверяет правильность представления почтового адреса
    // на главной страницы для 1(первого попавшегося контакта)
    @Test
    void testAddress() {
        var contacts = app.hbm().getContactList();
        var contact = contacts.get(0);
        var address = app.contact().getAddress(contact);
        //тк у нас 1 поле адреса, часть кода, где склеиваем результаты убираем
        var expected = contact.address() != null && !contact.address().isEmpty()
                ? contact.address() : "";
        Assertions.assertEquals(expected, address);
    }

    // проверяет правильность представления адреса электронной почты
    // на главной страницы для 1(первого попавшегося контакта)
    @Test
    void testEmail() {
        var contacts = app.hbm().getContactList();
        var contact = contacts.get(1);
        var email = app.contact().getEmail(contact);
        var expected = Stream.of(contact.email(), contact.email2(), contact.email3())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expected, email);
    }
}
