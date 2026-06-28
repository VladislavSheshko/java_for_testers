package ru.stqa.mantis.manager;

import okhttp3.*;
import org.openqa.selenium.By;
import ru.stqa.mantis.model.AccountData;

import java.io.IOException;
import java.net.CookieManager;

public class AccountApiHelper extends HelperBase {

    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client;

    public AccountApiHelper(ApplicationManager manager) {
        super(manager);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .build();
    }

    public void createAccount(AccountData account) {
        String json = String.format(
                """
                {
                  "username": "%s",
                  "realname": "%s",
                  "email": "%s %s",
                  "accesslevel": "%s",
                  "access_level": { "name": "viewer" },
                  "enabled": true,
                  "covered": false
                }
                """,
                account.userName(),
                account.realName(),
                account.email(),
                account.accessLevel(),
                account.enabled(),
                account.covered()
        );

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(String.format("%s/users", manager.property("mantis.apiBaseUrl")))
                .addHeader("Authorization", manager.property("apiKey"))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code " + response.code() + ": " + response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(String userName) {
        click(By.xpath("//a[.//span[normalize-space()='Manage']]"));
        //переход на вкладку пользователей
        click(By.linkText("Users"));
        //выбор нужного пользователя из списка
        click(By.xpath(String.format("//table//a[normalize-space()='%s']", userName)));
        //удаление выбранного пользователя
        click(By.cssSelector("#manage-user-delete-form button"));
        //подтверждение удаления
        click(By.cssSelector("form.center input[value='Delete Account']"));
    }
     /* метод для выбора имени из списка к удалению по переменной, а не конкретному значению
    public void selectUser(String userName) {
        var select = new Select(manager.driver().findElement(By.name("user")));
        select.selectByVisibleText(userName);
    }
      */
}