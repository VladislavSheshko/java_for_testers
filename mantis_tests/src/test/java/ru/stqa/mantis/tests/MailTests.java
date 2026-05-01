package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.regex.Pattern;

public class MailTests extends TestBase {

    //получаем почту без ожидания
    @Test
    void canReceiveEmail() {
        var messages = app.mail().receive("user1@localhost", "password");
        Assertions.assertEquals(1, messages.size());
        System.out.println(messages);
    }

    //получаем почту с ожиданием
    @Test
    void canReceiveEmail2() {
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(60));
        Assertions.assertEquals(1, messages.size());
        System.out.println(messages);
    }

    //удаление почтового адреса
    @Test
    void canDrainInbox() {
        app.mail().drain("user1@localhost", "password");
    }

    //извлечение ссылки из письма
    @Test
    void canExtractUrl() {
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(60));
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        if (matcher.find()) {
            var url = text.substring(matcher.start(), matcher.end());
            System.out.println(url);
        }
    }
}
