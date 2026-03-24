package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupModificationTests extends TestBase {

    @Test
    public void canModifyGroup() {
        if (!app.groups().isGroupPresent()) {
            app.groups().createGroup(new GroupData("Group name 1", "Group header 1", "Group footer 1"));
        }
        app.groups().modifyGroup(new GroupData().withName("modified name"));
    }
}
