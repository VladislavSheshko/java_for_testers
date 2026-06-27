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
    //создание нового почтового адреса
    @Test
    void canCreateUserApi() throws IOException, InterruptedException {
        app.jamesApi().addUser(
                String.format("%s@localhost", CommonFunctions.randomString(7)),
                "password");
    }

    //удаление почтового адреса
    @Test
    void canDeleteUserApi() throws IOException, InterruptedException {
        app.jamesApi().deleteUser("12@localhost");
    }
}
