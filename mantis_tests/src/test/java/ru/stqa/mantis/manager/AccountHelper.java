package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class AccountHelper extends HelperBase {

    public AccountHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createAccount(AccountHelper account) {
        openManagePage();
        openUsersPage();
        initAccountCreation();
        fillAccountForm(account);
        submitGroupCreation();
        returnToGroupPage();
    }


    private void openManagePage() {
        if (!manager.isElementPresent(By.xpath("//a[contains(text(), 'Users')]"))) {
            click(By.linkText("Manage"));
        }
    }

    private void openUsersPage() {
        click(By.xpath("//a[contains(text(), 'Users')]"));
    }

    private void initAccountCreation() {
        click(By.linkText("Create New Account"));
    }

    private void fillAccountForm(AccountHelper account) {

    }


}
