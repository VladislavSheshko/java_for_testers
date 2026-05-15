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
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        assertTrue(matcher.find(), "Confirmation link not found in email");

        var url = matcher.group();
        // проходим по ссылке и завершаем регистрацию (браузер)
        app.driver().get(url);
        app.account().accountActivation();
        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        app.session().login(userName, password);
    }

    //Используется REST API в создание почты и аккаунта
    @Test
    void canCreateUser() {
        //var email = String.format("%s@localhost", username);
        // авторизоваться через UI
        app.session().login("administrator", "root");
        // создать пользователя (адрес) на почтовом сервере (JamesHelper)
        app.jamesApi().addUser("10@localhost",
                "password");
        // заполняем форму создания и отправляем (браузер)
        var account = new AccountData("Vlad2", "Sheshko", "10@localhost", "", true, false);
        app.accountApi().createAccount(account);
        // ждем почту (MailHelper)
        var messages = app.mail().receive("10@localhost", "password", Duration.ofSeconds(10));
        // извлекаем ссылку из письма
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        String url = "";
        if (matcher.find()) {
            url = text.substring(matcher.start(), matcher.end());
            //System.out.println(url);
        }
        // проходим по ссылке и завершаем регистрацию (браузер)
        app.driver().get(url);
        app.account().accountActivation();
        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        app.session().login("Vlad2", "password");
    }

    @Test
    void canCreateContact() {
        app.session().login("administrator", "root");
        var account = new AccountData("Vlad", "Sheshko", "1@localhost", "", true, false);
        app.account().createAccount(account);
    }
}
