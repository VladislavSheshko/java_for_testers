package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

import java.io.IOException;

public class JamesTests extends TestBase {

    @Test
    void canCreateUser() throws IOException, InterruptedException {
        app.jamesCli().addUser(
                String.format("%s@localhost", CommonFunctions.randomString(7)),
                "password");

    }
}
