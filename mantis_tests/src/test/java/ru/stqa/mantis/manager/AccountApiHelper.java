package ru.stqa.mantis.manager;

import okhttp3.*;
import ru.stqa.mantis.model.AccountData;

import java.io.IOException;

public class AccountApiHelper extends HelperBase {

    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client;

    public AccountApiHelper(ApplicationManager manager) {
        super(manager);
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
                .url(String.format("%s/users/", manager.property("mantis.apiBaseUrl")))
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
}