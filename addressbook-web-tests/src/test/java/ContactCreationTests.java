import org.junit.jupiter.api.Test;
import tests.TestBase;

public class ContactCreationTests extends TestBase {

    @Test
    public void canCreateContact() {
        openContactCreation();
        createContact("Владислав", "Шешко", "Челябинск", "+7", "QA", "1@mail");
    }

    @Test
    public void canCreateContactWithEmptyName() {
        openContactCreation();
        createContact("", "", "", "", "", "");
    }
}
