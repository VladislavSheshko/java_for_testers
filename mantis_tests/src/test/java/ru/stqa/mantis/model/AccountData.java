package ru.stqa.mantis.model;

public record AccountData(String userName, String realName, String email, String accessLevel, Boolean enabled, Boolean covered) {

    public AccountData() {
        this("", "", "", "", true, false);
    }

    public AccountData withUserName(String userName) {
        return new AccountData(userName, this.realName, this.email, this.accessLevel, this.enabled, this.covered);
    }

    public AccountData withRealName(String realName) {
        return new AccountData(this.userName, realName, this.email, this.accessLevel, this.enabled, this.covered);
    }

    public AccountData withEmail(String email) {
        return new AccountData(this.userName, this.realName, email, this.accessLevel, this.enabled, this.covered);
    }

    public AccountData withAccessLevel(String accessLevel) {
        return new AccountData(this.userName, this.realName, this.email, accessLevel, this.enabled, this.covered);
    }

    public AccountData withEnabled(Boolean enabled) {
        return new AccountData(this.userName, this.realName, this.email, this.accessLevel, this.enabled, this.covered);
    }

    public AccountData withCovered(String covered) {
        return new AccountData(this.userName, this.realName, this.email, this.accessLevel, this.enabled, this.covered);
    }

}
