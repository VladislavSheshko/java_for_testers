package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void canCreateGroup() {
        int groupCount = app.groups().getCount();
        app.groups().createGroup(new GroupData("Group name 1", "Group header 1", "Group footer 1"));
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    @Test
    public void canCreateGroupWithEmptyName() {
        app.groups().createGroup(new GroupData());
    }

    @Test
    public void canCreateGroupWithNameOnly() {
        app.groups().createGroup(new GroupData().withName("some some"));
    }

    @Test
    public void canCreateMultipleGroup() {
        int n = 5;
        int groupCount = app.groups().getCount();

        for (int i = 0; i < n; i++) {
            app.groups().createGroup(new GroupData(randomString(i * 10), "Group header 1", "Group footer 1"));
        }

        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + n, newGroupCount);
    }

}
