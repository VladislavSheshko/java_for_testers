package manager;

import io.qameta.allure.Step;
import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import model.Pair;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateHelper extends HelperBase {

    private SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);

        sessionFactory = new Configuration()
                .addAnnotatedClass(ContactRecord.class)
                .addAnnotatedClass(GroupRecord.class)
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:mysql://localhost:3307/addressbook?zeroDateTimeBehavior=convertToNull")
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "root")
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "")
                .buildSessionFactory();
    }

    //Группы
//    static List<GroupData> convertGroupList(List<GroupRecord> records) {
//        List<GroupData> result = new ArrayList<>();
//        for (var record : records) {
//            result.add(convert(record));
//        }
//        return result;
//    }
    // код который выше, переписан под функциональный стиль
    static List<GroupData> convertGroupList(List<GroupRecord> records) {
        return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    private static GroupData convert(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    private static GroupRecord convert(GroupData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id), data.name(), data.header(), data.footer());
    }

    //Контакты
//    static List<ContactData> convertContactList(List<ContactRecord> records) {
//        List<ContactData> result = new ArrayList<>();
//        for (var record : records) {
//            result.add(convert(record));
//        }
//        return result;
//    }
    // код который выше, переписан под функциональный стиль
    static List<ContactData> convertContactList(List<ContactRecord> records) {
        return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    private static ContactData convert(ContactRecord record) {
        return new ContactData().withId("" + record.id)
                .withFirstname(record.firstname)
                .withLastname(record.lastname)
                .withAddress(record.address)
                .withHome(record.home)
                .withMobile(record.mobile)
                .withWork(record.work)
                .withAddress(record.address)
                .withEmail(record.email)
                .withEmail2(record.email2)
                .withEmail3(record.email3);
    }

    private static ContactRecord convert(ContactData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new ContactRecord(Integer.parseInt(id), data.firstname(), data.lastname(), data.address());
    }

    @Step
    public List<GroupData> getGroupList() {
        return convertGroupList(sessionFactory.fromSession(session -> {
            return session.createQuery("from GroupRecord", GroupRecord.class).list();
        }));
    }

    public List<ContactData> getContactList() {
        return convertContactList(sessionFactory.fromSession(session -> {
            return session.createQuery("from ContactRecord", ContactRecord.class).list();
        }));
    }

//    public void createContact(ContactData contact) {
//        sessionFactory.inSession(session -> {
//            session.getTransaction().begin();
//            session.persist(convert(contact));
//            session.getTransaction().commit();
//        });
//    }

    //считает кол-во групп в результате выполнения запроса
    public long getGroupCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        });
    }

    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }

    // метод для работы через БД
    public List<ContactData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            String id = group.id();
            if (id.isEmpty()) id = "0";
            var groupRecord = session.find(GroupRecord.class, Integer.parseInt(id));
            if (groupRecord == null) {
                return new ArrayList<>();
            }
            return convertContactList(groupRecord.contacts);
        });
    }

    //поиск контактов не находящихся в группе
    public List<ContactData> getContactsNotInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            String groupId = group.id();
            if (groupId.isEmpty()) groupId = "0";

            var allContacts = session.createQuery("from ContactRecord", ContactRecord.class).list();
            var groupRecord = session.find(GroupRecord.class, Integer.parseInt(groupId));
            var contactsInGroup = groupRecord == null ? List.of() : groupRecord.contacts;

            return allContacts.stream()
                    .filter(contact -> !contactsInGroup.contains(contact))
                    .map(HibernateHelper::convert)
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        });
    }

    public Pair<ContactData, GroupData> findOrCreateContactGroupPair() {
        return sessionFactory.fromSession(session -> {
            // все группы из БД
            var groupRecords = session.createQuery("from GroupRecord", GroupRecord.class).list();
            // ищем подходящую пару (контакт NOT в группе)
            for (var groupRecord : groupRecords) {
                var group = convert(groupRecord);
                var contactsNotInGroup = getContactsNotInGroup(group);
                if (!contactsNotInGroup.isEmpty()) {
                    return new Pair<>(contactsNotInGroup.get(0), group);
                }
            }
            // если вообще нет ни одной группы и контакта, возвращаем null
            return new Pair<>(null, null);
        });
    }
}
