package model;

public record ContactData(
        String id,
        String firstname,
        String lastname,
        String address,
        String mobile,
        String work,
        String email,
        String photo,
        String home
) {

    public ContactData() {
        this("", "", "","","","","", "", "");
    }

    public ContactData(String id) {
        this(id, "", "", "", "", "", "","", "");
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstname, this.lastname, this.address, this.mobile, this.work, this.email, this.photo, this.home);
    }

    public ContactData withFirstname(String firstname) {
        return new ContactData(this.id, firstname, this.lastname, this.address, this.mobile, this.work, this.email, this.photo, this.home);
    }

    public ContactData withLastname(String lastname) {
        return new ContactData(this.id, this.firstname, lastname, this.address, this.mobile, this.work, this.email, this.photo, this.home);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this.id, this.firstname, this.lastname, address, this.mobile, this.work, this.email, this.photo, this.home);
    }

    public ContactData withMobile(String mobile) {
        return new ContactData(this.id, this.firstname, this.lastname, this.address, mobile, this.work, this.email, this.photo, this.home);
    }

    public ContactData withWork(String work) {
        return new ContactData(this.id, this.firstname, this.lastname, this.address, this.mobile, work, this.email, this.photo, this.home);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this.id, this.firstname, this.lastname, this.address, this.mobile, this.work, email, this.photo, this.home);
    }
    public ContactData withPhoto(String photo) {
        return new ContactData(this.id, this.firstname, this.lastname, this.address, this.mobile, this.work, this.email, photo, this.home);
    }

    public ContactData withHome(String home) {
        return new ContactData(this.id, this.firstname, this.lastname, this.address, this.mobile, this.work, this.email, this.photo, home);
    }
}