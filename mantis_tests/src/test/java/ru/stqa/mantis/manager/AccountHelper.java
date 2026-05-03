package ru.stqa.mantis.manager;

import org.openqa.selenium.By;
import ru.stqa.mantis.model.AccountData;

public class AccountHelper extends HelperBase {

    public AccountHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createAccount(AccountData account) {
        openManagePage();
        openUsersPage();
        initAccountCreation();
        fillAccountForm(account);
        submitAccountCreation();
    }


    private void openManagePage() {
        //if (!manager.isElementPresent(By.xpath("//a[contains(text(), 'Users')]"))) {
            click(By.linkText("Manage"));
      //  }
    }

    private void openUsersPage() {
        click(By.linkText("Users"));
       // click(By.xpath("//a[contains(text(), 'Users')]"));
    }

    private void initAccountCreation() {
        click(By.xpath("//a[contains(text(), 'Create New Account')]"));
    }

    private void fillAccountForm(AccountData account) {
        type(By.name("username"), account.userName());
        type(By.name("realname"), account.realName());
        type(By.name("email"), account.email());
    }

    private void submitAccountCreation() {
        click(By.xpath("//input[@type='submit' and @value='Create User']"));
    }

}
