import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() {
        openGroupPage();
        if (!isGroupPresent()) {
            createGroup(new GroupData("Group name 1", "Group header 1", "Group footer 1"));
        }
        removeGroup();
    }

}
