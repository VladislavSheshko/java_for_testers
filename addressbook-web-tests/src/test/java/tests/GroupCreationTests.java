package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class GroupCreationTests extends TestBase {

    public static List<GroupData> groupProvider() {
        var result = new ArrayList<GroupData>();
        for(var name : List.of("", "group name")){
            for(var header : List.of("", "group header")){
                for(var footer : List.of("", "group footer")){
                    result.add(new GroupData(name, header, footer));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new GroupData(randomString(i * 10), randomString(i * 10), randomString(i * 10)));
        }
        return result;
    }

    //В метод передается Объект
    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateMultipleGroups(GroupData group) {
        int groupCount = app.groups().getCount();
        app.groups().createGroup(group);
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("group name'", "", "")));
        return result;
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {
        int groupCount = app.groups().getCount();
        app.groups().createGroup(group);
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount, newGroupCount);
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
}
