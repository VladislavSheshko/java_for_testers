package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.model.AccountData;

import java.time.Duration;
import java.util.regex.Pattern;

public class UserRegistrationTests extends TestBase {

    @Test
    void canRegisterUser() {
        //var email = String.format("%s@localhost", username);
        // авторизоваться через UI
        app.session().login("administrator", "root");
        // создать пользователя (адрес) на почтовом сервере (JamesHelper)
        app.jamesCli().addUser("8@localhost",
                "password");
        // заполняем форму создания и отправляем (браузер)
        var account = new AccountData("Vlad2", "Sheshko", "8@localhost", "", true, false);
        app.account().createAccount(account);
        // ждем почту (MailHelper)
        var messages = app.mail().receive("8@localhost", "password", Duration.ofSeconds(10));
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
