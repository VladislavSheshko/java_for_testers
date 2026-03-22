import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void canCreateGroup() {
        openGroupPage();
        createGroup("Group name 1", "Group header 1", "Group footer 1");
    }

    @Test
    public void canCreateGroupWithEmptyName() {
        openGroupPage();
        createGroup("", "", "");
    }

}
