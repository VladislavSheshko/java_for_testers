package ru.stqa.mantis.model;

import java.util.regex.Pattern;

public record MailMessage(String from, String content) {

    public MailMessage() {
        this("","");
    }

    public MailMessage withFrom(String from) {
        return new MailMessage(from, this.content);
    }

    public MailMessage withContent(String content) {
        return new MailMessage(this.from, content);
    }

    // метод для извлечения ссылки по нужному шаблону из письма
    public String getConfirmationLink() {
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group();
        }

        throw new RuntimeException("Confirmation link not found in email");
    }
}
