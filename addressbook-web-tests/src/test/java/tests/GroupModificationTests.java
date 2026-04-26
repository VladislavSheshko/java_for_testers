package tests;

import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GroupModificationTests extends TestBase {

    @Test
    public void canModifyGroup() {
        //создание группы через UI
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("", "Group name 1", "Group header 1", "Group footer 1"));
        }
        List<GroupData> oldGroups = app.groups().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        GroupData testDate = new GroupData().withName("modified name");
        app.groups().modifyGroup(oldGroups.get(index), testDate);
        List<GroupData> newGroups = app.groups().getList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.set(index, testDate.withId(oldGroups.get(index).id()));
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups, expectedList);
    }


    //при сравнении сравниваем списки
    @Test
    public void canModifyGroupHbm() {
        //создание группы через БД
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "Group name 1", "Group header 1", "Group footer 1"));
        }
        //Получение списков из БД
        List<GroupData> oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        GroupData testDate = new GroupData().withName("modified name");
        app.groups().modifyGroup(oldGroups.get(index), testDate);
        List<GroupData> newGroups = app.hbm().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.set(index, testDate.withId(oldGroups.get(index).id()));
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups, expectedList);
    }

    //при сравнении сравниваем множества
    @Test
    public void canModifyGroupHbmSet() {
        //создание группы через БД
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "Group name 1", "Group header 1", "Group footer 1"));
        }
        //Получение списков из БД
        List<GroupData> oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        GroupData testDate = new GroupData().withName(CommonFunctions.randomString(10));
        app.groups().modifyGroup(oldGroups.get(index), testDate);
        List<GroupData> newGroups = app.hbm().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.set(index, testDate.withId(oldGroups.get(index).id()));
        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
    }
}
