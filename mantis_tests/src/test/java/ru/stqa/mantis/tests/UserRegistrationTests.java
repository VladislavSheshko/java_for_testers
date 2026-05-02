package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

public class UserRegistrationTests extends TestBase {

    @Test
    void canRegisterUser(String username) {
        var email = String.format("%s@localhost", username);
        app.session().login("administrator", "root"); // авторизоваться через UI
        app.jamesCli().addUser(
                String.format(email),
                "password");// создать пользователя (адрес) на почтовом сервере (JamesHelper)
        // заполняем форму создания и отправляем (браузер)
        // ждем почту (MailHelper)
        // извлекаем ссылку из письма
        // проходим по ссылке и завершаем регистрацию (браузер)
        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
    }
}
