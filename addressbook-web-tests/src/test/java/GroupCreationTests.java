import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void canCreateGroup() {
        openGroupPage();
        createGroup(new GroupData("Group name 1", "Group header 1", "Group footer 1"));
    }

    @Test
    public void canCreateGroupWithEmptyName() {
        openGroupPage();
        createGroup(new GroupData());
    }

    @Test
    public void canCreateGroupWithNameOnly() {
        openGroupPage();
        createGroup(new GroupData().withName("some some"));
    }

}
