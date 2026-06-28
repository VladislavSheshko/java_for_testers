package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.AccountData;

import java.time.Duration;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRegistrationTests extends TestBase {


    //используется jamesCli
    @Test
    void canRegisterUser() {
        var userName = CommonFunctions.randomString(5);
        var password = "password";
        var emailDomain = "localhost";
        var email = String.format("%s@%s", userName, emailDomain);
        // авторизоваться через UI
        app.session().login("administrator", "root");
        // создать пользователя (адрес) на почтовом сервере (JamesHelper)
        app.jamesCli().addUser(email, password);
        // заполняем форму создания и отправляем (браузер)
        var account = new AccountData(userName, "Sheshko", email, "", true, false);
        app.account().createAccount(account);
        // ждем почту (MailHelper)
        var messages = app.mail().receive(email, password, Duration.ofSeconds(10));
        // извлекаем ссылку из письма
        var url = messages.get(0).getConfirmationLink();
        // проходим по ссылке и завершаем регистрацию (браузер)
        app.driver().get(url);
        app.account().accountActivation();
        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        app.session().login(userName, password);

        //добавляем шаги для удаления почтового адреса и учетной записи
        // выполнить логаут
        app.session().logout();
        // авторизоваться через админа
        app.session().login("administrator", "root");
        // удалить только что созданную учетную запись
        app.accountApi().deleteAccount(userName);
        // выполняем удаление почтового адреса
        app.jamesApi().deleteUser(email);
    }


    // Используется REST API в создание почты и аккаунта
    // тест после себя подчищает все данные
    @Test
    void canCreateUserAll() {
        // авторизоваться через UI
        app.session().login("administrator", "root");
        // создать пользователя (адрес) на почтовом сервере (JamesHelper)
        app.jamesApi().addUser("12@localhost",
                "password");
        // заполняем форму создания и отправляем (браузер)
        var account = new AccountData("Vlad4", "Sheshko", "12@localhost", "", true, false);
        app.accountApi().createAccount(account);
        // ждем почту (MailHelper)
        var messages = app.mail().receive("12@localhost", "password", Duration.ofSeconds(10));
        // извлекаем последнюю ссылку из письма
        var url = messages.get(messages.size() - 1).getConfirmationLink();
        // проходим по ссылке и завершаем регистрацию (браузер)
        app.driver().get(url);
        app.account().accountActivation();
        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        app.session().login("Vlad4", "password");
        // выполнить логаут
        app.session().logout();
        // авторизоваться через админа
        app.session().login("administrator", "root");
        // удалить только что созданную учетную запись
        app.accountApi().deleteAccount("Vlad4");
        // выполняем удаление почтового адреса
        app.jamesApi().deleteUser("12@localhost");
    }

    @Test
    void canCreateContact() {
        app.session().login("administrator", "root");
        var account = new AccountData("Vlad", "Sheshko", "1@localhost", "", true, false);
        app.account().createAccount(account);
    }
}
