package model;

public record ContactData(String firstname, String lastname, String address, String mobile, String work, String email) {

    public ContactData() {
        this("", "", "", "", "", "");
    }

}
