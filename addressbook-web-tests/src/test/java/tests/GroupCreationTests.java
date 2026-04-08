package tests;

import common.CommonFunctions;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

    public static List<GroupData> groupProvider() {
        var result = new ArrayList<GroupData>();
        for(var name : List.of("", "group name")){
            for(var header : List.of("", "group header")){
                for(var footer : List.of("", "group footer")){
                    result.add(new GroupData().withName(name).withHeader(header).withFooter(footer));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new GroupData()
                    .withName(CommonFunctions.randomString(i * 10))
                    .withHeader(CommonFunctions.randomString(i * 10))
                    .withFooter(CommonFunctions.randomString(i * 10)));
        }
        return result;
    }

    //В метод передается Объект
    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateMultipleGroups(GroupData group) {
        List<GroupData> oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        List<GroupData> newGroups = app.groups().getList();
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(group.withId(newGroups.get(newGroups.size() - 1).id()).withHeader("").withFooter(""));
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups, expectedList);
    }


    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "")));
        return result;
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {
        List<GroupData> oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        List<GroupData> newGroups = app.groups().getList();
        Assertions.assertEquals(newGroups, oldGroups);
    }

/*
    //Было, метод генерации групп для canCreateMultipleGroup
    public static List<String> groupNameProvider() {
        var result = new ArrayList<String>(List.of("group name", "group name'"));
        for (int i = 0; i < 5; i++) {
            result.add(randomString(i * 10));
        }
        return result;
    }

    //Было, в метод передавали параметр
    @ParameterizedTest
    @MethodSource("groupNameProvider")
    public void canCreateMultipleGroup(String name) {
        int groupCount = app.groups().getCount();
        app.groups().createGroup(new GroupData(name, "Group header 1", "Group footer 1"));
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    //Генерация данных для групп, без использования вложенных циклов
    public static List<GroupData> groupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData(),
                new GroupData().withName("some some"),
                new GroupData("group name", "", ""),
                new GroupData("group name'", "", "")));
        for (int i = 0; i < 5; i++) {
            result.add(new GroupData(randomString(i * 10), randomString(i * 10), randomString(i * 10)));
        }
        return result;
    }
 */

    /*
    понять и найти причины из-за чего не работает данный тест
    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateMultipleGroups(GroupData group) {
        List<GroupData> oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        List<GroupData> newGroups = app.groups().getList();
        //Проверяем, что размер группы увеличился на 1
        Assertions.assertEquals(oldGroups.size() + 1, newGroups.size());
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(group.withId(newGroups.get(newGroups.size() - 1).id()));
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups, expectedList);
        GroupData createdGroup = null;
        for (var g : newGroups) {
            if (g.name().equals(group.name())) {
                createdGroup = g;
                break;
            }
        }
        if (createdGroup == null) {
            throw new AssertionError("Не найдена группа с именем: " + group.name());
        }
        // Проверяем, что имя совпадает с переданным
        Assertions.assertEquals(group.name(), createdGroup.name());
    }
     */
}
