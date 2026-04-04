package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() {
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("Group name 1", "Group header 1", "Group footer 1"));
        }
        int groupCount = app.groups().getCount();
        app.groups().removeGroup();
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount - 1, newGroupCount);
    }

    //Удаление всех существующих групп
    @Test
    void canRemoveAllGroupsAtOnce() {
        if (app.groups().getCount() == 0) {
            app.groups().createGroup(new GroupData("Group name 1", "Group header 1", "Group footer 1"));
        }
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.groups().getCount());
    }
}
